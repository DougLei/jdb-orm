package com.douglei.database.dialect.impl.oracle.db.procedure;

import com.douglei.database.dialect.db.procedure.ProcedureHandler;

/**
 * 
 * @author DougLei
 */
public class ProcedureHandlerImpl implements ProcedureHandler {

	@Override
	public boolean supportDirectlyReturnResultSet() {
		return false;
	}
}
