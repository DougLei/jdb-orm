package com.douglei.orm.core.dialect.datatype.handler;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.douglei.orm.core.dialect.datatype.DataType;
import com.douglei.orm.core.dialect.datatype.handler.classtype.ClassDataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.classtype.ClassDataTypeHandlerMapping;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandlerMapping;
import com.douglei.orm.core.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandlerMapping;
import com.douglei.tools.instances.scanner.impl.ClassScanner;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDataTypeHandlerMapping{
	private final ClassDataTypeHandlerMapping classDataTypeHandlerMapping = new ClassDataTypeHandlerMapping();
	private final ResultSetColumnDataTypeHandlerMapping resultsetColumnDataTypeHandlerMapping = new ResultSetColumnDataTypeHandlerMapping();
	private final DBDataTypeHandlerMapping dbDataTypeHandlerMapping = new DBDataTypeHandlerMapping();
	
	public AbstractDataTypeHandlerMapping() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		ClassScanner scanner = new ClassScanner();
		String basePackage = getClass().getPackage().getName();
		List<String> classPaths = scanner.multiScan(basePackage + ".classtype", basePackage + ".resultset.columntype", basePackage + ".dbtype");
		if(classPaths.size() > 0) {
			Object obj = null;
			for (String cp : classPaths) {
				if(!cp.endsWith("$1")) { /** 这个判断是处理 {@link NumberDBDataTypeHandler#doValidate(String, Object, short, short)} 方法最后返回的 {@link ValidationResult} 匿名内部类 */
					obj = Class.forName(cp).getMethod("singleInstance").invoke(null);
					if(obj instanceof ClassDataTypeHandler) {
						classDataTypeHandlerMapping.register((ClassDataTypeHandler) obj);
					}else if(obj instanceof ResultSetColumnDataTypeHandler) {
						resultsetColumnDataTypeHandlerMapping.register((ResultSetColumnDataTypeHandler) obj);
					}else if(obj instanceof DBDataTypeHandler) {
						dbDataTypeHandlerMapping.register((DBDataTypeHandler) obj);
					}
				}
			}
		}
		scanner.destroy();
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
	 * 获取默认的DataTypeHandler
	 * @return
	 */
	public ClassDataTypeHandler getDefaultClassDataTypeHandler() {
		return getDataTypeHandlerByCode(DataType.STRING.getName());
	}
	
	/**
	 * 获取默认的DBDataTypeHandler
	 * @return
	 */
	public abstract DBDataTypeHandler getDefaultDBDataTypeHandler();
}
