package com.douglei.orm.sessionfactory.sessions.sqlsession;

import java.util.List;
import java.util.Map;

import com.douglei.orm.configuration.environment.EnvironmentContext;
import com.douglei.orm.sql.query.page.PageResult;
import com.douglei.orm.sql.query.page.PageSqlStatement;
import com.douglei.orm.sql.statement.util.NameConvertUtil;

/**
 * 分页递归查询器
 * @author DougLei
 */
@SuppressWarnings({"rawtypes", "unchecked"})
class PageRecursiveQuerier extends AbstractRecursiveQuerier<PageRecursiveEntity>{
	
	private PageSqlStatement statement;
	private String conditionSQL; // 查询条件sql
	
	/**
	 * 
	 * @param entity
	 * @param sql
	 * @param parameters
	 */
	public PageRecursiveQuerier(PageRecursiveEntity entity, String sql, List<Object> parameters) {
		this(null, entity, sql, parameters);
	}
	/**
	 * 
	 * @param clazz
	 * @param entity
	 * @param sql
	 * @param parameters
	 */
	public PageRecursiveQuerier(Class clazz, PageRecursiveEntity entity, String sql, List<Object> parameters) {
		if(!entity.isContinue())
			return;
		
		this.clazz = clazz;
		this.entity = entity;
		if(clazz != null) 
			entity.setChildren(NameConvertUtil.property2Column(entity.getChildren()));// 结果集为class时, 需要先将children转换为column, 后续的list2Class时会再转换回来
		
		this.statement = new PageSqlStatement(sql, EnvironmentContext.getEnvironment().getDialect().getDatabaseType().extractOrderByClause());
		this.parameters = parameters;
		
		// 设置第一次查询时的条件sql和参数值
		StringBuilder conditionSQL = new StringBuilder(100);
		setRecursiveConditionSQL(conditionSQL);
		this.conditionSQL = conditionSQL.toString();
	}
	
	/**
	 * 执行分页递归查询
	 * @param session
	 * @return
	 */
	public PageResult execute(SqlSessionImpl session) {
		if(entity == null)
			return new PageResult(entity.getPageNum(), entity.getPageSize(), 0);
		
		long count = Long.parseLong(session.uniqueQuery_(statement.getCountSql() + " WHERE " + conditionSQL, parameters)[0].toString()); // 查询总数量
		PageResult pageResult = new PageResult(entity.getPageNum(), entity.getPageSize(), count);
		if(count > 0) {
			List<Map<String, Object>> list = session.query(statement.getPageQuerySql(EnvironmentContext.getEnvironment().getDialect().getSqlStatementHandler(), pageResult.getPageNum(), pageResult.getPageSize(), conditionSQL), parameters);
			if((entity.isContinue())) {
				// 重置参数值集合, 并添加新的主键值
				if(parameters != null) {
					while(parameters.size() > parametersInitialLength)
						parameters.remove(parameters.size()-1);
				}
				
				// 设置递归用的参数值数组
				Object[] values = new Object[list.size()];
				for(int i=0;i<list.size();i++)
					values[i] = list.get(i).get(entity.getColumn());
				entity.setValue(values);
				
				// 进行递归查询
				RecursiveQuerier rq = new RecursiveQuerier(clazz, entity, statement, parameters, parametersInitialLength);
				List childrenList = rq.execute(session);
				rq.buildPCStruct(session, list, childrenList);
			}
			pageResult.setResultDatas((clazz == null)?list:session.listMap2listClass(clazz, list));
		}
		return pageResult;
	}
}
