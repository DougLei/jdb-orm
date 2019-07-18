package com.douglei.orm.configuration.impl.xml.element.extconfiguration.datatypehandler;

import com.douglei.orm.configuration.ext.configuration.datatypehandler.DataTypeHandlerClassCastException;
import com.douglei.orm.configuration.ext.configuration.datatypehandler.ExtDataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.DataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.DataTypeHandlerType;
import com.douglei.orm.core.dialect.datatype.handler.classtype.ClassDataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public class XmlExtDataTypeHandler implements ExtDataTypeHandler {

	private DataTypeHandler dataTypeHandler;
	
	public XmlExtDataTypeHandler(String clz) {
		Object obj = ConstructorUtil.newInstance(clz);													   
		if(!((obj instanceof ClassDataTypeHandler) || (obj instanceof ResultSetColumnDataTypeHandler) || (obj instanceof DBDataTypeHandler))) {
			throw new DataTypeHandlerClassCastException("扩展的DataTypeHandler=["+clz+"], 必须继承 ["+ClassDataTypeHandler.class.getName()+"] 或 ["+ResultSetColumnDataTypeHandler.class.getName()+"] 或 ["+DBDataTypeHandler.class.getName()+"] 三个类之一");
		}
		this.dataTypeHandler = (DataTypeHandler) obj;
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
