package com.douglei.orm.sessionfactory.sessions.sqlsession.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.environment.datasource.ConnectionWrapper;
import com.douglei.orm.sessionfactory.sessions.SessionExecutionException;
import com.douglei.orm.sessionfactory.sessions.SessionImpl;
import com.douglei.orm.sessionfactory.sessions.sqlsession.ProcedureExecutor;
import com.douglei.orm.sessionfactory.sessions.sqlsession.SqlSession;
import com.douglei.orm.sql.ReturnID;
import com.douglei.orm.sql.pagequery.PageResult;
import com.douglei.orm.sql.pagequery.PageSqlStatement;
import com.douglei.orm.sql.pagerecursivequery.PageRecursiveSqlStatement;
import com.douglei.orm.sql.recursivequery.RecursiveSqlStatement;
import com.douglei.orm.sql.statement.InsertResult;
import com.douglei.orm.sql.statement.StatementExecutionException;
import com.douglei.orm.sql.statement.StatementHandler;
import com.douglei.tools.utils.ExceptionUtil;
import com.douglei.tools.utils.naming.converter.ConverterUtil;
import com.douglei.tools.utils.naming.converter.impl.ColumnName2PropertyNameConverter;
import com.douglei.tools.utils.naming.converter.impl.PropertyName2ColumnNameConverter;
import com.douglei.tools.utils.reflect.ConstructorUtil;
import com.douglei.tools.utils.reflect.IntrospectorUtil;

/**
 * 执行sql语句的session实现类
 * @author DougLei
 */
public class SqlSessionImpl extends SessionImpl implements SqlSession{
	private static final Logger logger = LoggerFactory.getLogger(SqlSessionImpl.class);
	
	private boolean enableStatementCache;// 是否启用Statement缓存
	private Map<String, StatementHandler> statementHandlerCache;
	
	public SqlSessionImpl(ConnectionWrapper connection, Environment environment) {
		super(connection, environment);
		this.enableStatementCache = environment.getEnvironmentProperty().enableStatementCache();
		logger.debug("是否开启Statement缓存: {}", enableStatementCache);
	}
	
	/**
	 * 获取StatementHandler
	 * @param sql
	 * @param parameters
	 * @param returnID
	 * @return
	 */
	private StatementHandler getStatementHandler(String sql, List<Object> parameters, ReturnID returnID){
		logger.debug("执行的sql语句为: {}", sql);
		logger.debug("传入的sql参数为: {}", parameters);
		
		StatementHandler statementHandler = null;
		if(enableStatementCache) {
			String signature = DigestUtils.md5Hex(sql);
			
			if(statementHandlerCache == null) {
				statementHandlerCache = new HashMap<String, StatementHandler>(8);
			}else {
				statementHandler = statementHandlerCache.get(signature);
			}
			
			if(statementHandler == null) {
				logger.debug("缓存中不存在相关的StatementHandler实例, 创建实例并尝试放到缓存中");
				statementHandler = connection.createStatementHandler(sql, parameters, returnID);
				if(statementHandler.supportCache())
					statementHandlerCache.put(signature, statementHandler);
			}
		}else {
			logger.debug("没有开启缓存, 只创建StatementHandler实例");
			statementHandler = connection.createStatementHandler(sql, parameters, returnID);
		}
		return statementHandler;
	}
	
	@Override
	public List<Map<String, Object>> query(String sql, List<Object> parameters) {
		StatementHandler statementHandler = getStatementHandler(sql, parameters, null);
		try {
			return statementHandler.executeQueryResultList(parameters);
		} catch (StatementExecutionException e) {
			logger.error("在查询数据时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw new SessionExecutionException("在查询数据时出现异常", e);
		} finally {
			if(!enableStatementCache) {
				statementHandler.close();
			}
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
			logger.error("在查询数据时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw new SessionExecutionException("在查询数据时出现异常", e);
		} finally {
			if(!enableStatementCache) {
				statementHandler.close();
			}
		}
	}
	
	@Override
	public Map<String, Object> uniqueQuery(String sql, List<Object> parameters) {
		StatementHandler statementHandler = getStatementHandler(sql, parameters, null);
		try {
			return statementHandler.executeQueryUniqueResult(parameters);
		} catch (StatementExecutionException e) {
			logger.error("在查询数据时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw new SessionExecutionException("在查询数据时出现异常", e);
		} finally {
			if(!enableStatementCache) {
				statementHandler.close();
			}
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
			logger.error("在查询数据时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw new SessionExecutionException("在查询数据时出现异常", e);
		} finally {
			if(!enableStatementCache) {
				statementHandler.close();
			}
		}
	}
	
	@Override
	public List<Map<String, Object>> limitQuery(String sql, int startRow, int length, List<Object> parameters){
		StatementHandler statementHandler = getStatementHandler(sql, parameters, null);
		try {
			return statementHandler.executeLimitQueryResultList(startRow, length, parameters);
		} catch (StatementExecutionException e) {
			logger.error("在查询数据时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw new SessionExecutionException("在查询数据时出现异常", e);
		} finally {
			if(!enableStatementCache) 
				statementHandler.close();
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
			logger.error("在查询数据时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw new SessionExecutionException("在查询数据时出现异常", e);
		} finally {
			if(!enableStatementCache) 
				statementHandler.close();
		}
	}
	
	@Override
	public long countQuery(String sql, List<Object> parameters) {
		PageSqlStatement statement = new PageSqlStatement(EnvironmentContext.getDialect().getSqlStatementHandler(), sql);
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
		
		PageSqlStatement statement = new PageSqlStatement(EnvironmentContext.getDialect().getSqlStatementHandler(), sql);
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
	 * 递归查询 <内部方法, 不考虑泛型>
	 * @param targetClass
	 * @param deep
	 * @param parentColumnName
	 * @param parentValue
	 * @param sql
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List recursiveQuery_(Class targetClass, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String sql, List<Object> parameters) {
		pkColumnName = pkColumnName.toUpperCase();
		parentPkColumnName = parentPkColumnName.toUpperCase();
		if(deep <= 0)
			deep = -1;
		if(targetClass != null)
			childNodeName = ConverterUtil.convert(childNodeName, PropertyName2ColumnNameConverter.class);
		if(parameters == null)
			parameters = new ArrayList<Object>();
		logger.debug("开始执行递归查询, deep={}, pkColumnName={}, parentPkColumnName={}, parentValue={}, childNodeName={}", deep, pkColumnName, parentPkColumnName, parentValue, childNodeName);
		
		RecursiveSqlStatement statement = new RecursiveSqlStatement(EnvironmentContext.getDialect().getSqlStatementHandler(), sql, pkColumnName, parentPkColumnName, childNodeName, parentValue);
		List<Map<String, Object>> rootList = query(statement.getRecursiveSql(), statement.appendParameterValues(parameters));
		recursiveQuery_(targetClass, statement, rootList, deep-1, parameters);
		statement.removeParentValueList(parameters);
		
		if(targetClass != null && !rootList.isEmpty()) 
			rootList = listMap2listClass(targetClass, rootList);
		return rootList;
	}
	
	@SuppressWarnings("rawtypes")
	private void recursiveQuery_(Class targetClass, RecursiveSqlStatement statement, List<Map<String, Object>> parentList, int deep, List<Object> parameters) {
		if((deep < -1 && parentList.isEmpty()) || deep == 0)
			return;
		
		// 更新父级主键值, 并进行下一层的数据查询
		statement.updateParentValueList(parentList);
		List<Map<String, Object>> childrenList = query(statement.getRecursiveSql(), statement.appendParameterValues(parameters));
		recursiveQuery_(targetClass, statement, childrenList, deep-1, parameters);
		
		// 将subList与对应的parent建立父子层级结构
		for (Map<String, Object> parent : parentList) 
			buildingPCStruct(targetClass, parent, childrenList, statement);
	}
	// 构建父子结构
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void buildingPCStruct(Class targetClass, Map<String, Object> parent, List<Map<String, Object>> childrenList, RecursiveSqlStatement statement) {
		List sl = null;
		if(!childrenList.isEmpty()) {
			Object pid = parent.get(statement.getPkColumnName());
			for(int i=0;i<childrenList.size();i++) {
				if(childrenList.get(i).get(statement.getParentPkColumnName()).equals(pid)) { 
					if(sl == null)
						sl = new ArrayList();
					sl.add(targetClass==null?childrenList.remove(i--):map2Class(targetClass, childrenList.remove(i--)));
				}
			}
		}
		if(sl == null)
			sl = Collections.emptyList();
		parent.put(statement.getChildNodeName(), sl);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> recursiveQuery(int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String sql, List<Object> parameters) {
		return recursiveQuery_(null, deep, pkColumnName, parentPkColumnName, parentValue, childNodeName, sql, parameters);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> recursiveQuery(Class<T> targetClass, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String sql) {
		return recursiveQuery_(targetClass, deep, pkColumnName, parentPkColumnName, parentValue, childNodeName, sql, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> recursiveQuery(Class<T> targetClass, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String sql, List<Object> parameters) {
		return recursiveQuery_(targetClass, deep, pkColumnName, parentPkColumnName, parentValue, childNodeName, sql, parameters);
	}
	
	/**
	 * 分页递归查询 <内部方法, 不考虑泛型>
	 * @param targetClass
	 * @param pageNum
	 * @param pageSize
	 * @param deep
	 * @param pkColumnName
	 * @param parentPkColumnName
	 * @param parentValue
	 * @param childNodeName
	 * @param sql
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private PageResult pageRecursiveQuery_(Class targetClass, int pageNum, int pageSize, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String sql, List<Object> parameters) {
		if(pageNum < 0) pageNum = 1;
		if(pageSize < 0) pageSize = 10;
		pkColumnName = pkColumnName.toUpperCase();
		parentPkColumnName = parentPkColumnName.toUpperCase();
		if(deep <= 0)
			deep = -1;
		if(targetClass != null)
			childNodeName = ConverterUtil.convert(childNodeName, PropertyName2ColumnNameConverter.class);
		if(parameters == null)
			parameters = new ArrayList<Object>();
		logger.debug("开始执行分页递归查询, pageNum={}, pageSize={}, deep={}, pkColumnName={}, parentPkColumnName={}, parentValue={}, childNodeName={}", pageNum, pageSize, deep, pkColumnName, parentPkColumnName, parentValue, childNodeName);
		
		PageRecursiveSqlStatement statement = new PageRecursiveSqlStatement(EnvironmentContext.getDialect().getSqlStatementHandler(), sql, pkColumnName, parentPkColumnName, childNodeName, parentValue);
		long count = Integer.parseInt(uniqueQuery_(statement.getCountSql(), statement.appendParameterValues(parameters))[0].toString()); // 查询总数量
		logger.debug("查询到的数据总量为:{}条", count);
		PageResult pageResult = new PageResult(pageNum, pageSize, count);
		if(count > 0) {
			List<Map<String, Object>> rootList = query(statement.getPageRecursiveQuerySql(pageResult.getPageNum(), pageResult.getPageSize()), parameters);
			recursiveQuery_(targetClass, statement, rootList, deep-1, parameters);
			statement.removeParentValueList(parameters);
			
			if(targetClass != null && !rootList.isEmpty()) 
				rootList = listMap2listClass(targetClass, rootList);
			pageResult.setResultDatas(rootList);
		}
		return pageResult;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public PageResult<Map<String, Object>> pageRecursiveQuery(int pageNum, int pageSize, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String sql, List<Object> parameters) {
		return pageRecursiveQuery_(null, pageNum, pageSize, deep, pkColumnName, parentPkColumnName, parentValue, childNodeName, sql, parameters);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> PageResult<T> pageRecursiveQuery(Class<T> targetClass, int pageNum, int pageSize, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String sql) {
		return pageRecursiveQuery_(targetClass, pageNum, pageSize, deep, pkColumnName, parentPkColumnName, parentValue, childNodeName, sql, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> PageResult<T> pageRecursiveQuery(Class<T> targetClass, int pageNum, int pageSize, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String sql, List<Object> parameters) {
		return pageRecursiveQuery_(targetClass, pageNum, pageSize, deep, pkColumnName, parentPkColumnName, parentValue, childNodeName, sql, parameters);
	}

	/**
	 * listMap转换为listClass
	 * @param targetClass
	 * @param listMap
	 * @return
	 */
	protected <T> List<T> listMap2listClass(Class<T> targetClass, List<Map<String, Object>> listMap) {
		List<T> listT = new ArrayList<T>(listMap.size());
		String[] resultMapColumnKeys = getResultMapColumnKeys(listMap.get(0));
		for (Map<String, Object> map : listMap) {
			listT.add(map2Class(resultMapColumnKeys, targetClass, map));
		}
		return listT;
	}
	
	/**
	 * 将map转换为类
	 * @param targetClass
	 * @param resultMap
	 * @return
	 */
	protected <T> T map2Class(Class<T> targetClass, Map<String, Object> resultMap) {
		String[] resultMapColumnKeys = getResultMapColumnKeys(resultMap);
		return map2Class(resultMapColumnKeys, targetClass, resultMap);
	}
	
	/**
	 * 获取结果集map的 Column Key值数组
	 * @param resultMap
	 * @return
	 */
	private String[] getResultMapColumnKeys(Map<String, Object> resultMap) {
		String[] resultMapColumnKeys = new String[resultMap.size()];
		short index = 0;
		
		Set<String> keys = resultMap.keySet();
		for (String key : keys) {
			resultMapColumnKeys[index++] = key;
		}
		return resultMapColumnKeys;
	}
	
	/**
	 * 将resultMap转为指定的targetClass实例
	 * @param resultMapColumnKeys
	 * @param targetClass
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> T map2Class(String[] resultMapColumnKeys, Class<T> targetClass, Map<String, Object> map) {
		for (String columnKey : resultMapColumnKeys) {
			map.put(ConverterUtil.convert(columnKey, ColumnName2PropertyNameConverter.class), map.remove(columnKey));
		}
		return (T) IntrospectorUtil.setProperyValues(ConstructorUtil.newInstance(targetClass), map);
	}
	
	@Override
	public InsertResult executeInsert(String sql, List<Object> parameters, ReturnID returnID) {
		StatementHandler statementHandler = getStatementHandler(sql, parameters, returnID);
		try {
			return statementHandler.executeInsert(parameters);
		} catch (StatementExecutionException e) {
			logger.error("execute insert sql时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw new SessionExecutionException("execute insert sql时出现异常", e);
		} finally {
			if(!enableStatementCache) {
				statementHandler.close();
			}
		}
	}
	
	@Override
	public int executeUpdate(String sql, List<Object> parameters) {
		StatementHandler statementHandler = getStatementHandler(sql, parameters, null);
		try {
			return statementHandler.executeUpdate(parameters);
		} catch (StatementExecutionException e) {
			logger.error("execute update sql时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw new SessionExecutionException("execute update sql时出现异常", e);
		} finally {
			if(!enableStatementCache) {
				statementHandler.close();
			}
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
			
			if(enableStatementCache && statementHandlerCache != null) {
				if(!statementHandlerCache.isEmpty()) {
					statementHandlerCache.forEach((key, statementHandler) -> statementHandler.close());
					statementHandlerCache.clear();
				}
				statementHandlerCache = null;
			}
			isClosed = true;
		}
	}
}
