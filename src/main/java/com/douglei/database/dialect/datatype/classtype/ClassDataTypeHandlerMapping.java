package com.douglei.database.dialect.datatype.classtype;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.dialect.datatype.DataTypeHandler;
import com.douglei.database.dialect.datatype.UnsupportDataTypeHandlerCodeException;

/**
 * 
 * @author DougLei
 */
public class ClassDataTypeHandlerMapping {
	private static final Logger logger = LoggerFactory.getLogger(ClassDataTypeHandlerMapping.class);
	private final Map<Class<?>, ClassDataTypeHandler> DATATYPE_HANDLER_MAP = new HashMap<Class<?>, ClassDataTypeHandler>(16);
	
	public void initialRegister(ClassDataTypeHandler classDataHandler) {
		if(logger.isDebugEnabled()) {
			logger.debug("register {}", classDataHandler.toString());
		}
		Class<?>[] classes = classDataHandler.supportClasses();
		if(classes == null) {
			logger.debug("supportClasses is null");
		}else {
			for (Class<?> clz : classes) {
				DATATYPE_HANDLER_MAP.put(clz, classDataHandler);
			}
		}
	}

	/**
	 * 根据value的classType, 获取对应的DataTypeHandler
	 * @param value
	 * @return
	 */
	public DataTypeHandler getDataTypeHandlerByClassType(Object value) {
		ClassDataTypeHandler dataTypeHandler = DATATYPE_HANDLER_MAP.get(value.getClass());
		if(dataTypeHandler == null) {
			throw new UnsupportDataTypeHandlerCodeException("目前无法处理class=["+value.getClass().getName()+"]的数据类型");
		}
		return dataTypeHandler;
	}
}
