package com.douglei.orm.sessionfactory.sessions.session.sqlquery.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.environment.datasource.ConnectionEntity;
import com.douglei.orm.mapping.impl.sqlquery.metadata.SqlQueryMetadata;
import com.douglei.orm.sessionfactory.sessions.session.sqlquery.SQLQueryEntity;
import com.douglei.orm.sessionfactory.sessions.session.sqlquery.SQLQuerySession;
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
	 * @param entity
	 * @return
	 */
	private ExecutableQuerySql getExecutableQuerySql(SQLQueryEntity entity) {
		SqlQueryMetadata metadata = cache.get(entity.getName());
		if(metadata == null) {
			metadata= mappingHandler.getSqlQueryMetadata(entity.getName());
			cache.put(entity.getName(), metadata);
		}
		return new QuerySqlAssembler(metadata, entity.getParameters()).assembling();
	}

	@Override
	public List<Map<String, Object>> query(SQLQueryEntity entity) {
		ExecutableQuerySql sql = getExecutableQuerySql(entity);
		return super.query(sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public <T> List<T> query(Class<T> clazz, SQLQueryEntity entity) {
		ExecutableQuerySql sql = getExecutableQuerySql(entity);
		return super.query(clazz, sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public List<Object[]> query_(SQLQueryEntity entity) {
		ExecutableQuerySql sql = getExecutableQuerySql(entity);
		return super.query_(sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public Map<String, Object> uniqueQuery(SQLQueryEntity entity) {
		ExecutableQuerySql sql = getExecutableQuerySql(entity);
		return super.uniqueQuery(sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public <T> T uniqueQuery(Class<T> clazz, SQLQueryEntity entity) {
		ExecutableQuerySql sql = getExecutableQuerySql(entity);
		return super.uniqueQuery(clazz, sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public Object[] uniqueQuery_(SQLQueryEntity entity) {
		ExecutableQuerySql sql = getExecutableQuerySql(entity);
		return super.uniqueQuery_(sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public List<Map<String, Object>> limitQuery(int startRow, int length, SQLQueryEntity entity) {
		ExecutableQuerySql sql = getExecutableQuerySql(entity);
		return super.limitQuery(startRow, length, sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public <T> List<T> limitQuery(Class<T> clazz, int startRow, int length, SQLQueryEntity entity) {
		ExecutableQuerySql sql = getExecutableQuerySql(entity);
		return super.limitQuery(clazz, startRow, length, sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public List<Object[]> limitQuery_(int startRow, int length, SQLQueryEntity entity) {
		ExecutableQuerySql sql = getExecutableQuerySql(entity);
		return super.limitQuery_(startRow, length, sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public long countQuery(SQLQueryEntity entity) {
		ExecutableQuerySql sql = getExecutableQuerySql(entity);
		return super.countQuery(sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, SQLQueryEntity entity) {
		ExecutableQuerySql sql = getExecutableQuerySql(entity);
		return super.pageQuery(pageNum, pageSize, sql.getCurrentSql(), sql.getCurrentParameterValues());
	}

	@Override
	public <T> PageResult<T> pageQuery(Class<T> clazz, int pageNum, int pageSize, SQLQueryEntity entity) {
		ExecutableQuerySql sql = getExecutableQuerySql(entity);
		return super.pageQuery(clazz, pageNum, pageSize, sql.getCurrentSql(), sql.getCurrentParameterValues());
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
