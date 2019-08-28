package com.douglei.orm.sessionfactory.sessions.sqlsession.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.mapping.MappingWrapper;
import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.context.EnvironmentContext;
import com.douglei.orm.core.dialect.db.object.DBObjectType;
import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.sql.ConnectionWrapper;
import com.douglei.orm.core.sql.pagequery.PageResult;
import com.douglei.orm.core.sql.pagequery.PageSqlStatement;
import com.douglei.orm.core.sql.statement.StatementExecutionException;
import com.douglei.orm.core.sql.statement.StatementHandler;
import com.douglei.orm.core.utils.ResultSetMapConvertUtil;
import com.douglei.orm.sessionfactory.sessions.SessionExecutionException;
import com.douglei.orm.sessionfactory.sessions.SessionImpl;
import com.douglei.orm.sessionfactory.sessions.sqlsession.DBObjectIsExistsException;
import com.douglei.orm.sessionfactory.sessions.sqlsession.DBObjectNotExistsException;
import com.douglei.orm.sessionfactory.sessions.sqlsession.ProcedureExecutor;
import com.douglei.orm.sessionfactory.sessions.sqlsession.SqlSession;
import com.douglei.tools.utils.Collections;
import com.douglei.tools.utils.CryptographyUtil;
import com.douglei.tools.utils.ExceptionUtil;
import com.douglei.tools.utils.datatype.converter.ConverterUtil;

/**
 * 执行sql语句的session实现类
 * @author DougLei
 */
public class SqlSessionImpl extends SessionImpl implements SqlSession{
	private static final Logger logger = LoggerFactory.getLogger(SqlSessionImpl.class);
	
	private boolean enableSessionCache;// 是否启用session缓存
	private Map<String, StatementHandler> statementHandlerCache;
	
	public SqlSessionImpl(ConnectionWrapper connection, EnvironmentProperty environmentProperty, MappingWrapper mappingWrapper) {
		super(connection, environmentProperty, mappingWrapper);
		this.enableSessionCache = environmentProperty.enableSessionCache();
		logger.debug("是否开启Session缓存: {}", enableSessionCache);
	}
	
	/**
	 * 获取StatementHandler
	 * @param sql
	 * @param parameters
	 * @return
	 */
	private StatementHandler getStatementHandler(String sql, List<Object> parameters){
		StatementHandler statementHandler = null;
		if(enableSessionCache) {
			logger.debug("缓存开启, 从缓存中获取StatementHandler实例");
			String code = CryptographyUtil.encodeMD5(sql);
			
			if(statementHandlerCache == null) {
				statementHandlerCache = new HashMap<String, StatementHandler>(8);
			}else {
				statementHandler = statementHandlerCache.get(code);
			}
			
			if(statementHandler == null) {
				logger.debug("缓存中不存在相关的StatementHandler实例, 创建实例并放到缓存中");
				statementHandler = connection.createStatementHandler(sql, parameters);
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
			statementHandler = connection.createStatementHandler(sql, parameters);
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
			if(!enableSessionCache) {
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
			if(!enableSessionCache) {
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
			if(!enableSessionCache) {
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
			if(!enableSessionCache) {
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
			if(!enableSessionCache) {
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
			if(enableSessionCache && Collections.unEmpty(statementHandlerCache)) {
				statementHandlerCache.forEach((key, statementHandler) -> statementHandler.close());
				statementHandlerCache.clear();
				statementHandlerCache = null;
			}
			isClosed = true;
		}
	}

	@Override
	public PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String sql, List<Object> parameters) {
		logger.debug("开始执行分页查询, pageNum={}, pageSize={}", pageNum, pageSize);
		if(pageNum < 0) {
			logger.debug("pageNum实际值={}, pageNum<0, 修正pageNum=1", pageNum);
			pageNum = 1;
		}
		if(pageSize < 0) {
			logger.debug("pageSize实际值={}, pageSize<0, 修正pageSize=10", pageNum);
			pageSize = 10;
		}
		PageSqlStatement pageSqlStatement = new PageSqlStatement(sql);
		long count = queryCount(pageSqlStatement, parameters);
		logger.debug("查询到的数据总量为:{}条", count);
		PageResult<Map<String, Object>> pageResult = new PageResult<Map<String,Object>>(pageNum, pageSize, count);
		if(count > 0) {
			sql = EnvironmentContext.getEnvironmentProperty().getDialect().getSqlHandler().installPageQuerySql(pageResult.getPageNum(), pageResult.getPageSize(), pageSqlStatement.getWithClause(), pageSqlStatement.getSql());
			List<Map<String, Object>> listMap = query(sql, parameters);
			pageResult.setResultDatas(listMap);
		}
		if(logger.isDebugEnabled()) {
			logger.debug("分页查询的结果: {}", pageResult.toString());
		}
		return pageResult;
	}
	
	/**
	 * 查询总数量
	 * @param pageSqlStatement
	 * @param parameters
	 * @return
	 */
	private long queryCount(PageSqlStatement pageSqlStatement, List<Object> parameters) {
		Object count =  uniqueQuery_(pageSqlStatement.getWithClause() + " select count(1) from ("+pageSqlStatement.getSql()+") jdb_orm_qt_", parameters)[0];
		return Integer.parseInt(count.toString());
	}

	@Override
	public Object executeProcedure(ProcedureExecutor procedureExecutor) {
		return procedureExecutor.execute(getConnection());
	}

	@Override
	public <T> List<T> query(Class<T> targetClass, String sql) {
		return query(targetClass, sql, null);
	}

	@Override
	public <T> List<T> query(Class<T> targetClass, String sql, List<Object> parameters) {
		List<Map<String, Object>> listMap = query(sql, parameters);
		return listMap2listClass(targetClass, listMap, null);
	}
	
	@Override
	public <T> T uniqueQuery(Class<T> targetClass, String sql) {
		return uniqueQuery(targetClass, sql, null);
	}

	@Override
	public <T> T uniqueQuery(Class<T> targetClass, String sql, List<Object> parameters) {
		Map<String, Object> map = uniqueQuery(sql, parameters);
		return map2Class(targetClass, map, null);
	}
	
	// listMap转换为listClass
	protected <T> List<T> listMap2listClass(Class<T> targetClass, List<Map<String, Object>> listMap, TableMetadata tableMetadata) {
		if(listMap.size() > 0) {
			List<T> listT = new ArrayList<T>(listMap.size());
			for (Map<String, Object> map : listMap) {
				listT.add(map2Class(targetClass, map, tableMetadata));
			}
			return listT;
		}
		return null;
	}
	
	// 将map转换为类
	protected <T> T map2Class(Class<T> targetClass, Map<String, Object> map, TableMetadata tableMetadata) {
		if(map.size() == 0) {
			return null;
		}
		if(tableMetadata == null || tableMetadata.classNameEmpty()) { // 没有配置映射, 或没有配置映射的类, 则将列名转换为属性名
			return ResultSetMapConvertUtil.toClass(map, targetClass);
		}else {
			map = mapKey2MappingPropertyName(map, tableMetadata); // 配置了类映射, 要从映射中获取映射的属性
			return ConverterUtil.mapToClass(map, targetClass);
		}
	}

	// 将map的key, 由列名转换成映射的属性名
	protected Map<String, Object> mapKey2MappingPropertyName(Map<String, Object> map, TableMetadata tableMetadata) {
		Map<String, Object> targetMap = new HashMap<String, Object>(map.size());
		
		ColumnMetadata column = null;
		Set<String> codes = tableMetadata.getColumnCodes();
		for (String code : codes) {
			column = tableMetadata.getColumnByCode(code);
			targetMap.put(column.getProperty(), map.get(column.getName()));
		}
		return targetMap;
	}

	@Override
	public <T> PageResult<T> pageQuery(Class<T> targetClass, int pageNum, int pageSize, String sql) {
		return pageQuery(targetClass, pageNum, pageSize, sql, null);
	}

	@Override
	public <T> PageResult<T> pageQuery(Class<T> targetClass, int pageNum, int pageSize, String sql, List<Object> parameters) {
		if(logger.isDebugEnabled()) {
			logger.debug("开始执行分页查询, targetClass={}, pageNum={}, pageSize={}", targetClass.getName(), pageNum, pageSize);
		}
		PageResult<Map<String, Object>> pageResult = pageQuery(pageNum, pageSize, sql, parameters);
		PageResult<T> finalPageResult = new PageResult<T>(pageResult);
		finalPageResult.setResultDatas(listMap2listClass(targetClass, pageResult.getResultDatas(), null));
		if(logger.isDebugEnabled()) {
			logger.debug("分页查询的结果: {}", finalPageResult.toString());
		}
		return finalPageResult;
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
