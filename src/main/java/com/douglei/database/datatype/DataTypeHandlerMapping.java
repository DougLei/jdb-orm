package com.douglei.database.datatype;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.utils.StringUtil;

/**
 * 数据类型处理器映射
 * @author DougLei
 */
public class DataTypeHandlerMapping {
	private static final Logger logger = LoggerFactory.getLogger(DataTypeHandlerMapping.class);
	
	private static final Map<String, DataTypeHandler> DATATYPE_HANDLER_MAP = new HashMap<String, DataTypeHandler>(16);
	static {
		
	}
	
	/**
	 * 获取DataTypeHandler实例
	 * @param dataType
	 * @return
	 */
	public static DataTypeHandler getDataTypeHandler(String dataType) {
		if(logger.isDebugEnabled()) {
			logger.debug("获取dataType值为{} 的{}实例", dataType, DataTypeHandler.class);
		}
		if(StringUtil.isEmpty(dataType)) {
			logger.error("dataType的参数值不能为空");
			throw new NullPointerException("dataType的参数值不能为空");
		}
		
		DataTypeHandler dataTypeHandler = DATATYPE_HANDLER_MAP.get(dataType.trim().toUpperCase());
		if(dataTypeHandler == null) {
			logger.error("系统目前不支持[{}], 目前支持的dataType值包括:{}", dataType, DATATYPE_HANDLER_MAP.keySet());
			throw new NullPointerException("系统目前不支持["+dataType+"], 目前支持的dataType值包括:"+DATATYPE_HANDLER_MAP.keySet());
		}
		if(logger.isDebugEnabled()) {
			logger.debug("获取dataType值为{} 的{}实例", dataType, dataTypeHandler.getClass());
		}
		return dataTypeHandler;
	}
	
	/**
	 * 注册新的DataTypeHandler实例
	 * @param dataTypeHandler
	 */
	public static void register(DataTypeHandler dataTypeHandler) {
		if(dataTypeHandler == null) {
			logger.error("要注册的 {} 实例不能为空", DataTypeHandler.class);
			throw new NullPointerException("要注册的 "+DataTypeHandler.class+" 实例不能为空");
		}
		
		String dataType = dataTypeHandler.getClass().getName();
		if(DATATYPE_HANDLER_MAP.containsKey(dataType)) {
			logger.error("已经存在dataTypeHandler-dataType值为[{}]的映射实例:{}", dataType, DATATYPE_HANDLER_MAP.get(dataType).getClass());
			throw new RepeatDataTypeHandlerException("已经存在dataTypeHandler-dataType值为["+dataType+"]的映射实例:" + DATATYPE_HANDLER_MAP.get(dataType).getClass());
		}
		DATATYPE_HANDLER_MAP.put(dataType, dataTypeHandler);
		if(logger.isDebugEnabled()) {
			logger.debug("注册dataType值为{} 的{}实例", dataType, dataTypeHandler.getClass());
		}
	}
}
