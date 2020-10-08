package com.douglei.orm.dialect.impl.sqlserver;

import com.douglei.orm.dialect.impl.AbstractDialect;
import com.douglei.orm.dialect.impl.sqlserver.dbobject.DBObjectHandlerImpl;
import com.douglei.orm.dialect.impl.sqlserver.sqlhandler.SqlQueryHandlerImpl;
import com.douglei.orm.dialect.impl.sqlserver.sqlhandler.SqlStatementHandlerImpl;

/**
 * 
 * @author DougLei
 */
public class SqlServerDialect extends AbstractDialect{
	
	public SqlServerDialect() {
		initDataTypeContainer();
		super.objectHandler = new DBObjectHandlerImpl();
		super.sqlStatementHandler = new SqlStatementHandlerImpl();
		super.sqlQueryHandler = new SqlQueryHandlerImpl(sqlStatementHandler);
	}
	
	/**
	 * 初始化数据类型容器
	 */
	private void initDataTypeContainer() {
		// TODO 将实例注册到容器dataTypeContainer中
		
	}
}
