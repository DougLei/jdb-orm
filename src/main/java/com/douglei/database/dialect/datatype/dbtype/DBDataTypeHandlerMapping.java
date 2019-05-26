package com.douglei.database.dialect.datatype.dbtype;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.dialect.datatype.RepeatedDataTypeHandlerException;
import com.douglei.database.dialect.datatype.UnsupportDataTypeHandlerException;
import com.douglei.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public class DBDataTypeHandlerMapping {
	private static final Logger logger = LoggerFactory.getLogger(DBDataTypeHandlerMapping.class);
	private final Map<String, DBDataTypeHandler> DB_DATA_TYPE_HANDLER_MAP = new HashMap<String, DBDataTypeHandler>(16);
	
	public void register(DBDataTypeHandler dbDataTypeHandler) {
		if(logger.isDebugEnabled()) {
			logger.debug("register {}", dbDataTypeHandler.toString());
		}
		String typeName = dbDataTypeHandler.getTypeName();
		if(DB_DATA_TYPE_HANDLER_MAP.containsKey(typeName)) {
			throw new RepeatedDataTypeHandlerException("[dynamicRegister] ["+dbDataTypeHandler.toString()+"] 和 ["+DB_DATA_TYPE_HANDLER_MAP.get(typeName).toString()+"] 的typeName值相同, 请修改");
		}
		DB_DATA_TYPE_HANDLER_MAP.put(dbDataTypeHandler.getTypeName(), dbDataTypeHandler);
	}
	
	/**
	 * 根据typeName, 获取DBTypeHandler实例
	 * @param typeName
	 * @return
	 */
	public DBDataTypeHandler getDataTypeHandlerByDBTypeName(String typeName) {
		DBDataTypeHandler dbDataTypeHandler = DB_DATA_TYPE_HANDLER_MAP.get(typeName.toUpperCase());
		if(dbDataTypeHandler == null) {
			logger.debug("没有获取到typeName=[{}]的DBDataTypeHandler实例, 尝试加载该自定义DBDataTypeHandler实现子类", typeName);
			Object obj = null;
			try {
				obj = ConstructorUtil.newInstance(typeName);
			} catch (Exception e) {
				throw new UnsupportDataTypeHandlerException("目前无法处理DBTypeName=["+typeName+"]的数据类型");
			}
			if(!(obj instanceof DBDataTypeHandler)) {
				throw new ClassCastException("typeName=["+typeName+"]的类必须继承["+DBDataTypeHandler.class.getName()+"]");
			}
			dbDataTypeHandler = (DBDataTypeHandler) obj;
			register(dbDataTypeHandler);
		}
		return dbDataTypeHandler;
	}
}