package com.douglei.orm.sessionfactory.sessions.sqlsession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.environment.datasource.ConnectionEntity;
import com.douglei.orm.sessionfactory.sessions.AbstractSession;
import com.douglei.orm.sessionfactory.sessions.SessionExecuteException;
import com.douglei.orm.sql.query.page.PageResult;
import com.douglei.orm.sql.query.page.PageSqlStatement;
import com.douglei.orm.sql.statement.AutoIncrementID;
import com.douglei.orm.sql.statement.InsertResult;
import com.douglei.orm.sql.statement.StatementExecutionException;
import com.douglei.orm.sql.statement.StatementHandler;
import com.douglei.orm.sql.statement.util.NameConvertUtil;
import com.douglei.tools.ExceptionUtil;
import com.douglei.tools.reflect.ClassUtil;
import com.douglei.tools.reflect.IntrospectorUtil;

/**
 * 执行sql语句的session实现类
 * @author DougLei
 */
public class SqlSessionImpl extends AbstractSession implements SqlSession{
	private static final Logger logger = LoggerFactory.getLogger(SqlSessionImpl.class);
	private Map<String, StatementHandler> statementHandlerCache;
	
	public SqlSessionImpl(ConnectionEntity connection, Environment environment) {
		super(connection, environment);
	}
	
	/**
	 * 获取StatementHandler
	 * @param sql
	 * @param parameters
	 * @param autoIncrementID
	 * @return
	 */
	private StatementHandler getStatementHandler(String sql, List<Object> parameters, AutoIncrementID autoIncrementID){
		logger.debug("执行的sql语句为: {}", sql);
		logger.debug("执行的sql参数值集合为: {}", parameters);
		
		StatementHandler statementHandler = null;
		String key = DigestUtils.md5Hex(sql);
		if(statementHandlerCache == null) {
			statementHandlerCache = new HashMap<String, StatementHandler>(8);
		}else {
			statementHandler = statementHandlerCache.get(key);
		}
		
		if(statementHandler == null) {
			logger.debug("缓存中不存在相关的StatementHandler实例, 创建实例并放入缓存中");
			
			statementHandler = connection.createStatementHandler(sql, parameters, autoIncrementID);
			if(statementHandler.supportCache())
				statementHandlerCache.put(key, statementHandler);
		}
		return statementHandler;
	}
	
	@Override
	public List<Map<String, Object>> query(String sql, List<Object> parameters) {
		StatementHandler statementHandler = getStatementHandler(sql, parameters, null);
		try {
			return statementHandler.executeQueryResultList(parameters);
		} catch (StatementExecutionException e) {
			logger.error("在查询数据时出现异常: {}", ExceptionUtil.getStackTrace(e));
			throw new SessionExecuteException("在查询数据时出现异常", e);
		} 
	}
	
	@Override
	public <T> List<T> query(Class<T> clazz, String sql) {
		return query(clazz, sql, null);
	}

	@Override
	public <T> List<T> query(Class<T> clazz, String sql, List<Object> parameters) {
		List<Map<String, Object>> listMap = query(sql, parameters);
		if(listMap.isEmpty())
			return Collections.emptyList();
		return listMap2listClass(clazz, listMap);
	}
	
	@Override
	public List<Object[]> query_(String sql, List<Object> parameters) {
		StatementHandler statementHandler = getStatementHandler(sql, parameters, null);
		try {
			return statementHandler.executeQueryResultList_(parameters);
		} catch (StatementExecutionException e) {
			logger.error("在查询数据时出现异常: {}", ExceptionUtil.getStackTrace(e));
			throw new SessionExecuteException("在查询数据时出现异常", e);
		} 
	}
	
	@Override
	public Map<String, Object> uniqueQuery(String sql, List<Object> parameters) {
		StatementHandler statementHandler = getStatementHandler(sql, parameters, null);
		try {
			return statementHandler.executeQueryUniqueResult(parameters);
		} catch (StatementExecutionException e) {
			logger.error("在查询数据时出现异常: {}", ExceptionUtil.getStackTrace(e));
			throw new SessionExecuteException("在查询数据时出现异常", e);
		}
	}
	
	@Override
	public <T> T uniqueQuery(Class<T> clazz, String sql) {
		return uniqueQuery(clazz, sql, null);
	}

	@Override
	public <T> T uniqueQuery(Class<T> clazz, String sql, List<Object> parameters) {
		Map<String, Object> map = uniqueQuery(sql, parameters);
		if(map == null)
			return null;
		return map2Class(clazz, map);
	}
	
	@Override
	public Object[] uniqueQuery_(String sql, List<Object> parameters) {
		StatementHandler statementHandler = getStatementHandler(sql, parameters, null);
		try {
			return statementHandler.executeQueryUniqueResult_(parameters);
		} catch (StatementExecutionException e) {
			logger.error("在查询数据时出现异常: {}", ExceptionUtil.getStackTrace(e));
			throw new SessionExecuteException("在查询数据时出现异常", e);
		}
	}
	
	@Override
	public List<Map<String, Object>> limitQuery(int startRow, int length, String sql, List<Object> parameters){
		StatementHandler statementHandler = getStatementHandler(sql, parameters, null);
		try {
			return statementHandler.executeLimitQueryResultList(startRow, length, parameters);
		} catch (StatementExecutionException e) {
			logger.error("在查询数据时出现异常: {}", ExceptionUtil.getStackTrace(e));
			throw new SessionExecuteException("在查询数据时出现异常", e);
		}
	}
	
	@Override
	public <T> List<T> limitQuery(Class<T> clazz, int startRow, int length, String sql){
		return limitQuery(clazz, startRow, length, sql, null);
	}
	
	@Override
	public <T> List<T> limitQuery(Class<T> clazz, int startRow, int length, String sql, List<Object> parameters){
		List<Map<String, Object>> listMap = limitQuery(startRow, length, sql, parameters);
		if(listMap.isEmpty())
			return Collections.emptyList();
		return listMap2listClass(clazz, listMap);
	}
	
	@Override
	public List<Object[]> limitQuery_(int startRow, int length, String sql, List<Object> parameters){
		StatementHandler statementHandler = getStatementHandler(sql, parameters, null);
		try {
			return statementHandler.executeLimitQueryResultList_(startRow, length, parameters);
		} catch (StatementExecutionException e) {
			logger.error("在查询数据时出现异常: {}", ExceptionUtil.getStackTrace(e));
			throw new SessionExecuteException("在查询数据时出现异常", e);
		}
	}
	
	@Override
	public long countQuery(String sql, List<Object> parameters) {
		PageSqlStatement statement = new PageSqlStatement(sql, environment.getDialect().getDatabaseType().extractOrderByClause());
		return Long.parseLong(uniqueQuery_(statement.getCountSql(), parameters)[0].toString());
	}

	// 分页查询; 内部方法, 不考虑泛型
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private PageResult pageQuery_(Class clazz, int pageNum, int pageSize, String sql, List<Object> parameters) {
		if(pageNum < 0) pageNum = 1;
		if(pageSize < 0) pageSize = 10;
		
		PageSqlStatement statement = new PageSqlStatement(sql, environment.getDialect().getDatabaseType().extractOrderByClause());
		long count = Long.parseLong(uniqueQuery_(statement.getCountSql(), parameters)[0].toString()); // 查询总数量
		PageResult pageResult = new PageResult(pageNum, pageSize, count);
		if(count > 0) {
			List list = query(statement.getPageQuerySql(environment.getDialect().getSqlStatementHandler(), pageResult.getPageNum(), pageResult.getPageSize()), parameters);
			if(clazz != null && !list.isEmpty()) 
				list = listMap2listClass(clazz, list);
			pageResult.setResultDatas(list);
		}
		return pageResult;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String sql, List<Object> parameters) {
		return pageQuery_(null, pageNum, pageSize, sql, parameters);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> PageResult<T> pageQuery(Class<T> clazz, int pageNum, int pageSize, String sql){
		return pageQuery_(clazz, pageNum, pageSize, sql, null);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> PageResult<T> pageQuery(Class<T> clazz, int pageNum, int pageSize, String sql, List<Object> parameters) {
		return pageQuery_(clazz, pageNum, pageSize, sql, parameters);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> recursiveQuery(RecursiveEntity entity, String sql, List<Object> parameters) {
		return new RecursiveQuerier(entity, sql, parameters).execute(this);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> recursiveQuery(Class<T> clazz, RecursiveEntity entity, String sql, List<Object> parameters) {
		return new RecursiveQuerier(clazz, entity, sql, parameters).execute(this);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public PageResult<List<Map<String, Object>>> pageRecursiveQuery(PageRecursiveEntity entity, String sql, List<Object> parameters) {
		return new PageRecursiveQuerier(entity, sql, parameters).execute(this);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> PageResult<T> pageRecursiveQuery(Class<T> clazz, PageRecursiveEntity entity, String sql, List<Object> parameters) {
		return new PageRecursiveQuerier(clazz, entity, sql, parameters).execute(this);
	}
	
	/**
	 * listMap转换为listClass
	 * @param clazz
	 * @param listMap
	 * @return
	 */
	protected <T> List<T> listMap2listClass(Class<T> clazz, List<Map<String, Object>> listMap) {
		List<T> targetList = new ArrayList<T>(listMap.size());
		NamePair namePair = new NamePair(listMap.get(0));
		listMap.forEach(map -> targetList.add(map2Class(namePair, clazz, map)));
		return targetList;
	}
	
	/**
	 * 将map转换为类
	 * @param clazz
	 * @param resultMap
	 * @return
	 */
	protected <T> T map2Class(Class<T> clazz, Map<String, Object> resultMap) {
		NamePair namePair = new NamePair(resultMap);
		return map2Class(namePair, clazz, resultMap);
	}
	
	/**
	 * 将resultMap转为指定的clazz实例
	 * @param columNames
	 * @param propertyNames
	 * @param clazz
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> T map2Class(NamePair namePair, Class<T> clazz, Map<String, Object> map) {
		while(namePair.next())
			map.put(namePair.getProperty(), map.remove(namePair.getColumn()));
		
		Object object = ClassUtil.newInstance(clazz);
		IntrospectorUtil.setValues(map, object);
		return (T) object;
	}
	
	@Override
	public InsertResult executeInsert(String sql, List<Object> parameters, AutoIncrementID autoIncrementID) {
		StatementHandler statementHandler = getStatementHandler(sql, parameters, autoIncrementID);
		try {
			return statementHandler.executeInsert(parameters);
		} catch (StatementExecutionException e) {
			logger.error("executeInsert时出现异常: {}", ExceptionUtil.getStackTrace(e));
			throw new SessionExecuteException("executeInsert时出现异常", e);
		}
	}
	
	@Override
	public int executeUpdate(String sql, List<Object> parameters) {
		StatementHandler statementHandler = getStatementHandler(sql, parameters, null);
		try {
			return statementHandler.executeUpdate(parameters);
		} catch (StatementExecutionException e) {
			logger.error("executeUpdate时出现异常: {}", ExceptionUtil.getStackTrace(e));
			throw new SessionExecuteException("executeUpdate时出现异常", e);
		}
	}
	
	@Override
	public Object executeProcedure(ProcedureExecutor procedureExecutor) {
		return procedureExecutor.execute(getConnection());
	}
	
	@Override
	public void close() {
		if(!isClosed) {
			if(logger.isDebugEnabled()) 
				logger.debug("close {}", getClass().getName());
			
			if(statementHandlerCache != null && statementHandlerCache.size() > 0) {
				statementHandlerCache.forEach((key, statementHandler) -> statementHandler.close());
				statementHandlerCache.clear();
				statementHandlerCache = null;
			}
			isClosed = true;
		}
	}
}

/**
 * 
 * @author DougLei
 */
class NamePair {
	private int index;
	private String[] columns;
	private String[] properties;
	
	public NamePair(Map<String, Object> resultMap) {
		this.columns = new String[resultMap.size()];
		this.properties = new String[resultMap.size()];
		
		for (String key : resultMap.keySet()) {
			columns[index] = key;
			properties[index] = NameConvertUtil.column2Property(key);
			index++;
		}
	}
	
	public boolean next() { 
		return --index >= 0;
	}
	public String getColumn() {
		return columns[index];
	}
	public String getProperty() {
		return properties[index];
	}
}