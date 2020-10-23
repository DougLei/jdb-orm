package com.douglei.orm.mapping.handler.object;

import java.sql.SQLException;

import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.mapping.handler.object.procedure.ProcedureObjectHandler;
import com.douglei.orm.mapping.handler.object.table.TableObjectHandler;
import com.douglei.orm.mapping.handler.object.view.ViewObjectHandler;

/**
 * 
 * @author DougLei
 */
public class ObjectHandlerPackageContext {
	private static final ThreadLocal<ObjectHandlerPackage> context = new ThreadLocal<ObjectHandlerPackage>();

	/**
	 * 结构包上下文初始化
	 * @param dataSourceWrapper
	 */
	public static void initialize(DataSourceWrapper dataSourceWrapper) {
		context.set(new ObjectHandlerPackage(dataSourceWrapper));
	}
	
	/**
	 * 结构包上下文的销毁
	 * @param dataSourceWrapper
	 */
	public static void destroy() {
		context.get().destroy();
		context.remove();
	}

	/**
	 * 获取表对象的处理器
	 * @return
	 * @throws SQLException 
	 */
	public static TableObjectHandler getTableObjectHandler() throws SQLException {
		return context.get().getTableObjectHandler();
	}

	/**
	 * 获取视图对象处理器
	 * @return
	 */
	public static ViewObjectHandler getViewObjectHandler() {
		return context.get().getViewObjectHandler();
	}
	
	/**
	 * 获取存储过程对象处理器
	 * @return
	 */
	public static ProcedureObjectHandler getProcedureObjectHandler() {
		return context.get().getProcedureObjectHandler();
	}
}
