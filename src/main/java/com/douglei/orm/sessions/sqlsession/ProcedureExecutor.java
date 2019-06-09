package com.douglei.orm.sessions.sqlsession;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 * @author DougLei
 */
public interface ProcedureExecutor {
	
	/**
	 * 执行存储过程
	 * @see com.douglei.sessions.session.sql.impl.SQLSessionImpl.executeProcedure(String callableSqlContent, List<Object> callableParameters)
	 * @param connection 使用完成后<b>禁止关闭</b>, 系统会进行关闭, 通过该connection创建的其他对象需要自己手动关闭, 例如CallableStatement
	 * @return 如果执行的存储过程有返回数据
	 * @throws SQLException
	 */
	Object execute(Connection connection)  throws SQLException;
}
