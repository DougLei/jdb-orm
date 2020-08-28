package com.douglei.orm.core.dialect.mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 回滚信息记录器
 * @author DougLei
 */
class RollbackRecorder {
	private static final ThreadLocal<List<RollbackExecutor>> recorder = new ThreadLocal<List<RollbackExecutor>>();

	/**
	 * 记录回滚时的执行对象
	 * @param execMethod
	 * @param execObject
	 */
	public static void record(RollbackExecMethod execMethod, Object execObject) {
		List<RollbackExecutor> list = recorder.get();
		if(list == null) {
			list = new ArrayList<RollbackExecutor>();
			recorder.set(list);
		}
		list.add(new RollbackExecutor(execMethod, execObject));
	}
	
	/**
	 * 获取回滚执行器集合
	 * @return
	 */
	public static List<RollbackExecutor> getRollbackExecutorList() {
		return recorder.get();
	}
	
	/**
	 * 清空记录
	 */
	public static void clear() {
		recorder.remove();
	}
}
