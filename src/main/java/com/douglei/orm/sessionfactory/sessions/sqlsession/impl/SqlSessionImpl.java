package com.douglei.orm.sessionfactory.sessions.sqlsession.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.mapping.MappingWrapper;
import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.context.EnvironmentContext;
import com.douglei.orm.core.dialect.db.object.DBObjectType;
import com.douglei.orm.core.sql.ConnectionWrapper;
import com.douglei.orm.core.sql.pagequery.PageResult;
import com.douglei.orm.core.sql.pagequery.PageSqlStatement;
import com.douglei.orm.core.sql.recursivequery.RecursiveSqlStatement;
import com.douglei.orm.core.sql.statement.StatementExecutionException;
import com.douglei.orm.core.sql.statement.StatementHandler;
import com.douglei.orm.core.utils.ResultSetMapConvertUtil;
import com.douglei.orm.sessionfactory.sessions.SessionExecutionException;
import com.douglei.orm.sessionfactory.sessions.SessionImpl;
import com.douglei.orm.sessionfactory.sessions.sqlsession.DBObjectIsExistsException;
import com.douglei.orm.sessionfactory.sessions.sqlsession.DBObjectNotExistsException;
import com.douglei.orm.sessionfactory.sessions.sqlsession.ProcedureExecutor;
import com.douglei.orm.sessionfactory.sessions.sqlsession.SqlSession;
import com.douglei.tools.utils.CollectionUtil;
import com.douglei.tools.utils.CryptographyUtil;
import com.douglei.tools.utils.ExceptionUtil;

/**
 * 执行sql语句的session实现类
 * @author DougLei
 */
public class SqlSessionImpl extends SessionImpl implements SqlSession{
	private static final Logger logger = LoggerFactory.getLogger(SqlSessionImpl.class);
	
	private boolean enableStatementCache;// 是否启用Statement缓存
	private boolean enableResultCache;// 是否开启Result缓存
	private Map<String, StatementHandler> statementHandlerCache;
	
	public SqlSessionImpl(ConnectionWrapper connection, EnvironmentProperty environmentProperty, MappingWrapper mappingWrapper) {
		super(connection, environmentProperty, mappingWrapper);
		this.enableStatementCache = environmentProperty.enableStatementCache();
		this.enableResultCache = environmentProperty.enableResultCache();
		logger.debug("是否开启Statement缓存: {}; 是否开启Result缓存: {}", enableStatementCache, enableResultCache);
	}
	
	/**
	 * 获取StatementHandler
	 * @param sql
	 * @param parameters
	 * @return
	 */
	private StatementHandler getStatementHandler(String sql, List<Object> parameters){
		StatementHandler statementHandler = null;
		if(enableStatementCache) {
			logger.debug("缓存开启, 从缓存中获取StatementHandler实例");
			String code = CryptographyUtil.encodeMD5(sql);
			
			if(statementHandlerCache == null) {
				statementHandlerCache = new HashMap<String, StatementHandler>(8);
			}else {
				statementHandler = statementHandlerCache.get(code);
			}
			
			if(statementHandler == null) {
				logger.debug("缓存中不存在相关的StatementHandler实例, 创建实例并放到缓存中");
				statementHandler = connection.createStatementHandler(enableResultCache, sql, parameters);
				statementHandlerCache.put(code, statementHandler);
			}else {
				if(logger.isDebugEnabled()) {
					logger.debug("缓存中存在{}实例", statementHandler.getClass());
					logger.debug("sql语句为: {}", sql);
					if(parameters==null || parameters.size()==0) {
						logger.debug("本次没有参数");
					}else {
						logger.debug("本次参数为: {}", parameters.toString());
					}
				}
			}
		}else {
			logger.debug("没有开启缓存, 创建StatementHandler实例");
			statementHandler = connection.createStatementHandler(enableResultCache, sql, parameters);
		}
		return statementHandler;
	}
	
	@Override
	public List<Map<String, Object>> query(String sql, List<Object> parameters) {
		StatementHandler statementHandler = getStatementHandler(sql, parameters);
		try {
			return statementHandler.getQueryResultList(parameters);
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
		StatementHandler statementHandler = getStatementHandler(sql, parameters);
		try {
			return statementHandler.getQueryUniqueResult(parameters);
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
	public List<Object[]> query_(String sql, List<Object> parameters) {
		StatementHandler statementHandler = getStatementHandler(sql, parameters);
		try {
			return statementHandler.getQueryResultList_(parameters);
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
	public Object[] uniqueQuery_(String sql, List<Object> parameters) {
		StatementHandler statementHandler = getStatementHandler(sql, parameters);
		try {
			return statementHandler.getQueryUniqueResult_(parameters);
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
	public int executeUpdate(String sql, List<Object> parameters) {
		StatementHandler statementHandler = getStatementHandler(sql, parameters);
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
	public void close() {
		if(!isClosed) {
			if(logger.isDebugEnabled()) {
				logger.debug("close {}", getClass().getName());
			}
			if(enableStatementCache && CollectionUtil.unEmpty(statementHandlerCache)) {
				statementHandlerCache.forEach((key, statementHandler) -> statementHandler.close());
				statementHandlerCache.clear();
				statementHandlerCache = null;
			}
			isClosed = true;
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
		logger.debug("开始执行分页查询, pageNum={}, pageSize={}", pageNum, pageSize);
		if(pageNum < 0) {
			logger.debug("pageNum实际值={}, 将值修正为1", pageNum);
			pageNum = 1;
		}
		if(pageSize < 0) {
			logger.debug("pageSize实际值={}, 将值修正为10", pageSize);
			pageSize = 10;
		}
		PageSqlStatement pageSqlStatement = new PageSqlStatement(sql);
		long count = Integer.parseInt(uniqueQuery_(pageSqlStatement.getWithClause() + " select count(1) from ("+pageSqlStatement.getSql()+") jdb_orm_qt_", parameters)[0].toString()); // 查询总数量
		logger.debug("查询到的数据总量为:{}条", count);
		PageResult pageResult = new PageResult(pageNum, pageSize, count);
		if(count > 0) {
			sql = EnvironmentContext.getEnvironmentProperty().getDialect().getSqlHandler().installPageQuerySql(pageResult.getPageNum(), pageResult.getPageSize(), pageSqlStatement);
			List list = query(sql, parameters);
			if(!list.isEmpty() && targetClass != null) 
				list = listMap2listClass(targetClass, list);
			pageResult.setResultDatas(list);
		}
		if(logger.isDebugEnabled()) {
			logger.debug("分页查询的结果: {}", pageResult.toString());
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
	private List recursiveQuery_(Class targetClass, int deep, String parentColumnName, Object parentValue, String sql, List<Object> parameters) {
		logger.debug("开始执行递归查询, deep={}, parentColumnName={}, parentValue={}", deep, parentColumnName, parentValue);
		RecursiveSqlStatement recursiveSqlStatement = new RecursiveSqlStatement(sql);
		
		List list = query(sql, parameters);
		
//		long count = Integer.parseInt(uniqueQuery_(pageSqlStatement.getWithClause() + " select count(1) from ("+pageSqlStatement.getSql()+") jdb_orm_qt_", parameters)[0].toString()); // 查询总数量
//		logger.debug("查询到的数据总量为:{}条", count);
//		PageResult pageResult = new PageResult(pageNum, pageSize, count);
//		if(count > 0) {
//			sql = EnvironmentContext.getEnvironmentProperty().getDialect().getSqlHandler().installPageQuerySql(pageResult.getPageNum(), pageResult.getPageSize(), pageSqlStatement);
//			List list = query(sql, parameters);
//			if(!list.isEmpty() && targetClass != null) 
//				list = listMap2listClass(targetClass, list);
//			pageResult.setResultDatas(list);
//		}
//		if(logger.isDebugEnabled()) {
//			logger.debug("分页查询的结果: {}", pageResult.toString());
//		}
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> recursiveQuery(int deep, String parentColumnName, Object parentValue, String sql, List<Object> parameters) {
		return recursiveQuery_(null, deep, parentColumnName, parentValue, sql, parameters);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> recursiveQuery(Class<T> targetClass, int deep, String parentColumnName, Object parentValue, String sql) {
		return recursiveQuery_(targetClass, deep, parentColumnName, parentValue, sql, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> recursiveQuery(Class<T> targetClass, int deep, String parentColumnName, Object parentValue, String sql, List<Object> parameters) {
		return recursiveQuery_(targetClass, deep, parentColumnName, parentValue, sql, parameters);
	}

	/**
	 * listMap转换为listClass
	 * @param targetClass
	 * @param listMap
	 * @return
	 */
	protected <T> List<T> listMap2listClass(Class<T> targetClass, List<Map<String, Object>> listMap) {
		List<T> listT = new ArrayList<T>(listMap.size());
		for (Map<String, Object> map : listMap) {
			listT.add(map2Class(targetClass, map));
		}
		return listT;
	}
	
	/**
	 * 将map转换为类
	 * @param targetClass
	 * @param map
	 * @return
	 */
	protected <T> T map2Class(Class<T> targetClass, Map<String, Object> map) {
		return ResultSetMapConvertUtil.toClass(map, targetClass);
	}

	
	@Override
	public Object executeProcedure(ProcedureExecutor procedureExecutor) {
		return procedureExecutor.execute(getConnection());
	}
	
	@Override
	public boolean dbObjectExists(DBObjectType dbObjectType, String dbObjectName) {
		List<Object> parameters = new ArrayList<Object>(2);
		return Byte.parseByte(uniqueQuery_(environmentProperty.getDialect().getDBObjectHandler().getQueryDBObjectIsExistsSqlStatement(dbObjectType, dbObjectName, parameters), parameters)[0].toString()) == 1;
	}

	@Override
	public void dbObjectCreate(DBObjectType dbObjectType, String dbObjectName, String createSqlStatement) {
		dbObjectCreate(dbObjectType, dbObjectName, createSqlStatement, false);
	}
	
	@Override
	public void dbObjectCreate(DBObjectType dbObjectType, String dbObjectName, String createSqlStatement, boolean isOverride) {
		environmentProperty.getDialect().getDBObjectHandler().validateDBObjectName(dbObjectName);
		if(dbObjectExists(dbObjectType, dbObjectName)) {
			if(isOverride) {
				executeUpdate(environmentProperty.getDialect().getDBObjectHandler().getDropDBObjectSqlStatement(dbObjectType, dbObjectName));
			}else {
				throw new DBObjectIsExistsException(dbObjectType, dbObjectName);
			}
		}
		executeUpdate(createSqlStatement);
	}

	@Override
	public void dbObjectDrop(DBObjectType dbObjectType, String dbObjectName) {
		if(dbObjectExists(dbObjectType, dbObjectName)) {
			executeUpdate(environmentProperty.getDialect().getDBObjectHandler().getDropDBObjectSqlStatement(dbObjectType, dbObjectName));
			return;
		}
		throw new DBObjectNotExistsException(dbObjectType, dbObjectName);
	}
}
