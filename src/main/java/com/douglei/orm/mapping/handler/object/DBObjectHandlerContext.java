package com.douglei.orm.mapping.handler.object;

import java.sql.SQLException;

import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;

/**
 * 
 * @author DougLei
 */
public class DBObjectHandlerContext {
	private static final ThreadLocal<DBObjectHandlers> OBJECT_HANDLERS_CONTEXT = new ThreadLocal<DBObjectHandlers>();

	/**
	 * 
	 * @param dataSource
	 */
	public static void init(DataSourceWrapper dataSource) {
		OBJECT_HANDLERS_CONTEXT.set(new DBObjectHandlers(dataSource));
	}
	
	/**
	 * 
	 */
	public static void destroy() {
		DBObjectHandlers handlers = OBJECT_HANDLERS_CONTEXT.get();
		if(handlers != null) {
			handlers.destroy();
			OBJECT_HANDLERS_CONTEXT.remove();
		}
	}

	/**
	 * 获取表对象的处理器
	 * @return
	 * @throws SQLException 
	 */
	public static TableObjectHandler getTableObjectHandler() throws SQLException {
		return OBJECT_HANDLERS_CONTEXT.get().getTableObjectHandler();
	}

	/**
	 * 获取视图对象处理器
	 * @return
	 */
	public static ViewObjectHandler getViewObjectHandler() {
		return OBJECT_HANDLERS_CONTEXT.get().getViewObjectHandler();
	}
	
	/**
	 * 获取存储过程对象处理器
	 * @return
	 */
	public static ProcedureObjectHandler getProcedureObjectHandler() {
		return OBJECT_HANDLERS_CONTEXT.get().getProcedureObjectHandler();
	}
}
