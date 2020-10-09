package com.douglei.orm.dialect.impl.mysql;

import com.douglei.orm.dialect.DialectType;
import com.douglei.orm.dialect.impl.AbstractDialect;
import com.douglei.orm.dialect.impl.mysql.dbobject.DBObjectHandlerImpl;
import com.douglei.orm.dialect.impl.mysql.sqlhandler.SqlQueryHandlerImpl;
import com.douglei.orm.dialect.impl.mysql.sqlhandler.SqlStatementHandlerImpl;

/**
 * 
 * @author DougLei
 */
public class MySqlDialect extends AbstractDialect{
	
	public MySqlDialect() {
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
	
	@Override
	public DialectType getType() {
		return DialectType.MYSQL;
	}
}
