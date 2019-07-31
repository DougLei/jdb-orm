package com.douglei.orm.core.metadata.table.pk;

import java.util.HashMap;
import java.util.Map;

import com.douglei.orm.core.metadata.table.pk.impl.IncrementPrimaryKeyHandler;
import com.douglei.orm.core.metadata.table.pk.impl.UUID32PrimaryKeyHandler;
import com.douglei.orm.core.metadata.table.pk.impl.UUID36PrimaryKeyHandler;
import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public class PrimaryKeyHandlerContext {
	private static final Map<String, PrimaryKeyHandler> handlers = new HashMap<String, PrimaryKeyHandler>(4);
	static {
		handlers.put("uuid32", new UUID32PrimaryKeyHandler());
		handlers.put("uuid36", new UUID36PrimaryKeyHandler());
		handlers.put("increment", new IncrementPrimaryKeyHandler());
	}
	
	/**
	 * 
	 * @param primaryKeyHandler
	 * @return
	 */
	public static PrimaryKeyHandler getHandler(String primaryKeyHandler) {
		PrimaryKeyHandler handler = handlers.get(primaryKeyHandler);
		if(handler == null) {
			handler = (PrimaryKeyHandler) ConstructorUtil.newInstance(primaryKeyHandler);
			handlers.put(handler.getClass().getName(), handler);
		}
		return handler;
	}
}
