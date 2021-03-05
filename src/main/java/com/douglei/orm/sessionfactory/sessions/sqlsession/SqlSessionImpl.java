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
import com.douglei.orm.configuration.environment.datasource.ConnectionWrapper;
import com.douglei.orm.sessionfactory.sessions.SessionExecutionException;
import com.douglei.orm.sessionfactory.sessions.SessionImpl;
import com.douglei.orm.sql.AutoIncrementID;
import com.douglei.orm.sql.pagequery.PageResult;
import com.douglei.orm.sql.pagequery.PageSqlStatement;
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
public class SqlSessionImpl extends SessionImpl implements SqlSession{
	private static final Logger logger = LoggerFactory.getLogger(SqlSessionImpl.class);
	private Map<String, StatementHandler> statementHandlerCache;
	
	public SqlSessionImpl(ConnectionWrapper connection, Environment environment) {
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
			throw new SessionExecutionException("在查询数据时出现异常", e);
		} 
	}
	
	@Override
	public <T> List<T> query(Class<T> targetClass, String sql) {
		return query(targetClass, sql, null);
	}

	@Override
	public <T> List<T> query(Class<T> targetClass, String sql, List<Object> parameters) {
		List<Map<String, Object>> listMap = query(sql, parameters);
		if(listMap.isEmpty())
			return Collections.emptyList();
		return listMap2listClass(targetClass, listMap);
	}
	
	@Override
	public List<Object[]> query_(String sql, List<Object> parameters) {
		StatementHandler statementHandler = getStatementHandler(sql, parameters, null);
		try {
			return statementHandler.executeQueryResultList_(parameters);
		} catch (StatementExecutionException e) {
			logger.error("在查询数据时出现异常: {}", ExceptionUtil.getStackTrace(e));
			throw new SessionExecutionException("在查询数据时出现异常", e);
		} 
	}
	
	@Override
	public Map<String, Object> uniqueQuery(String sql, List<Object> parameters) {
		StatementHandler statementHandler = getStatementHandler(sql, parameters, null);
		try {
			return statementHandler.executeQueryUniqueResult(parameters);
		} catch (StatementExecutionException e) {
			logger.error("在查询数据时出现异常: {}", ExceptionUtil.getStackTrace(e));
			throw new SessionExecutionException("在查询数据时出现异常", e);
		}
	}
	
	@Override
	public <T> T uniqueQuery(Class<T> targetClass, String sql) {
		return uniqueQuery(targetClass, sql, null);
	}

	@Override
	public <T> T uniqueQuery(Class<T> targetClass, String sql, List<Object> parameters) {
		Map<String, Object> map = uniqueQuery(sql, parameters);
		if(map == null)
			return null;
		return map2Class(targetClass, map);
	}
	
	@Override
	public Object[] uniqueQuery_(String sql, List<Object> parameters) {
		StatementHandler statementHandler = getStatementHandler(sql, parameters, null);
		try {
			return statementHandler.executeQueryUniqueResult_(parameters);
		} catch (StatementExecutionException e) {
			logger.error("在查询数据时出现异常: {}", ExceptionUtil.getStackTrace(e));
			throw new SessionExecutionException("在查询数据时出现异常", e);
		}
	}
	
	@Override
	public List<Map<String, Object>> limitQuery(String sql, int startRow, int length, List<Object> parameters){
		StatementHandler statementHandler = getStatementHandler(sql, parameters, null);
		try {
			return statementHandler.executeLimitQueryResultList(startRow, length, parameters);
		} catch (StatementExecutionException e) {
			logger.error("在查询数据时出现异常: {}", ExceptionUtil.getStackTrace(e));
			throw new SessionExecutionException("在查询数据时出现异常", e);
		}
	}
	
	@Override
	public <T> List<T> limitQuery(Class<T> targetClass, String sql, int startRow, int length){
		return limitQuery(targetClass, sql, startRow, length, null);
	}
	
	@Override
	public <T> List<T> limitQuery(Class<T> targetClass, String sql, int startRow, int length, List<Object> parameters){
		List<Map<String, Object>> listMap = limitQuery(sql, startRow, length, parameters);
		if(listMap.isEmpty())
			return Collections.emptyList();
		return listMap2listClass(targetClass, listMap);
	}
	
	@Override
	public List<Object[]> limitQuery_(String sql, int startRow, int length, List<Object> parameters){
		StatementHandler statementHandler = getStatementHandler(sql, parameters, null);
		try {
			return statementHandler.executeLimitQueryResultList_(startRow, length, parameters);
		} catch (StatementExecutionException e) {
			logger.error("在查询数据时出现异常: {}", ExceptionUtil.getStackTrace(e));
			throw new SessionExecutionException("在查询数据时出现异常", e);
		}
	}
	
	@Override
	public long countQuery(String sql, List<Object> parameters) {
		PageSqlStatement statement = new PageSqlStatement(environment.getDialect().getSqlStatementHandler(), sql);
		return Long.parseLong(uniqueQuery_(statement.getCountSql(), parameters)[0].toString());
	}

	/**
	 * 分页查询 <内部方法, 不考虑泛型>
	 * @param targetClass
	 * @param pageNum
	 * @param pageSize
	 * @param sql
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private PageResult pageQuery_(Class targetClass, int pageNum, int pageSize, String sql, List<Object> parameters) {
		if(pageNum < 0) pageNum = 1;
		if(pageSize < 0) pageSize = 10;
		logger.debug("开始执行分页查询, pageNum={}, pageSize={}", pageNum, pageSize);
		
		PageSqlStatement statement = new PageSqlStatement(environment.getDialect().getSqlStatementHandler(), sql);
		long count = Long.parseLong(uniqueQuery_(statement.getCountSql(), parameters)[0].toString()); // 查询总数量
		logger.debug("查询到的数据总量为:{}条", count);
		PageResult pageResult = new PageResult(pageNum, pageSize, count);
		if(count > 0) {
			List list = query(statement.getPageQuerySql(pageResult.getPageNum(), pageResult.getPageSize()), parameters);
			if(targetClass != null && !list.isEmpty()) 
				list = listMap2listClass(targetClass, list);
			pageResult.setResultDatas(list);
		}
		logger.debug("分页查询的结果: {}", pageResult);
		return pageResult;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String sql, List<Object> parameters) {
		return pageQuery_(null, pageNum, pageSize, sql, parameters);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> PageResult<T> pageQuery(Class<T> targetClass, int pageNum, int pageSize, String sql){
		return pageQuery_(targetClass, pageNum, pageSize, sql, null);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> PageResult<T> pageQuery(Class<T> targetClass, int pageNum, int pageSize, String sql, List<Object> parameters) {
		return pageQuery_(targetClass, pageNum, pageSize, sql, parameters);
	}
	
	/**
	 * listMap转换为listClass
	 * @param targetClass
	 * @param listMap
	 * @return
	 */
	protected <T> List<T> listMap2listClass(Class<T> targetClass, List<Map<String, Object>> listMap) {
		List<T> targetList = new ArrayList<T>(listMap.size());
		String[] columnNames = getColumnNames(listMap.get(0));
		listMap.forEach(map -> targetList.add(map2Class(columnNames, targetClass, map)));
		return targetList;
	}
	
	/**
	 * 将map转换为类
	 * @param targetClass
	 * @param resultMap
	 * @return
	 */
	protected <T> T map2Class(Class<T> targetClass, Map<String, Object> resultMap) {
		String[] columnNames = getColumnNames(resultMap);
		return map2Class(columnNames, targetClass, resultMap);
	}
	
	/**
	 * 获取结果集map的 Column名数组
	 * @param resultMap
	 * @return
	 */
	private String[] getColumnNames(Map<String, Object> resultMap) {
		String[] columnNames = new String[resultMap.size()];
		int index = 0;
		for (String key : resultMap.keySet()) 
			columnNames[index++] = key;
		return columnNames;
	}
	
	/**
	 * 将resultMap转为指定的targetClass实例
	 * @param columNames
	 * @param targetClass
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> T map2Class(String[] columNames, Class<T> targetClass, Map<String, Object> map) {
		for (String columName : columNames) 
			map.put(NameConvertUtil.column2Property(columName), map.remove(columName));
		
		Object object = ClassUtil.newInstance(targetClass);
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
			throw new SessionExecutionException("executeInsert时出现异常", e);
		}
	}
	
	@Override
	public int executeUpdate(String sql, List<Object> parameters) {
		StatementHandler statementHandler = getStatementHandler(sql, parameters, null);
		try {
			return statementHandler.executeUpdate(parameters);
		} catch (StatementExecutionException e) {
			logger.error("executeUpdate时出现异常: {}", ExceptionUtil.getStackTrace(e));
			throw new SessionExecutionException("executeUpdate时出现异常", e);
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
