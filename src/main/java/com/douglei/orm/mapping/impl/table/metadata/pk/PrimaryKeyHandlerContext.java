package com.douglei.orm.mapping.impl.table.metadata.pk;

import java.util.HashMap;
import java.util.Map;

import com.douglei.orm.mapping.impl.table.metadata.pk.impl.SequencePrimaryKeyHandler;
import com.douglei.orm.mapping.impl.table.metadata.pk.impl.UUID32PrimaryKeyHandler;
import com.douglei.orm.mapping.impl.table.metadata.pk.impl.UUID36PrimaryKeyHandler;
import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public class PrimaryKeyHandlerContext {
	private static Map<String, PrimaryKeyHandler> handlers = new HashMap<String, PrimaryKeyHandler>(8);
	static {
		handlers.put("uuid32", new UUID32PrimaryKeyHandler());
		handlers.put("uuid36", new UUID36PrimaryKeyHandler());
		handlers.put("sequence", new SequencePrimaryKeyHandler());
	}
	
	/**
	 * 
	 * @param primaryKeyHandler
	 * @return
	 */
	public static PrimaryKeyHandler getHandler(String primaryKeyHandler) {
		if(StringUtil.isEmpty(primaryKeyHandler)) 
			return null;
		PrimaryKeyHandler handler = handlers.get(primaryKeyHandler);
		if(handler == null) {
			handler = (PrimaryKeyHandler) ConstructorUtil.newInstance(primaryKeyHandler);
			handlers.put(primaryKeyHandler, handler);
		}
		return handler;
	}
}
