package com.douglei.orm.sessionfactory.sessions.sqlsession;

import java.sql.CallableStatement;
import java.sql.Connection;

/**
 * 存储过程执行器
 * @author DougLei
 */
public interface ProcedureExecutor {
	
	/**
	 * 执行存储过程
	 * @see com.douglei.sessions.session.sql.impl.SQLSessionImpl.executeProcedure(String callableSqlContent, List<Object> callableParameters)
	 * @param connection 使用完成后<b>禁止关闭</b>, 系统会进行关闭, 通过该connection创建的其他对象需要自己手动关闭, 例如 {@link CallableStatement}
	 * @return 如果执行的存储过程有返回数据
	 * @throws ProcedureExecutionException
	 */
	Object execute(Connection connection)  throws ProcedureExecutionException;
}
