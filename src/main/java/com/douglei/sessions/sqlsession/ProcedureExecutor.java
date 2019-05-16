package com.douglei.sessions.sqlsession;

import java.sql.Connection;

/**
 * 
 * @author DougLei
 */
public interface ProcedureExecutor {

	/**
	 * 执行存储过程
	 * @param connection 使用完成后<b>禁止关闭</b>, 系统会进行关闭, 通过该connection创建的CallableStatement需要自己手动关闭
	 * @return 如果执行的存储过程有返回数据
	 */
	Object execute(Connection connection);
}
