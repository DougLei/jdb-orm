package com.douglei.orm.core.mapping.struct;

import java.sql.SQLException;

import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.core.mapping.struct.proc.ProcStructHandler;
import com.douglei.orm.core.mapping.struct.table.TableStructHandler;
import com.douglei.orm.core.mapping.struct.view.ViewStructHandler;

/**
 * 
 * @author DougLei
 */
public class StructHandlerPackageContext {
	private static final ThreadLocal<StructHandlerPackage> context = new ThreadLocal<StructHandlerPackage>();

	/**
	 * 结构包上下文初始化
	 * @param dataSourceWrapper
	 */
	public static void initialize(DataSourceWrapper dataSourceWrapper) {
		context.set(new StructHandlerPackage(dataSourceWrapper));
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
	 * 获取表结构的处理器
	 * @return
	 * @throws SQLException 
	 */
	public static TableStructHandler getTableStructHandler() throws SQLException {
		return context.get().getTableStructHandler();
	}

	/**
	 * 获取视图结构处理器
	 * @return
	 */
	public static ViewStructHandler getViewStructHandler() {
		return context.get().getViewStructHandler();
	}
	
	/**
	 * 获取存储过程结构处理器
	 * @return
	 */
	public static ProcStructHandler getProcStructHandler() {
		return context.get().getProcStructHandler();
	}
}
