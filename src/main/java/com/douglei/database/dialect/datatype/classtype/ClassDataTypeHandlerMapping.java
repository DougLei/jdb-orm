package com.douglei.database.dialect.datatype.classtype;

import java.util.HashMap;
import java.util.Map;

import com.douglei.database.dialect.datatype.DataTypeHandler;
import com.douglei.database.dialect.datatype.UnsupportDataTypeHandlerCodeException;

/**
 * 
 * @author DougLei
 */
public class ClassDataTypeHandlerMapping {
	private final Map<Class<?>, ClassDataHandler> DATATYPE_HANDLER_MAP = new HashMap<Class<?>, ClassDataHandler>(16);
	
	public void register(ClassDataHandler classDataHandler) {
		DATATYPE_HANDLER_MAP.put(classDataHandler.supportClass(), classDataHandler);
	}

	/**
	 * 根据value的classType, 获取对应的DataTypeHandler
	 * @param value
	 * @return
	 */
	public DataTypeHandler getDataTypeHandlerByClassType(Object value) {
		ClassDataHandler dataTypeHandler = DATATYPE_HANDLER_MAP.get(value.getClass());
		if(dataTypeHandler == null) {
			throw new UnsupportDataTypeHandlerCodeException("目前无法处理class=["+value.getClass().getName()+"]的数据类型");
		}
		return dataTypeHandler;
	}
}
