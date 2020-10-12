package com.douglei.orm.mapping.handler.object.procedure;

import java.sql.SQLException;

import com.douglei.orm.mapping.handler.object.DBConnection;
import com.douglei.orm.mapping.handler.object.view.ViewObjectHandler;
import com.douglei.orm.mapping.impl.procedure.metadata.ProcedureMetadata;
import com.douglei.orm.mapping.impl.view.metadata.ViewMetadata;

/**
 * 存储过程对象处理器
 * @author DougLei
 */
public class ProcedureObjectHandler extends ViewObjectHandler{
	public ProcedureObjectHandler(DBConnection connection) {
		super(connection);
	}

	@Override
	protected String getDropSql(String name) {
		return sqlStatementHandler.dropProcedure(name);
	}

	@Override
	protected Class<? extends ViewMetadata> getSerialClass() {
		return ProcedureMetadata.class;
	}

	@Override
	protected boolean nameExists(String name) throws SQLException {
		return connection.procedureExists(name);
	}

	@Override
	protected String queryCreateScript(String name) throws SQLException {
		return connection.queryProcedureScript(name);
	}
}
