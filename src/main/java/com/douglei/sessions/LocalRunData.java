package com.douglei.sessions;

import com.douglei.database.dialect.Dialect;
import com.douglei.instances.expression.resolver.ExpressionResolverHandler;

/**
 * 当前系统运行时所需要的相关数据对象实例
 * @author DougLei
 */
public class LocalRunData {
	private static final ThreadLocal<RunData> RUN_DATA = new ThreadLocal<RunData>();
	
	private static RunData getRunData() {
		RunData runData = RUN_DATA.get();
		if(runData == null) {
			runData = new RunData();
			RUN_DATA.set(runData);
		}
		return runData;
	}
	
	public static void setDialect(Dialect dialect) {
		RunData runData = getRunData();
		if(runData.dialect == null) {
			runData.dialect = dialect;
		}
	}
	public static Dialect getDialect() {
		return getRunData().dialect;
	}
	
	public static ExpressionResolverHandler getExpressionResolverHandler() {
		RunData runData = getRunData();
		if(runData.expressionResolverHandler == null) {
			runData.expressionResolverHandler = ExpressionResolverHandler.newInstance();
		}
		return runData.expressionResolverHandler;
	}
}

class RunData{
	Dialect dialect;
	ExpressionResolverHandler expressionResolverHandler;
}

