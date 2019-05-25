package com.douglei.configuration.impl.xml.element.extconfiguration.datatypehandler;

import com.douglei.configuration.extconfiguration.datatypehandler.DataTypeHandlerClassCastException;
import com.douglei.configuration.extconfiguration.datatypehandler.ExtDataTypeHandler;
import com.douglei.database.dialect.Dialect;
import com.douglei.database.dialect.DialectMapping;
import com.douglei.database.dialect.datatype.DataTypeHandler;
import com.douglei.database.dialect.datatype.DataTypeHandlerType;
import com.douglei.database.dialect.datatype.classtype.ClassDataTypeHandler;
import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.utils.StringUtil;
import com.douglei.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public class XmlExtDataTypeHandler implements ExtDataTypeHandler {

	private Dialect dialect;
	private DataTypeHandler dataTypeHandler;
	
	public XmlExtDataTypeHandler(String dialect, String clz) {
		if(StringUtil.notEmpty(dialect)) {
			this.dialect = DialectMapping.getDialect(dialect);
		}
		
		Object obj = ConstructorUtil.newInstance(clz);													   
		if(!((obj instanceof ClassDataTypeHandler) || (obj instanceof ResultSetColumnDataTypeHandler) || (obj instanceof DBDataTypeHandler))) {
			throw new DataTypeHandlerClassCastException("扩展的DataTypeHandler=["+clz+"], 必须继承 ["+ClassDataTypeHandler.class.getName()+"] 或 ["+ResultSetColumnDataTypeHandler.class.getName()+"] 或 ["+DBDataTypeHandler.class.getName()+"] 三个类之一");
		}
		this.dataTypeHandler = (DataTypeHandler) obj;
	}

	@Override
	public Dialect getDialect() {
		return dialect;
	}

	@Override
	public DataTypeHandlerType getType() {
		return dataTypeHandler.getType();
	}

	@Override
	public ClassDataTypeHandler getClassDataTypeHandler() {
		return (ClassDataTypeHandler) dataTypeHandler;
	}

	@Override
	public ResultSetColumnDataTypeHandler getResultsetColumnDataTypeHandler() {
		return (ResultSetColumnDataTypeHandler) dataTypeHandler;
	}

	@Override
	public DBDataTypeHandler getDBDataTypeHandler() {
		return (DBDataTypeHandler) dataTypeHandler;
	}

}
