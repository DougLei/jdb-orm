package com.douglei.orm.dialect.impl.oracle;

import com.douglei.orm.dialect.impl.AbstractDialect;
import com.douglei.orm.dialect.impl.oracle.dbobject.DBObjectHandlerImpl;
import com.douglei.orm.dialect.impl.oracle.sqlhandler.SqlQueryHandlerImpl;
import com.douglei.orm.dialect.impl.oracle.sqlhandler.SqlStatementHandlerImpl;

/**
 * 
 * @author DougLei
 */
public class OracleDialect extends AbstractDialect{

	public OracleDialect() {
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
