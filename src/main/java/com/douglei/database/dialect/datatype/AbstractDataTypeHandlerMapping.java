package com.douglei.database.dialect.datatype;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.utils.reflect.ConstructorUtil;

/**
 * 每种数据库都有自己的DataTypeHandlerMapping实现
 * @author DougLei
 */
public abstract class AbstractDataTypeHandlerMapping {
	private static final Logger logger = LoggerFactory.getLogger(AbstractDataTypeHandlerMapping.class);
	private static final int count = 16;
	
	private final Map<String, DataTypeHandler> CODE_DATATYPE_HANDLER_MAP = new HashMap<String, DataTypeHandler>(count);// DataTypeHandler映射集合------------以getCode()值为key
	private final Map<Class<?>, DataTypeHandler> CLASS_DATATYPE_HANDLER_MAP = new HashMap<Class<?>, DataTypeHandler>(count);// DataTypeHandler映射集合------------以supportClass()值为key
	private final Map<Integer, DataTypeHandler> COLUMNTYPE_DATATYPE_HANDLER_MAP = new HashMap<Integer, DataTypeHandler>(count);// DataTypeHandler映射集合------------以supportColumnType()值为key
	
	/**
	 * <pre>
	 * 	将新的DataTypeHandler实例, put到DataTypeHandler Map集合中
	 * 	这个方法也给子类提供, 在static块中注册系统提供的一些DataTypeHandler
	 * </pre>
	 * @param dataTypeHandler
	 */
	protected void register_(DataTypeHandler dataTypeHandler) {
		if(logger.isDebugEnabled()) {
			logger.debug("注册DataTypeHandler, code={}, 实例={}", dataTypeHandler.getCode(), dataTypeHandler.toString());
		}
		CODE_DATATYPE_HANDLER_MAP.put(dataTypeHandler.getCode(), dataTypeHandler);
		CLASS_DATATYPE_HANDLER_MAP.put(dataTypeHandler.supportClass(), dataTypeHandler);
		COLUMNTYPE_DATATYPE_HANDLER_MAP.put(dataTypeHandler.supportColumnType(), dataTypeHandler);
	}
	
	/**
	 * 注册新的DataTypeHandler实例
	 * @param dataTypeHandler
	 */
	private void register(DataTypeHandler dataTypeHandler) {
		if(logger.isDebugEnabled()) {
			if(CODE_DATATYPE_HANDLER_MAP.containsKey(dataTypeHandler.getCode())) {
				logger.debug("已经存在code={}的DataTypeHandler实例={}", dataTypeHandler.getCode(), CODE_DATATYPE_HANDLER_MAP.get(dataTypeHandler.getCode()).toString());
				logger.debug("【覆盖】用当前新实例={}", dataTypeHandler.toString());
			}
			if(CLASS_DATATYPE_HANDLER_MAP.containsKey(dataTypeHandler.supportClass())) {
				logger.debug("已经存在supportClass={}的DataTypeHandler实例={}", dataTypeHandler.supportClass(), CLASS_DATATYPE_HANDLER_MAP.get(dataTypeHandler.supportClass()).toString());
				logger.debug("【覆盖】用当前新实例={}", dataTypeHandler.toString());
			}
			if(COLUMNTYPE_DATATYPE_HANDLER_MAP.containsKey(dataTypeHandler.supportColumnType())) {
				logger.debug("已经存在supportColumnType={}的DataTypeHandler实例={}", dataTypeHandler.supportColumnType(), COLUMNTYPE_DATATYPE_HANDLER_MAP.get(dataTypeHandler.supportColumnType()).toString());
				logger.debug("【覆盖】用当前新实例={}", dataTypeHandler.toString());
			}
		}
		register_(dataTypeHandler);
	}
	
	/**
	 * 根据code, 获取DataTypeHandler实例
	 * @param code
	 * @return
	 */
	public DataTypeHandler getDataTypeHandlerByCode(String code) {
		DataTypeHandler dataTypeHandler = CODE_DATATYPE_HANDLER_MAP.get(code);
		if(dataTypeHandler == null) {
			logger.debug("没有获取到code=[{}]的DataTypeHandler实例, 尝试加载该自定义DataTypeHandler实现子类", code);
			Object obj = ConstructorUtil.newInstance(code);
			if(!(obj instanceof DataTypeHandler)) {
				throw new ClassCastException("类["+code+"]必须继承["+DataTypeHandler.class.getName()+"]");
			}
			dataTypeHandler = (DataTypeHandler) obj;
			register(dataTypeHandler);
		}
		logger.debug("获取code={} 的DataTypeHandler实例 {}", code, dataTypeHandler.toString());
		return dataTypeHandler;
	}
	
	/**
	 * 根据value的classType, 获取对应的DataTypeHandler
	 * @param value
	 * @return
	 */
	public DataTypeHandler getDataTypeHandlerByValueClassType(Object value) {
		DataTypeHandler dataTypeHandler = CLASS_DATATYPE_HANDLER_MAP.get(value.getClass());
		if(dataTypeHandler == null) {
			throw new UnSupportDataTypeHandlerException("目前不支持处理 ["+value.getClass().getName()+"]类型");
		}
		if(logger.isDebugEnabled()) {
			logger.debug("获取supportClass={} 的DataTypeHandler实例 {}", value.getClass().getName(), dataTypeHandler.toString());
		}
		return dataTypeHandler;
	}
	
	/**
	 * 根据数据库的ResultSetMetadata的ColumnType, 获取对应的DataTypeHandler
	 * @param columnType
	 * @return
	 */
	public DataTypeHandler getDataTypeHandlerByDatabaseColumnType(int columnType) {
		DataTypeHandler dataTypeHandler = COLUMNTYPE_DATATYPE_HANDLER_MAP.get(columnType);
		if(dataTypeHandler == null) {
			throw new UnSupportDataTypeHandlerException("目前不支持处理 columnType="+columnType+"");
		}
		if(logger.isDebugEnabled()) {
			logger.debug("获取supportColumnType={} 的DataTypeHandler实例 {}", columnType, dataTypeHandler.toString());
		}
		return dataTypeHandler;
	}
}
