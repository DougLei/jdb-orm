package com.douglei.orm.sessionfactory.sessions.session.sqlquery.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.environment.datasource.ConnectionEntity;
import com.douglei.orm.mapping.impl.sqlquery.metadata.SqlQueryMetadata;
import com.douglei.orm.sessionfactory.sessions.session.sqlquery.SQLQueryParameter;
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
	 * @param parameter
	 * @return
	 */
	private ExecutableQuerySql getExecutableQuerySql(SQLQueryParameter parameter) {
		SqlQueryMetadata metadata = cache.get(parameter.getName());
		if(metadata == null) {
			metadata= mappingHandler.getSqlQueryMetadata(parameter.getName());
			cache.put(parameter.getName(), metadata);
		}
		return new QuerySqlAssembler(metadata, parameter.getParameters()).assembling();
	}

	@Override
	public List<Map<String, Object>> query(SQLQueryParameter parameter) {
		ExecutableQuerySql entity = getExecutableQuerySql(parameter);
		return super.query(entity.getCurrentSql(), entity.getCurrentParameterValues());
	}

	@Override
	public <T> List<T> query(Class<T> clazz, SQLQueryParameter parameter) {
		ExecutableQuerySql entity = getExecutableQuerySql(parameter);
		return super.query(clazz, entity.getCurrentSql(), entity.getCurrentParameterValues());
	}

	@Override
	public List<Object[]> query_(SQLQueryParameter parameter) {
		ExecutableQuerySql entity = getExecutableQuerySql(parameter);
		return super.query_(entity.getCurrentSql(), entity.getCurrentParameterValues());
	}

	@Override
	public Map<String, Object> uniqueQuery(SQLQueryParameter parameter) {
		ExecutableQuerySql entity = getExecutableQuerySql(parameter);
		return super.uniqueQuery(entity.getCurrentSql(), entity.getCurrentParameterValues());
	}

	@Override
	public <T> T uniqueQuery(Class<T> clazz, SQLQueryParameter parameter) {
		ExecutableQuerySql entity = getExecutableQuerySql(parameter);
		return super.uniqueQuery(clazz, entity.getCurrentSql(), entity.getCurrentParameterValues());
	}

	@Override
	public Object[] uniqueQuery_(SQLQueryParameter parameter) {
		ExecutableQuerySql entity = getExecutableQuerySql(parameter);
		return super.uniqueQuery_(entity.getCurrentSql(), entity.getCurrentParameterValues());
	}

	@Override
	public List<Map<String, Object>> limitQuery(int startRow, int length, SQLQueryParameter parameter) {
		ExecutableQuerySql entity = getExecutableQuerySql(parameter);
		return super.limitQuery(startRow, length, entity.getCurrentSql(), entity.getCurrentParameterValues());
	}

	@Override
	public <T> List<T> limitQuery(Class<T> clazz, int startRow, int length, SQLQueryParameter parameter) {
		ExecutableQuerySql entity = getExecutableQuerySql(parameter);
		return super.limitQuery(clazz, startRow, length, entity.getCurrentSql(), entity.getCurrentParameterValues());
	}

	@Override
	public List<Object[]> limitQuery_(int startRow, int length, SQLQueryParameter parameter) {
		ExecutableQuerySql entity = getExecutableQuerySql(parameter);
		return super.limitQuery_(startRow, length, entity.getCurrentSql(), entity.getCurrentParameterValues());
	}

	@Override
	public long countQuery(SQLQueryParameter parameter) {
		ExecutableQuerySql entity = getExecutableQuerySql(parameter);
		return super.countQuery(entity.getCurrentSql(), entity.getCurrentParameterValues());
	}

	@Override
	public PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, SQLQueryParameter parameter) {
		ExecutableQuerySql entity = getExecutableQuerySql(parameter);
		return super.pageQuery(pageNum, pageSize, entity.getCurrentSql(), entity.getCurrentParameterValues());
	}

	@Override
	public <T> PageResult<T> pageQuery(Class<T> clazz, int pageNum, int pageSize, SQLQueryParameter parameter) {
		ExecutableQuerySql entity = getExecutableQuerySql(parameter);
		return super.pageQuery(clazz, pageNum, pageSize, entity.getCurrentSql(), entity.getCurrentParameterValues());
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
