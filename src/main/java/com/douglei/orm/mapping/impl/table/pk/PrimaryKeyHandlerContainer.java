package com.douglei.orm.mapping.impl.table.pk;

import java.util.HashMap;
import java.util.Map;

import com.douglei.orm.configuration.OrmException;

/**
 * 
 * @author DougLei
 */
public class PrimaryKeyHandlerContainer {
	private static final Map<String, PrimaryKeyHandler> HANDLER_MAP = new HashMap<String, PrimaryKeyHandler>(8);
	static {
		UUIDPrimaryKeyHandler uuid = new UUIDPrimaryKeyHandler();
		HANDLER_MAP.put(uuid.getType(), uuid);
		
		SequencePrimaryKeyHandler sequence = new SequencePrimaryKeyHandler();
		HANDLER_MAP.put(sequence.getType(), sequence);
	}
	
	/**
	 * 获取指定类型的主键处理器实例
	 * @param type
	 * @return
	 */
	public static PrimaryKeyHandler get(String type) {
		PrimaryKeyHandler handler = HANDLER_MAP.get(type);
		if(handler == null) {
			try {
				handler = (PrimaryKeyHandler)Class.forName(type).newInstance();
				HANDLER_MAP.put(handler.getType(), handler);
			} catch (Exception e) {
				throw new OrmException("获取"+type+"类型的主键处理器时出现异常, 请检查类型名称, 或自定义主键处理器实现类的路径是否正确", e);
			} 
		}
		return handler;
	}
}
