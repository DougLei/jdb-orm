package com.douglei.orm.core.mapping.struct.procedure;

import java.sql.SQLException;

import com.douglei.orm.core.mapping.struct.DBConnection;
import com.douglei.orm.core.mapping.struct.view.ViewStructHandler;
import com.douglei.orm.core.metadata.procedure.ProcedureMetadata;
import com.douglei.orm.core.metadata.view.ViewMetadata;

/**
 * 存储过程结构处理器
 * @author DougLei
 */
public class ProcedureStructHandler extends ViewStructHandler{
	public ProcedureStructHandler(DBConnection connection) {
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
