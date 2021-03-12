package com.douglei.orm.sessionfactory.sessions.session.sqlquery.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.environment.datasource.ConnectionEntity;
import com.douglei.orm.mapping.impl.sqlquery.metadata.SqlQueryMetadata;
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
	 * 获取SqlQueryEntity实例
	 * @param parameter
	 * @return
	 */
	private SqlQueryEntity getSqlQueryEntity(SQLQueryParameter parameter) {
		SqlQueryMetadata metadata = cache.get(parameter.getName());
		if(metadata == null) {
			metadata= mappingHandler.getSqlQueryMetadata(parameter.getName());
			cache.put(parameter.getName(), metadata);
		}
		return new SqlQueryAssembler(metadata, parameter.getParameters()).assembling();
	}

	@Override
	public List<Map<String, Object>> query(SQLQueryParameter parameter) {
		SqlQueryEntity entity = getSqlQueryEntity(parameter);
		return super.query(entity.getSql(), entity.getParameterValues());
	}

	@Override
	public <T> List<T> query(Class<T> targetClass, SQLQueryParameter parameter) {
		SqlQueryEntity entity = getSqlQueryEntity(parameter);
		return super.query(targetClass, entity.getSql(), entity.getParameterValues());
	}

	@Override
	public List<Object[]> query_(SQLQueryParameter parameter) {
		SqlQueryEntity entity = getSqlQueryEntity(parameter);
		return super.query_(entity.getSql(), entity.getParameterValues());
	}

	@Override
	public Map<String, Object> uniqueQuery(SQLQueryParameter parameter) {
		SqlQueryEntity entity = getSqlQueryEntity(parameter);
		return super.uniqueQuery(entity.getSql(), entity.getParameterValues());
	}

	@Override
	public <T> T uniqueQuery(Class<T> targetClass, SQLQueryParameter parameter) {
		SqlQueryEntity entity = getSqlQueryEntity(parameter);
		return super.uniqueQuery(targetClass, entity.getSql(), entity.getParameterValues());
	}

	@Override
	public Object[] uniqueQuery_(SQLQueryParameter parameter) {
		SqlQueryEntity entity = getSqlQueryEntity(parameter);
		return super.uniqueQuery_(entity.getSql(), entity.getParameterValues());
	}

	@Override
	public List<Map<String, Object>> limitQuery(int startRow, int length, SQLQueryParameter parameter) {
		SqlQueryEntity entity = getSqlQueryEntity(parameter);
		return super.limitQuery(startRow, length, entity.getSql(), entity.getParameterValues());
	}

	@Override
	public <T> List<T> limitQuery(Class<T> targetClass, int startRow, int length, SQLQueryParameter parameter) {
		SqlQueryEntity entity = getSqlQueryEntity(parameter);
		return super.limitQuery(targetClass, startRow, length, entity.getSql(), entity.getParameterValues());
	}

	@Override
	public List<Object[]> limitQuery_(int startRow, int length, SQLQueryParameter parameter) {
		SqlQueryEntity entity = getSqlQueryEntity(parameter);
		return super.limitQuery_(startRow, length, entity.getSql(), entity.getParameterValues());
	}

	@Override
	public long countQuery(SQLQueryParameter parameter) {
		SqlQueryEntity entity = getSqlQueryEntity(parameter);
		return super.countQuery(entity.getSql(), entity.getParameterValues());
	}

	@Override
	public PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, SQLQueryParameter parameter) {
		SqlQueryEntity entity = getSqlQueryEntity(parameter);
		return super.pageQuery(pageNum, pageSize, entity.getSql(), entity.getParameterValues());
	}

	@Override
	public <T> PageResult<T> pageQuery(Class<T> targetClass, int pageNum, int pageSize, SQLQueryParameter parameter) {
		SqlQueryEntity entity = getSqlQueryEntity(parameter);
		return super.pageQuery(targetClass, pageNum, pageSize, entity.getSql(), entity.getParameterValues());
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
