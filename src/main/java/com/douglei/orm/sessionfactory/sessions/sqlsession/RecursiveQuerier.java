package com.douglei.orm.sessionfactory.sessions.sqlsession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.EnvironmentContext;
import com.douglei.orm.sessionfactory.sessions.SessionExecuteException;
import com.douglei.orm.sql.query.QuerySqlStatement;
import com.douglei.orm.sql.query.page.PageSqlStatement;
import com.douglei.orm.sql.statement.util.NameConvertUtil;

/**
 * 递归查询器
 * @author DougLei
 */
@SuppressWarnings({"rawtypes", "unchecked"})
class RecursiveQuerier extends AbstractRecursiveQuerier<RecursiveEntity>{
	private static final Logger logger = LoggerFactory.getLogger(RecursiveQuerier.class);
	
	private StringBuilder sql; // 递归查询sql
	private int sqlInitialLength; // 递归查询sql的初始长度
	private String orderByClause; // order by子句
	
	private HashSet<Object> counter; // 参数值的计数器, 防止数据异常导致的递归死循环; 例如id和parentId值相同等
	
	/**
	 * 
	 * @param entity
	 * @param sql
	 * @param parameters
	 */
	public RecursiveQuerier(RecursiveEntity entity, String sql, List<Object> parameters) {
		this(null, entity, sql, parameters);
	}
	/**
	 * 
	 * @param clazz
	 * @param entity
	 * @param sql
	 * @param parameters
	 */
	public RecursiveQuerier(Class clazz, RecursiveEntity entity, String sql, List<Object> parameters) {
		if(!entity.isContinue())
			return;
		
		this.clazz = clazz;
		this.entity = entity;
		if(clazz != null) 
			entity.setChildren(NameConvertUtil.property2Column(entity.getChildren()));// 结果集为class时, 需要先将children转换为column, 后续的list2Class时会再转换回来
		
		// 设置递归查询sql
		QuerySqlStatement statement = new QuerySqlStatement(sql, EnvironmentContext.getEnvironment().getDialect().getDatabaseType().extractOrderByClause());
		setSql(statement);
		
		// 设置参数值集合
		this.parameters = parameters;
	}
	
	// 分页递归时使用的构造函数
	RecursiveQuerier(Class clazz, RecursiveEntity entity, PageSqlStatement statement, List<Object> parameters, int parametersInitialLength) {
		this.clazz = clazz;
		this.entity = entity;
		setSql(statement);
		this.parameters = parameters;
		this.parametersInitialLength = parametersInitialLength;
	}
	
	// 设置递归查询sql
	private void setSql(QuerySqlStatement statement){
		this.sql = new StringBuilder(statement.getTotalLength() + 200);
		if(statement.getWithClause() != null)
			this.sql.append(statement.getWithClause()).append(' ');
		this.sql.append("SELECT JDB_ORM_R_Q_.* FROM (");
		this.sql.append(statement.getSql());
		this.sql.append(") JDB_ORM_R_Q_ WHERE ");
		
		// 设置递归查询sql的初始长度
		this.sqlInitialLength = this.sql.length();
		
		// 设置解析出的order by子句
		this.orderByClause = statement.getOrderByClause();
	}
	
	// 获取(第一次)递归查询的sql语句
	private String getRecursiveQuerySql() {
		setRecursiveConditionSQL(sql);
		
		if(orderByClause != null)
			sql.append(' ').append(orderByClause);
		logger.debug("(第一次)递归查询的sql语句为: {}", sql);
		return sql.toString();
	}
	
	// 重置
	private void reset() {
		// 重置递归查询sql
		sql.setLength(sqlInitialLength);
		
		// 重置参数值集合
		while(parameters.size() > parametersInitialLength)
			parameters.remove(parameters.size()-1);
	}
	
	// 获取(第二次及后续)递归查询的sql语句
	private String getRecursiveQuerySql(List<Map<String, Object>> parentList) {
		sql.append(entity.getParentColumn()).append(" IN (");
		for (Map<String, Object> map : parentList) {
			Object idValue = map.get(entity.getColumn());
			if(idValue == null)
				throw new SessionExecuteException("递归查询时, 查询出主键值为null的数据");
			if(counter.contains(idValue))
				throw new SessionExecuteException("递归查询时, 出现重复的主键值["+idValue+"]");
			
			sql.append("?,");
			parameters.add(idValue);
			counter.add(idValue);
		}
		sql.setLength(sql.length()-1);
		sql.append(')');
		
		if(orderByClause != null)
			sql.append(' ').append(orderByClause);
		logger.debug("(第二次及后续)递归查询的sql语句为: {}", sql);
		return sql.toString();
	}
	
	// 递归查询
	private void recursiveQuery(SqlSessionImpl session, List<Map<String, Object>> parentList){
		if(parentList.isEmpty())
			return;
		if(!entity.isContinue()) {
			parentList.forEach(parent -> parent.put(entity.getChildren(), Collections.emptyList()));
			return;
		}
		
		reset();
		List<Map<String, Object>> list = session.query(getRecursiveQuerySql(parentList), parameters);
		recursiveQuery(session, list);
		buildPCStruct(session, parentList, list);
	}
	
	/**
	 * 构建父子结构
	 * @param session
	 * @param parentList
	 * @param childrenList
	 */
	void buildPCStruct(SqlSessionImpl session, List<Map<String, Object>> parentList, List<Map<String, Object>> childrenList) {
		if(childrenList.isEmpty()) {
			parentList.forEach(parent -> parent.put(entity.getChildren(), Collections.emptyList()));
			return;
		}
		
		List<Map<String, Object>> clist = null;
		for (Map<String, Object> parent : parentList) {
			Object idValue = parent.get(entity.getColumn());
			for(int i=0;i<childrenList.size();i++) {
				if(!idValue.equals(childrenList.get(i).get(entity.getParentColumn())))
					continue;
				
				if(clist == null)
					clist = new ArrayList<Map<String, Object>>();
				clist.add(childrenList.remove(i--));
			}
			
			if(clist == null)
				clist = Collections.emptyList();
			parent.put(entity.getChildren(), (clazz == null || clist.isEmpty()?clist:session.listMap2listClass(clazz, clist)));
			clist = null;
		}
	}
	
	/**
	 * 执行递归查询, 内部使用
	 * @param session
	 * @return
	 */
	List<Map<String, Object>> execute_(SqlSessionImpl session) {
		if(entity == null)
			return Collections.emptyList();
		
		if(entity.getDeep() == -1 || entity.getDeep() > 0) {
			if(parameters == null) 
				parameters = new ArrayList<Object>();
			if(entity.getDeep() == -1)
				counter = new HashSet<Object>(parameters);
		}
		
		List<Map<String, Object>> list = session.query(getRecursiveQuerySql(), parameters);
		recursiveQuery(session, list);
		return list;
	}
	
	/**
	 * 执行递归查询
	 * @param session
	 * @return
	 */
	public List execute(SqlSessionImpl session) {
		List<Map<String, Object>> list = execute_(session);
		return (clazz == null || list.isEmpty())?list:session.listMap2listClass(clazz, list);
	}
}
