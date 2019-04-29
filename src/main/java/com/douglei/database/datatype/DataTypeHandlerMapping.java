package com.douglei.database.datatype;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.datatype.impl.ObjectDataTypeHandler;
import com.douglei.utils.StringUtil;

/**
 * 数据类型处理器映射
 * @author DougLei
 */
public class DataTypeHandlerMapping {
	private static final Logger logger = LoggerFactory.getLogger(DataTypeHandlerMapping.class);
	
	private static final ObjectDataTypeHandler defaultDataTypeHandler = ObjectDataTypeHandler.singleInstance();
	private static final Map<String, DataTypeHandler> DATATYPE_HANDLER_MAP = new HashMap<String, DataTypeHandler>(16);
	static {
		
		
		
		
	}
	
	
	public static DataTypeHandler getDefaultDataTypeHandler() {
		return defaultDataTypeHandler;
	}
	
	/**
	 * 获取DataTypeHandler实例
	 * @param dataType
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static DataTypeHandler getDataTypeHandler(String dataType) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		logger.debug("获取dataType值为{} 的{}实例", dataType, DataTypeHandler.class);
		DataTypeHandler dataTypeHandler = null;
		
		if(StringUtil.isEmpty(dataType)) {
			logger.debug("没有指定dataType, 使用默认的DataTypeHandler: {}",  defaultDataTypeHandler.getClass());
			dataTypeHandler = defaultDataTypeHandler;
		}else {
			dataType = dataType.trim();
			dataTypeHandler = DATATYPE_HANDLER_MAP.get(dataType);
			if(dataTypeHandler == null) {
				logger.debug("没有获取到dataType=[{}]的实例, 尝试加载该自定义DataTypeHandler类", dataType);
				Object obj = Class.forName(dataType).newInstance();
				if(!(obj instanceof DataTypeHandler)) {
					throw new ClassCastException("类["+dataType+"]必须实现["+DataTypeHandler.class+"]接口");
				}
				dataTypeHandler = (DataTypeHandler) obj;
				register(dataTypeHandler);
			}
		}
		
		logger.debug("获取dataType值为{} 的{}实例", dataType, dataTypeHandler.getClass());
		return dataTypeHandler;
	}
	
	/**
	 * 注册新的DataTypeHandler实例
	 * @param dataTypeHandler
	 */
	private static void register(DataTypeHandler dataTypeHandler) {
		String dataTypeHandlerClassName = dataTypeHandler.getClass().getName();
		if(DATATYPE_HANDLER_MAP.containsKey(dataTypeHandlerClassName)) {
			logger.error("已经存在dataTypeHandlerClassName=[{}]的映射实例", dataTypeHandlerClassName);
			throw new RepeatDataTypeHandlerException("已经存在dataTypeHandlerClassName=["+dataTypeHandlerClassName+"]的映射实例");
		}
		DATATYPE_HANDLER_MAP.put(dataTypeHandlerClassName, dataTypeHandler);
		logger.debug("注册dataTypeHandlerClassName=[{}]的实例", dataTypeHandlerClassName);
	}
}
