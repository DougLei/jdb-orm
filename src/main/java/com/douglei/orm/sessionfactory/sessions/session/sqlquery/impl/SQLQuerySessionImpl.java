package com.douglei.orm.sessionfactory.sessions.session.sqlquery.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.environment.datasource.ConnectionEntity;
import com.douglei.orm.mapping.impl.sqlquery.metadata.SqlQueryMetadata;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.ExecutableSql;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.QueryPurposeEntity;
import com.douglei.orm.sessionfactory.sessions.session.sqlquery.SQLQuerySession;
import com.douglei.orm.sessionfactory.sessions.sqlsession.PageRecursiveEntity;
import com.douglei.orm.sessionfactory.sessions.sqlsession.RecursiveEntity;
import com.douglei.orm.sessionfactory.sessions.sqlsession.SqlSessionImpl;
import com.douglei.orm.sql.query.page.PageResult;

/**
 * 
 * @author DougLei
 */
public class SQLQuerySessionImpl extends SqlSessionImpl implements SQLQuerySession {
	private static final Logger logger = LoggerFactory.getLogger(SQLQuerySessionImpl.class);
	private Map<String, SqlQueryMetadata> cache = new HashMap<String, SqlQueryMetadata>(4);

	public SQLQuerySessionImpl(ConnectionEntity connection, Environment environment) {
		super(connection, environment);
	}
	
	/**
	 * 获取ExecutableQuerySql实例
	 * @param name
	 * @param sqlParameter
	 * @param parameters
	 * @return
	 */
	private ExecutableQuerySql getExecutableQuerySql(String name, Object sqlParameter, List<AbstractParameter> parameters) {
		SqlQueryMetadata metadata = cache.get(name);
		if(metadata == null) {
			metadata= mappingHandler.getSqlQueryMetadata(name);
			cache.put(name, metadata);
		}
		
		ExecutableSql executableSql = new ExecutableSql(QueryPurposeEntity.getSingleton(), metadata.getContent(), null, sqlParameter);
		return new QuerySqlAssembler(metadata, executableSql, parameters).assembling();
	}

	@Override
	public List<Map<String, Object>> query(String name, Object sqlParameter, List<AbstractParameter> parameters) {
		ExecutableQuerySql sql = getExecutableQuerySql(name, sqlParameter, parameters);
		return super.query(sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public <T> List<T> query(Class<T> clazz, String name, Object sqlParameter, List<AbstractParameter> parameters) {
		ExecutableQuerySql sql = getExecutableQuerySql(name, sqlParameter, parameters);
		return super.query(clazz, sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public List<Object[]> query_(String name, Object sqlParameter, List<AbstractParameter> parameters) {
		ExecutableQuerySql sql = getExecutableQuerySql(name, sqlParameter, parameters);
		return super.query_(sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public Map<String, Object> uniqueQuery(String name, Object sqlParameter, List<AbstractParameter> parameters) {
		ExecutableQuerySql sql = getExecutableQuerySql(name, sqlParameter, parameters);
		return super.uniqueQuery(sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public <T> T uniqueQuery(Class<T> clazz, String name, Object sqlParameter, List<AbstractParameter> parameters) {
		ExecutableQuerySql sql = getExecutableQuerySql(name, sqlParameter, parameters);
		return super.uniqueQuery(clazz, sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public Object[] uniqueQuery_(String name, Object sqlParameter, List<AbstractParameter> parameters) {
		ExecutableQuerySql sql = getExecutableQuerySql(name, sqlParameter, parameters);
		return super.uniqueQuery_(sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public List<Map<String, Object>> limitQuery(int startRow, int length, String name, Object sqlParameter, List<AbstractParameter> parameters) {
		ExecutableQuerySql sql = getExecutableQuerySql(name, sqlParameter, parameters);
		return super.limitQuery(startRow, length, sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public <T> List<T> limitQuery(Class<T> clazz, int startRow, int length, String name, Object sqlParameter, List<AbstractParameter> parameters) {
		ExecutableQuerySql sql = getExecutableQuerySql(name, sqlParameter, parameters);
		return super.limitQuery(clazz, startRow, length, sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public List<Object[]> limitQuery_(int startRow, int length, String name, Object sqlParameter, List<AbstractParameter> parameters) {
		ExecutableQuerySql sql = getExecutableQuerySql(name, sqlParameter, parameters);
		return super.limitQuery_(startRow, length, sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public long countQuery(String name, Object sqlParameter, List<AbstractParameter> parameters) {
		ExecutableQuerySql sql = getExecutableQuerySql(name, sqlParameter, parameters);
		return super.countQuery(sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String name, Object sqlParameter, List<AbstractParameter> parameters) {
		ExecutableQuerySql sql = getExecutableQuerySql(name, sqlParameter, parameters);
		return super.pageQuery(pageNum, pageSize, sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public <T> PageResult<T> pageQuery(Class<T> clazz, int pageNum, int pageSize, String name, Object sqlParameter, List<AbstractParameter> parameters) {
		ExecutableQuerySql sql = getExecutableQuerySql(name, sqlParameter, parameters);
		return super.pageQuery(clazz, pageNum, pageSize, sql.getCurrentSql(), sql.getCurrentParameterValues());
	}
	
	@Override
	public List<Map<String, Object>> recursiveQuery(RecursiveEntity entity, String name, Object sqlParameter, List<AbstractParameter> parameters) {
		ExecutableQuerySql sql = getExecutableQuerySql(name, sqlParameter, parameters);
		return super.recursiveQuery(entity, sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public <T> List<T> recursiveQuery(Class<T> clazz, RecursiveEntity entity, String name, Object sqlParameter, List<AbstractParameter> parameters) {
		ExecutableQuerySql sql = getExecutableQuerySql(name, sqlParameter, parameters);
		return super.recursiveQuery(clazz, entity, sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public PageResult<Map<String, Object>> pageRecursiveQuery(PageRecursiveEntity entity, String name, Object sqlParameter, List<AbstractParameter> parameters) {
		ExecutableQuerySql sql = getExecutableQuerySql(name, sqlParameter, parameters);
		return super.pageRecursiveQuery(entity, sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public <T> PageResult<T> pageRecursiveQuery(Class<T> clazz, PageRecursiveEntity entity, String name, Object sqlParameter, List<AbstractParameter> parameters) {
		ExecutableQuerySql sql = getExecutableQuerySql(name, sqlParameter, parameters);
		return super.pageRecursiveQuery(clazz, entity, sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public void close() {
		if(!isClosed) {
			if(logger.isDebugEnabled()) 
				logger.debug("close {}", getClass().getName());
			
			cache.clear();
			super.close();
		}
	}
}
