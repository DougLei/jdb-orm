package com.douglei.database.dialect.datatype.ormtype;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.dialect.datatype.DataTypeHandler;
import com.douglei.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public class OrmDataTypeHandlerMapping {
	private static final Logger logger = LoggerFactory.getLogger(OrmDataTypeHandlerMapping.class);
	private final Map<String, OrmDataTypeHandler> DATATYPE_HANDLER_MAP = new HashMap<String, OrmDataTypeHandler>(16);
	
	public void register(OrmDataTypeHandler ormDataTypeHandler) {
		if(logger.isDebugEnabled()) {
			logger.debug("register {}", ormDataTypeHandler.toString());
		}
		DATATYPE_HANDLER_MAP.put(ormDataTypeHandler.getCode(), ormDataTypeHandler);
	}
	
	/**
	 * 根据code, 获取DataTypeHandler实例
	 * @param code
	 * @return
	 */
	public DataTypeHandler getDataTypeHandlerByClassType(String code) {
		OrmDataTypeHandler dataTypeHandler = DATATYPE_HANDLER_MAP.get(code);
		if(dataTypeHandler == null) {
			logger.debug("没有获取到code=[{}]的DataTypeHandler实例, 尝试加载该自定义OrmDataTypeHandler实现子类", code);
			Object obj = ConstructorUtil.newInstance(code);
			if(!(obj instanceof OrmDataTypeHandler)) {
				throw new ClassCastException("类["+code+"]必须继承["+OrmDataTypeHandler.class.getName()+"]");
			}
			dataTypeHandler = (OrmDataTypeHandler) obj;
			dynamicRegister(dataTypeHandler);
		}
		return dataTypeHandler;
	}
	
	private void dynamicRegister(OrmDataTypeHandler ormDataTypeHandler) {
		String code = ormDataTypeHandler.getCode();
		if(DATATYPE_HANDLER_MAP.containsKey(code)) {
			throw new RepeatedDataTypeHandlerCodeException("[dynamicRegister] 已经存在code="+code+" 的OrmDataTypeHandler实例, " + DATATYPE_HANDLER_MAP.get(code).toString());
		}
		register(ormDataTypeHandler);
	}
}
