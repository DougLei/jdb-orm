package com.douglei.database.dialect.datatype;

import java.util.List;

import com.douglei.configuration.extconfiguration.datatypehandler.ExtDataTypeHandler;
import com.douglei.database.dialect.datatype.classtype.ClassDataTypeHandlerMapping;
import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandlerMapping;
import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandlerMapping;
import com.douglei.instances.scanner.ClassScanner;
import com.douglei.utils.reflect.ConstructorUtil;

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
		List<String> classPaths = cs.multiScan(basePackage + ".resultset.columntype", basePackage + ".dbtype");
		if(classPaths.size() > 0) {
			Object obj = null;
			for (String cp : classPaths) {
				obj = ConstructorUtil.newInstance(cp);
				if(obj instanceof ResultSetColumnDataTypeHandler) {
					resultsetColumnDataTypeHandlerMapping.register((ResultSetColumnDataTypeHandler) obj);
				}else if(obj instanceof DBDataTypeHandler) {
					dbDataTypeHandlerMapping.register((DBDataTypeHandler) obj);
				}
			}
		}
		cs.destroy();
	}
	
	public DataTypeHandler getDataTypeHandlerByClassType(Object value) {
		return classDataTypeHandlerMapping.getDataTypeHandlerByClassType(value);
	}
	public DataTypeHandler getDataTypeHandlerByCode(String code) {
		return classDataTypeHandlerMapping.getDataTypeHandlerByClassType(code);
	}
	public DataTypeHandler getDataTypeHandlerByDatabaseColumnType(int columnType, String columnName, String columnTypeName) {
		return resultsetColumnDataTypeHandlerMapping.getDataTypeHandlerByDatabaseColumnType(columnType, columnName, columnTypeName);
	}
	public DBDataTypeHandler getDBDataTypeHandlerByDBTypeName(String typeName) {
		return dbDataTypeHandlerMapping.getDBDataTypeHandlerByDBTypeName(typeName);
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
}
