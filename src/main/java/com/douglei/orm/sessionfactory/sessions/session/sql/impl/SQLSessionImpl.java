package com.douglei.orm.sessionfactory.sessions.session.sql.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.douglei.orm.EnvironmentContext;
import com.douglei.orm.environment.datasource.ConnectionWrapper;
import com.douglei.orm.environment.property.EnvironmentProperty;
import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.impl.sql.metadata.SqlMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.impl.sql.metadata.content.IncrementIdValueConfig;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterMode;
import com.douglei.orm.mapping.type.MappingTypeConstants;
import com.douglei.orm.sessionfactory.sessions.session.MappingMismatchingException;
import com.douglei.orm.sessionfactory.sessions.session.sql.SQLSession;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.PurposeEntity;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.SqlExecuteHandler;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.impl.ProcedurePurposeEntity;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.impl.QueryPurposeEntity;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.impl.UpdatePurposeEntity;
import com.douglei.orm.sessionfactory.sessions.sqlsession.ProcedureExecutionException;
import com.douglei.orm.sessionfactory.sessions.sqlsession.ProcedureExecutor;
import com.douglei.orm.sessionfactory.sessions.sqlsession.impl.SqlSessionImpl;
import com.douglei.orm.sql.ReturnID;
import com.douglei.orm.sql.pagequery.PageResult;
import com.douglei.orm.sql.statement.InsertResult;
import com.douglei.orm.sql.statement.util.ResultSetUtil;
import com.douglei.tools.utils.CloseUtil;
import com.douglei.tools.utils.reflect.IntrospectorUtil;

/**
 * 
 * @author DougLei
 */
public class SQLSessionImpl extends SqlSessionImpl implements SQLSession {
	private final Map<String, SqlMetadata> sqlMetadataCache = new HashMap<String, SqlMetadata>(8);
	
	public SQLSessionImpl(ConnectionWrapper connection, EnvironmentProperty environmentProperty) {
		super(connection, environmentProperty);
	}
	
	private SqlMetadata getSqlMetadata(String namespace) {
		SqlMetadata sm = null;
		if(sqlMetadataCache.isEmpty() || (sm = sqlMetadataCache.get(namespace)) == null) {
			Mapping mapping = mappingContainer.getMapping(namespace);
			if(mapping == null)
				throw new NullPointerException("不存在code为"+namespace+"的mapping");
			if(!MappingTypeConstants.SQL.equals(mapping.getType())) 
				throw new MappingMismatchingException("code为"+namespace+"的mapping不是["+MappingTypeConstants.SQL+"]类型");
			sm= (SqlMetadata) mapping.getMetadata();
			sqlMetadataCache.put(namespace, sm);
		}
		return sm;
	}
	
	// 获取SqlExecuteHandler
	private SqlExecuteHandler getSqlExecuteHandler(PurposeEntity purposeEntity, String namespace, String name, Object sqlParameter) {
		SqlMetadata sqlMetadata = getSqlMetadata(namespace);
		return new SqlExecuteHandler(purposeEntity, sqlMetadata, name, sqlParameter);
	}

	@Override
	public List<Map<String, Object>> query(String namespace, String name, Object sqlParameter) {
		SqlExecuteHandler executeHandler = getSqlExecuteHandler(QueryPurposeEntity.DEFAULT, namespace, name, sqlParameter);
		return super.query(executeHandler.getCurrentSql(), executeHandler.getCurrentParameters());
	}
	
	@Override
	public <T> List<T> query(Class<T> targetClass, String namespace, String name, Object sqlParameter) {
		SqlExecuteHandler executeHandler = getSqlExecuteHandler(QueryPurposeEntity.DEFAULT, namespace, name, sqlParameter);
		return super.query(targetClass, executeHandler.getCurrentSql(), executeHandler.getCurrentParameters());
	}
	
	@Override
	public List<Object[]> query_(String namespace, String name, Object sqlParameter) {
		SqlExecuteHandler executeHandler = getSqlExecuteHandler(QueryPurposeEntity.DEFAULT, namespace, name, sqlParameter);
		return super.query_(executeHandler.getCurrentSql(), executeHandler.getCurrentParameters());
	}

	@Override
	public Map<String, Object> uniqueQuery(String namespace, String name, Object sqlParameter) {
		SqlExecuteHandler executeHandler = getSqlExecuteHandler(QueryPurposeEntity.DEFAULT, namespace, name, sqlParameter);
		return super.uniqueQuery(executeHandler.getCurrentSql(), executeHandler.getCurrentParameters());
	}

	@Override
	public <T> T uniqueQuery(Class<T> targetClass, String namespace, String name, Object sqlParameter) {
		SqlExecuteHandler executeHandler = getSqlExecuteHandler(QueryPurposeEntity.DEFAULT, namespace, name, sqlParameter);
		return super.uniqueQuery(targetClass, executeHandler.getCurrentSql(), executeHandler.getCurrentParameters());
	}
	
	@Override
	public Object[] uniqueQuery_(String namespace, String name, Object sqlParameter) {
		SqlExecuteHandler executeHandler = getSqlExecuteHandler(QueryPurposeEntity.DEFAULT, namespace, name, sqlParameter);
		return super.uniqueQuery_(executeHandler.getCurrentSql(), executeHandler.getCurrentParameters());
	}
	
	@Override
	public Map<String, Object> queryFirst(String namespace, String name, Object sqlParameter) {
		SqlExecuteHandler executeHandler = getSqlExecuteHandler(QueryPurposeEntity.DEFAULT, namespace, name, sqlParameter);
		return super.queryFirst(executeHandler.getCurrentSql(), executeHandler.getCurrentParameters());
	}

	@Override
	public <T> T queryFirst(Class<T> targetClass, String namespace, String name, Object sqlParameter) {
		SqlExecuteHandler executeHandler = getSqlExecuteHandler(QueryPurposeEntity.DEFAULT, namespace, name, sqlParameter);
		return super.queryFirst(targetClass, executeHandler.getCurrentSql(), executeHandler.getCurrentParameters());
	}

	@Override
	public Object[] queryFirst_(String namespace, String name, Object sqlParameter) {
		SqlExecuteHandler executeHandler = getSqlExecuteHandler(QueryPurposeEntity.DEFAULT, namespace, name, sqlParameter);
		return super.queryFirst_(executeHandler.getCurrentSql(), executeHandler.getCurrentParameters());
	}

	@Override
	public long countQuery(String namespace, String name, Object sqlParameter) {
		SqlExecuteHandler executeHandler = getSqlExecuteHandler(QueryPurposeEntity.DEFAULT, namespace, name, sqlParameter);
		return super.countQuery(executeHandler.getCurrentSql(), executeHandler.getCurrentParameters());
	}

	@Override
	public PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String namespace, String name, Object sqlParameter) {
		SqlExecuteHandler executeHandler = getSqlExecuteHandler(QueryPurposeEntity.DEFAULT, namespace, name, sqlParameter);
		return super.pageQuery(pageNum, pageSize, executeHandler.getCurrentSql(), executeHandler.getCurrentParameters());
	}
	
	@Override
	public <T> PageResult<T> pageQuery(Class<T> targetClass, int pageNum, int pageSize, String namespace, String name, Object sqlParameter) {
		SqlExecuteHandler executeHandler = getSqlExecuteHandler(QueryPurposeEntity.DEFAULT, namespace, name, sqlParameter);
		return super.pageQuery(targetClass, pageNum, pageSize, executeHandler.getCurrentSql(), executeHandler.getCurrentParameters());
	}
	
	@Override
	public List<Map<String, Object>> recursiveQuery(int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String namespace, String name, Object sqlParameter) {
		SqlExecuteHandler executeHandler = getSqlExecuteHandler(QueryPurposeEntity.DEFAULT, namespace, name, sqlParameter);
		return super.recursiveQuery(deep, pkColumnName, parentPkColumnName, parentValue, childNodeName, executeHandler.getCurrentSql(), executeHandler.getCurrentParameters());
	}

	@Override
	public <T> List<T> recursiveQuery(Class<T> targetClass, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String namespace, String name, Object sqlParameter) {
		SqlExecuteHandler executeHandler = getSqlExecuteHandler(QueryPurposeEntity.DEFAULT, namespace, name, sqlParameter);
		return super.recursiveQuery(targetClass, deep, pkColumnName, parentPkColumnName, parentValue, childNodeName, executeHandler.getCurrentSql(), executeHandler.getCurrentParameters());
	}
	
	@Override
	public PageResult<Map<String, Object>> pageRecursiveQuery(int pageNum, int pageSize, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String namespace, String name, Object sqlParameter) {
		SqlExecuteHandler executeHandler = getSqlExecuteHandler(QueryPurposeEntity.DEFAULT, namespace, name, sqlParameter);
		return super.pageRecursiveQuery(pageNum, pageSize, deep, pkColumnName, parentPkColumnName, parentValue, childNodeName, executeHandler.getCurrentSql(), executeHandler.getCurrentParameters());
	}

	@Override
	public <T> PageResult<T> pageRecursiveQuery(Class<T> targetClass, int pageNum, int pageSize, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String namespace, String name, Object sqlParameter) {
		SqlExecuteHandler executeHandler = getSqlExecuteHandler(QueryPurposeEntity.DEFAULT, namespace, name, sqlParameter);
		return super.pageRecursiveQuery(targetClass, pageNum, pageSize, deep, pkColumnName, parentPkColumnName, parentValue, childNodeName, executeHandler.getCurrentSql(), executeHandler.getCurrentParameters());
	}

	// 执行update, 传入的sqlParameter为null或对象
	private int executeUpdate_(String namespace, String name, Object sqlParameter) {
		IncrementIdValueConfig incrementIdValueConfig;
		InsertResult insertResult;
		
		SqlExecuteHandler executeHandler = getSqlExecuteHandler(UpdatePurposeEntity.DEFAULT, namespace, name, sqlParameter);
		int updateRowCount = 0;
		do {
			if(executeHandler.getCurrentType() == ContentType.INSERT && (incrementIdValueConfig = executeHandler.getCurrentIncrementIdValueConfig()) != null) {
				insertResult = super.executeInsert(executeHandler.getCurrentSql(), executeHandler.getCurrentParameters(), new ReturnID(incrementIdValueConfig.getOracleSequenceName()));
				updateRowCount += insertResult.getRow();
				IntrospectorUtil.setProperyValue(incrementIdValueConfig.getTargetObject(sqlParameter), incrementIdValueConfig.getKey(), insertResult.getId());
			}else {
				updateRowCount += super.executeUpdate(executeHandler.getCurrentSql(), executeHandler.getCurrentParameters());
			}
		}while(executeHandler.next());
		return updateRowCount;
	}
	
	@Override
	public int executeUpdate(String namespace, String name) {
		return executeUpdate_(namespace, name, null);
	}

	@Override
	public int executeUpdate(String namespace, String name, Object sqlParameter) {
		return executeUpdate_(namespace, name, sqlParameter);
	}
	
	@Override
	public int executeUpdate(String namespace, String name, List<? extends Object> sqlParameters) {
		IncrementIdValueConfig incrementIdValueConfig;
		InsertResult insertResult;
		
		SqlMetadata sqlMetadata = getSqlMetadata(namespace);
		int updateRowCount = 0;
		SqlExecuteHandler executeHandler = null;
		for (Object sqlParameter : sqlParameters) {
			executeHandler = new SqlExecuteHandler(UpdatePurposeEntity.DEFAULT, sqlMetadata, name, sqlParameter);
			do {
				if(executeHandler.getCurrentType() == ContentType.INSERT && (incrementIdValueConfig = executeHandler.getCurrentIncrementIdValueConfig()) != null) {
					insertResult = super.executeInsert(executeHandler.getCurrentSql(), executeHandler.getCurrentParameters(), new ReturnID(incrementIdValueConfig.getOracleSequenceName()));
					updateRowCount += insertResult.getRow();
					IntrospectorUtil.setProperyValue(incrementIdValueConfig.getTargetObject(sqlParameter), incrementIdValueConfig.getKey(), insertResult.getId());
				}else {
					updateRowCount += super.executeUpdate(executeHandler.getCurrentSql(), executeHandler.getCurrentParameters());
				}
			}while(executeHandler.next());
		}
		return updateRowCount;
	}

	
	@Override
	public Object executeProcedure(String namespace, String name, Object sqlParameter) {
		return executeProcedure_(getSqlMetadata(namespace), name, sqlParameter);
	}
	
	@Override
	public List<Object> executeProcedure(String namespace, String name, List<? extends Object> sqlParameters) {
		SqlMetadata sqlMetadata = getSqlMetadata(namespace);
		List<Object> objects = new ArrayList<Object>(sqlParameters.size());
		sqlParameters.forEach(sqlParameter -> objects.add(executeProcedure_(sqlMetadata, name, sqlParameters)));
		return objects;
	}
	
	private Object executeProcedure_(SqlMetadata sqlMetadata, String name, Object sqlParameter) {
		SqlExecuteHandler executeHandler = new SqlExecuteHandler(ProcedurePurposeEntity.DEFAULT, sqlMetadata, name, sqlParameter);
		return executeProcedure(executeHandler.getCurrentSql(), executeHandler.getCurrentSqlParameters(), sqlParameter);
	}
	
	// 执行存储过程
	private Object executeProcedure(String callableSqlContent, List<SqlParameterMetadata> callableSqlParameters, Object sqlParameter) {
		Object executeResult = super.executeProcedure(new ProcedureExecutor() {
			@Override
			public Object execute(Connection connection) throws ProcedureExecutionException {
				try (CallableStatement callableStatement = connection.prepareCall(callableSqlContent)){
					
					short outParameterCount = 0;
					short[] outParameterIndex = null;
					if(callableSqlParameters.size() > 0) {
						outParameterIndex = new short[callableSqlParameters.size()];
						
						short index = 1;
						for (SqlParameterMetadata sqlParameterMetadata : callableSqlParameters) {
							if(sqlParameterMetadata.getMode() != SqlParameterMode.OUT) {
								sqlParameterMetadata.getDBDataType().setValue(callableStatement, index, sqlParameterMetadata.getValue(sqlParameter));
							}
							if(sqlParameterMetadata.getMode() != SqlParameterMode.IN) {
								callableStatement.registerOutParameter(index, sqlParameterMetadata.getDBDataType().getSqlType());
								outParameterIndex[outParameterCount] = index;
								outParameterCount++;
							}
							index++;
						}
					}
					
					boolean returnResultSet = callableStatement.execute();// 记录执行后, 是否返回结果集, 该参数值针对procedureSupportDirectlyReturnResultSet=true的数据库有用
					boolean procedureSupportDirectlyReturnResultSet = EnvironmentContext.getDialect().getObjectHandler().supportProcedureDirectlyReturnResultSet();
					if(outParameterCount > 0 || procedureSupportDirectlyReturnResultSet) {
						Map<String, Object> outMap = new HashMap<String, Object>(8);
						
						if(outParameterCount > 0) {
							SqlParameterMetadata sqlParameterMetadata = null;
							for(short i=0;i<outParameterCount;i++) {
								sqlParameterMetadata = callableSqlParameters.get(outParameterIndex[i]-1);
								outMap.put(sqlParameterMetadata.getName(), sqlParameterMetadata.getDBDataType().getValue(outParameterIndex[i], callableStatement));
							}
						}
						
						if(procedureSupportDirectlyReturnResultSet) 
							processDirectlyReturnResultSet(outMap, callableStatement, returnResultSet);
						return outMap;
					}
					return null;
				} catch(SQLException e){
					throw new ProcedureExecutionException("调用并执行存储过程时出现异常", e);
				}
			}

			// 处理直接返回 ResultSet
			private void processDirectlyReturnResultSet(Map<String, Object> outMap, CallableStatement callableStatement, boolean returnResultSet) throws SQLException {
				byte sequence = 1;
				ResultSet rs = null;
				do {
					if(returnResultSet) {
						if((rs = callableStatement.getResultSet()) != null && rs.next()) {
							outMap.put(PROCEDURE_DIRECTLY_RETURN_RESULTSET_NAME_PREFIX + sequence++, ResultSetUtil.getResultSetListMap(rs));
						}else {
							outMap.put(PROCEDURE_DIRECTLY_RETURN_RESULTSET_NAME_PREFIX + sequence++, Collections.emptyList());
						}
					}else {
						if(callableStatement.getUpdateCount() == -1)
							break;
					}
					returnResultSet = callableStatement.getMoreResults();
				}while(true);
				CloseUtil.closeDBConn(rs);
			}
		});
		return executeResult;
	}

	@Override
	public void close() {
		if(!sqlMetadataCache.isEmpty())
			sqlMetadataCache.clear();
		super.close();
	}
}
