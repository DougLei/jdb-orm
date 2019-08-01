package com.douglei.orm.core.metadata.table.pk;

import java.util.HashMap;
import java.util.Map;

import com.douglei.orm.core.metadata.table.pk.impl.SequencePrimaryKeyHandler;
import com.douglei.orm.core.metadata.table.pk.impl.UUID32PrimaryKeyHandler;
import com.douglei.orm.core.metadata.table.pk.impl.UUID36PrimaryKeyHandler;
import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public class PrimaryKeyHandlerContext {
	private static final Map<String, PrimaryKeyHandler> handlers = new HashMap<String, PrimaryKeyHandler>(4);
	static {
		registerHandler(new UUID32PrimaryKeyHandler());
		registerHandler(new UUID36PrimaryKeyHandler());
		registerHandler(new SequencePrimaryKeyHandler());
	}
	
	private static void registerHandler(PrimaryKeyHandler handler) {
		handlers.put(handler.getName(), handler);
	}
	
	/**
	 * 
	 * @param primaryKeyHandler
	 * @return
	 */
	public static PrimaryKeyHandler getHandler(String primaryKeyHandler) {
		if(StringUtil.isEmpty(primaryKeyHandler)) {
			return null;
		}
		PrimaryKeyHandler handler = handlers.get(primaryKeyHandler);
		if(handler == null) {
			handler = (PrimaryKeyHandler) ConstructorUtil.newInstance(primaryKeyHandler);
			registerHandler(handler);
		}
		return handler;
	}
}
