package com.douglei.database.dialect.datatype;

import java.util.List;

import com.douglei.configuration.extconfiguration.datatypehandler.ExtDataTypeHandler;
import com.douglei.database.dialect.datatype.classtype.ClassDataTypeHandler;
import com.douglei.database.dialect.datatype.classtype.ClassDataTypeHandlerMapping;
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
	
	public AbstractDataTypeHandlerMapping() {
		List<String> classPaths = new ClassScanner().multiScan(dataTypeHandlerBasePackages());
		if(classPaths.size() > 0) {
			Object obj = null;
			for (String cp : classPaths) {
				obj = ConstructorUtil.newInstance(cp);
				if(obj instanceof ClassDataTypeHandler) {
					classDataTypeHandlerMapping.register((ClassDataTypeHandler) obj);
				}else if(obj instanceof ResultSetColumnDataTypeHandler) {
					resultsetColumnDataTypeHandlerMapping.register((ResultSetColumnDataTypeHandler) obj);
				}
			}
		}
	}
	
	/**
	 * 获取各种类型的DataTypeHandler的根包路径数组
	 * @return
	 */
	protected abstract String[] dataTypeHandlerBasePackages();
	
	public DataTypeHandler getDataTypeHandlerByClassType(Object value) {
		return classDataTypeHandlerMapping.getDataTypeHandlerByClassType(value);
	}
	public DataTypeHandler getDataTypeHandlerByCode(String code) {
		return classDataTypeHandlerMapping.getDataTypeHandlerByClassType(code);
	}
	public DataTypeHandler getDataTypeHandlerByDatabaseColumnType(int columnType, String columnTypeName) {
		return resultsetColumnDataTypeHandlerMapping.getDataTypeHandlerByDatabaseColumnType(columnType, columnTypeName);
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
		}
	}
}
