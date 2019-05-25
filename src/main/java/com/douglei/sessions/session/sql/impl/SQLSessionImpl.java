package com.douglei.sessions.session.sql.impl;

import java.util.List;
import java.util.Map;

import com.douglei.configuration.environment.mapping.Mapping;
import com.douglei.configuration.environment.mapping.MappingType;
import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.database.metadata.sql.SqlMetadata;
import com.douglei.database.sql.ConnectionWrapper;
import com.douglei.database.sql.pagequery.PageResult;
import com.douglei.sessions.session.MappingMismatchingException;
import com.douglei.sessions.session.persistent.execution.ExecutionHolder;
import com.douglei.sessions.session.sql.SQLSession;
import com.douglei.sessions.session.sql.impl.persistent.execution.SqlExecutionHolder;
import com.douglei.sessions.sqlsession.impl.SqlSessionImpl;
import com.douglei.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class SQLSessionImpl extends SqlSessionImpl implements SQLSession {
	
	public SQLSessionImpl(ConnectionWrapper connection, EnvironmentProperty environmentProperty, MappingWrapper mappingWrapper) {
		super(connection, environmentProperty, mappingWrapper);
	}
	
	// 获取code值
	private String getCode(String namespace, String name) {
		if(StringUtil.isEmpty(name)) {
			throw new NullPointerException("参数name值不能为空");
		}
		if(namespace == null) {
			return name;
		}
		return namespace+"."+name;
	}
	
	// 获取SqlMetadata实例
	private SqlMetadata getSqlMetadata(String namespace, String name) {
		String code = getCode(namespace, name);
		Mapping mapping = mappingWrapper.getMapping(code);
		if(mapping == null) {
			throw new NullPointerException("不存在code为["+code+"]的映射");
		}
		if(mapping.getMappingType() != MappingType.SQL) {
			throw new MappingMismatchingException("传入code=["+code+"], 获取的mapping不是["+MappingType.SQL+"]类型");
		}
		return (SqlMetadata) mapping.getMetadata();
	}
	
	// 获取ExecutionHolder
	private ExecutionHolder getExecutionHolder(String namespace, String name, Object sqlParameter) {
		SqlMetadata sqlMetadata = getSqlMetadata(namespace, name);
		return new SqlExecutionHolder(sqlMetadata, sqlParameter);
	}

	@Override
	public List<Map<String, Object>> query(String namespace, String name) {
		return query(namespace, name, null);
	}

	@Override
	public List<Map<String, Object>> query(String namespace, String name, Object sqlParameter) {
		ExecutionHolder executionHolder = getExecutionHolder(namespace, name, sqlParameter);
		return super.query(executionHolder.getCurrentSql(), executionHolder.getCurrentParameters());
	}

	@Override
	public Map<String, Object> uniqueQuery(String namespace, String name) {
		return uniqueQuery(namespace, name, null);
	}

	@Override
	public Map<String, Object> uniqueQuery(String namespace, String name, Object sqlParameter) {
		ExecutionHolder executionHolder = getExecutionHolder(namespace, name, sqlParameter);
		return super.uniqueQuery(executionHolder.getCurrentSql(), executionHolder.getCurrentParameters());
	}

	@Override
	public List<Object[]> query_(String namespace, String name) {
		return query_(namespace, name, null);
	}

	@Override
	public List<Object[]> query_(String namespace, String name, Object sqlParameter) {
		ExecutionHolder executionHolder = getExecutionHolder(namespace, name, sqlParameter);
		return super.query_(executionHolder.getCurrentSql(), executionHolder.getCurrentParameters());
	}

	@Override
	public Object[] uniqueQuery_(String namespace, String name) {
		return uniqueQuery_(namespace, name, null);
	}

	@Override
	public Object[] uniqueQuery_(String namespace, String name, Object sqlParameter) {
		ExecutionHolder executionHolder = getExecutionHolder(namespace, name, sqlParameter);
		return super.uniqueQuery_(executionHolder.getCurrentSql(), executionHolder.getCurrentParameters());
	}

	@Override
	public PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String namespace, String name) {
		return pageQuery(pageNum, pageSize, namespace, name, null);
	}

	@Override
	public PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String namespace, String name, Object sqlParameter) {
		ExecutionHolder executionHolder = getExecutionHolder(namespace, name, sqlParameter);
		return super.pageQuery(pageNum, pageSize, executionHolder.getCurrentSql(), executionHolder.getCurrentParameters());
	}
	
	@Override
	public int executeUpdate(String namespace, String name) {
		return executeUpdate(namespace, name, null);
	}

	@Override
	public int executeUpdate(String namespace, String name, Object sqlParameter) {
		ExecutionHolder executionHolder = getExecutionHolder(namespace, name, sqlParameter);
		int updateRowCount = 0;
		do {
			updateRowCount += super.executeUpdate(executionHolder.getCurrentSql(), executionHolder.getCurrentParameters());
		}while(executionHolder.next());
		return updateRowCount;
	}

	@Override
	public <T> List<T> query(Class<T> targetClass, String namespace, String name) {
		return query(targetClass, namespace, name, null);
	}

	@Override
	public <T> List<T> query(Class<T> targetClass, String namespace, String name, Object sqlParameter) {
		ExecutionHolder executionHolder = getExecutionHolder(namespace, name, sqlParameter);
		return super.query(targetClass, executionHolder.getCurrentSql(), executionHolder.getCurrentParameters());
	}

	@Override
	public <T> T uniqueQuery(Class<T> targetClass, String namespace, String name) {
		return uniqueQuery(targetClass, namespace, name, null);
	}

	@Override
	public <T> T uniqueQuery(Class<T> targetClass, String namespace, String name, Object sqlParameter) {
		ExecutionHolder executionHolder = getExecutionHolder(namespace, name, sqlParameter);
		return super.uniqueQuery(targetClass, executionHolder.getCurrentSql(), executionHolder.getCurrentParameters());
	}

	@Override
	public <T> PageResult<T> pageQuery(Class<T> targetClass, int pageNum, int pageSize, String namespace, String name) {
		return pageQuery(targetClass, pageNum, pageSize, namespace, name, null);
	}

	@Override
	public <T> PageResult<T> pageQuery(Class<T> targetClass, int pageNum, int pageSize, String namespace, String name, Object sqlParameter) {
		ExecutionHolder executionHolder = getExecutionHolder(namespace, name, sqlParameter);
		return super.pageQuery(targetClass, pageNum, pageSize, executionHolder.getCurrentSql(), executionHolder.getCurrentParameters());
	}

	@Override
	public Object executeProcedure(String namespace, String name) {
		return executeProcedure(namespace, namespace, null);
	}

	@Override
	public Object executeProcedure(String namespace, String name, Object sqlParameter) {
		// TODO Auto-generated method stub
		return null;
	}
}
