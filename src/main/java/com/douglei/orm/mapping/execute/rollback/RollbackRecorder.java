package com.douglei.orm.mapping.execute.rollback;

import java.util.LinkedList;

import com.douglei.orm.mapping.execute.struct.DBConnection;

/**
 * 回滚信息记录器
 * @author DougLei
 */
public class RollbackRecorder {
	private static final ThreadLocal<LinkedList<RollbackExecutor>> recorder = new ThreadLocal<LinkedList<RollbackExecutor>>();

	/**
	 * 记录回滚时的执行对象
	 * @param execMethod
	 * @param execObject
	 * @param connection
	 */
	public static void record(RollbackExecMethod execMethod, Object execObject, DBConnection connection) {
		LinkedList<RollbackExecutor> list = recorder.get();
		if(list == null) {
			list = new LinkedList<RollbackExecutor>();
			recorder.set(list);
		}
		list.add(new RollbackExecutor(execMethod, execObject, connection));
	}
	
	/**
	 * 获取回滚执行器集合
	 * @return
	 */
	public static LinkedList<RollbackExecutor> getRollbackExecutorList() {
		return recorder.get();
	}
	
	/**
	 * 清空记录
	 */
	public static void clear() {
		recorder.remove();
	}
}
