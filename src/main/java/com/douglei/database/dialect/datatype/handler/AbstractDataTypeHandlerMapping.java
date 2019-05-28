package com.douglei.database.dialect.datatype.handler;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.douglei.configuration.extconfiguration.datatypehandler.ExtDataTypeHandler;
import com.douglei.database.dialect.datatype.handler.classtype.ClassDataTypeHandler;
import com.douglei.database.dialect.datatype.handler.classtype.ClassDataTypeHandlerMapping;
import com.douglei.database.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.datatype.handler.dbtype.DBDataTypeHandlerMapping;
import com.douglei.database.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.database.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandlerMapping;
import com.douglei.instances.scanner.ClassScanner;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDataTypeHandlerMapping{
	private final ClassDataTypeHandlerMapping classDataTypeHandlerMapping = new ClassDataTypeHandlerMapping();
	private final ResultSetColumnDataTypeHandlerMapping resultsetColumnDataTypeHandlerMapping = new ResultSetColumnDataTypeHandlerMapping();
	private final DBDataTypeHandlerMapping dbDataTypeHandlerMapping = new DBDataTypeHandlerMapping();
	
	public AbstractDataTypeHandlerMapping() {
		ClassScanner cs = new ClassScanner();
		String basePackage = getClass().getPackage().getName();
		List<String> classPaths = cs.multiScan(basePackage + ".classtype", basePackage + ".resultset.columntype", basePackage + ".dbtype");
		if(classPaths.size() > 0) {
			Object obj = null;
			try {
				for (String cp : classPaths) {
					obj = getDataTypeHandlerSingleInstance(cp);
					if(obj instanceof ClassDataTypeHandler) {
						classDataTypeHandlerMapping.register((ClassDataTypeHandler) obj);
					}else if(obj instanceof ResultSetColumnDataTypeHandler) {
						resultsetColumnDataTypeHandlerMapping.register((ResultSetColumnDataTypeHandler) obj);
					}else if(obj instanceof DBDataTypeHandler) {
						dbDataTypeHandlerMapping.register((DBDataTypeHandler) obj);
					}
				}
			} catch (Exception e) {
				throw new RuntimeException("在调用DataTypeHandler子类的 singleInstance()方法时, 出现异常", e);
			}
		}
		cs.destroy();
	}
	private Object getDataTypeHandlerSingleInstance(String className) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		Class<?> clz = Class.forName(className);
		return clz.getMethod("singleInstance").invoke(null);
	}
	
	
	public ClassDataTypeHandler getDataTypeHandlerByClassType(Object value) {
		return classDataTypeHandlerMapping.getDataTypeHandlerByClassType(value);
	}
	public ClassDataTypeHandler getDataTypeHandlerByCode(String code) {
		return classDataTypeHandlerMapping.getDataTypeHandlerByClassType(code);
	}
	public ResultSetColumnDataTypeHandler getDataTypeHandlerByDatabaseColumnType(int columnType, String columnName, String columnTypeName) {
		return resultsetColumnDataTypeHandlerMapping.getDataTypeHandlerByDatabaseColumnType(columnType, columnName, columnTypeName);
	}
	public DBDataTypeHandler getDBDataTypeHandlerByDBTypeName(String typeName) {
		return dbDataTypeHandlerMapping.getDataTypeHandlerByDBTypeName(typeName);
	}
	
	/**
	 * 注册扩展的DataTypeHandler
	 * @param extDataTypeHandler
	 */
	public void registerExtDataTypeHandler(ExtDataTypeHandler extDataTypeHandler) {
		switch(extDataTypeHandler.getType()) {
			case CLASS:
				classDataTypeHandlerMapping.register(extDataTypeHandler.getClassDataTypeHandler());
				return;
			case RESULTSET_COLUMN:
				resultsetColumnDataTypeHandlerMapping.register(extDataTypeHandler.getResultsetColumnDataTypeHandler());
				return;
			case DB:
				dbDataTypeHandlerMapping.register(extDataTypeHandler.getDBDataTypeHandler());
				return;
		}
	}
	
	/**
	 * 获取默认的DataTypeHandler
	 * @return
	 */
	public ClassDataTypeHandler getDefaultClassDataTypeHandler() {
		return getDataTypeHandlerByCode("string");
	}
	
	/**
	 * 获取默认的DBDataTypeHandler
	 * @return
	 */
	public abstract DBDataTypeHandler getDefaultDBDataTypeHandler();
}
