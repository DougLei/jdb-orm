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
	
	private static final Map<String, DataTypeHandler> DATATYPE_HANDLER_MAP = new HashMap<String, DataTypeHandler>(16);// DataTypeHandler映射集合
	
	/**
	 * <pre>
	 * 	将新的DataTypeHandler实例, put到DataTypeHandler Map集合中
	 * 	这个方法也给子类提供, 在static块中注册系统提供的一些DataTypeHandler
	 * </pre>
	 * @param dataTypeHandler
	 */
	protected static void register_(DataTypeHandler dataTypeHandler) {
		if(logger.isDebugEnabled()) {
			logger.debug("注册DataTypeHandler, code={}, 实例={}", dataTypeHandler.getCode(), dataTypeHandler.getClass().getName());
		}
		DATATYPE_HANDLER_MAP.put(dataTypeHandler.getCode(), dataTypeHandler);
	}
	
	/**
	 * 注册新的DataTypeHandler实例
	 * @param dataTypeHandler
	 */
	private void register(DataTypeHandler dataTypeHandler) {
		String code = dataTypeHandler.getCode();
		if(DATATYPE_HANDLER_MAP.containsKey(code)) {
			throw new RepeatDataTypeHandlerException("已经存在code=["+code+"]的映射实例 " + DATATYPE_HANDLER_MAP.get(code).getClass().getName())  ;
		}
		register_(dataTypeHandler);
	}
	
	/**
	 * 根据code, 获取DataTypeHandler实例
	 * @param code
	 * @return
	 */
	public DataTypeHandler getDataTypeHandlerByCode(String code) {
		DataTypeHandler dataTypeHandler = DATATYPE_HANDLER_MAP.get(code);
		if(dataTypeHandler == null) {
			logger.debug("没有获取到code=[{}]的DataTypeHandler实例, 尝试加载该自定义DataTypeHandler实现子类", code);
			Object obj = ConstructorUtil.newInstance(code);
			if(!(obj instanceof DataTypeHandler)) {
				throw new ClassCastException("类["+code+"]必须继承["+DataTypeHandler.class.getName()+"]");
			}
			dataTypeHandler = (DataTypeHandler) obj;
			register(dataTypeHandler);
		}
		logger.debug("获取code值为{} 的{}实例", code, dataTypeHandler.getClass());
		return dataTypeHandler;
	}
	
	/**
	 * 根据value的classType, 获取对应的DataTypeHandler
	 * @param value
	 * @return
	 */
	public DataTypeHandler getDataTypeHandlerByValueClassType(Object value) {
		return null;
	}
	
	/**
	 * 根据数据库的ResultSetMetadata的ColumnType, 获取对应的DataTypeHandler
	 * @param columnType
	 * @return
	 */
	public DataTypeHandler getDataTypeHandlerByDatabaseColumnType(int columnType) {
		return null;
	}
}
