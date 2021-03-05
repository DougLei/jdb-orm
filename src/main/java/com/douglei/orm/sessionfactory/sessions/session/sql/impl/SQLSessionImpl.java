package com.douglei.orm.sessionfactory.sessions.session.sql.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.environment.EnvironmentContext;
import com.douglei.orm.configuration.environment.datasource.ConnectionWrapper;
import com.douglei.orm.mapping.impl.sql.metadata.SqlMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.AutoIncrementIDMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.SqlParameterNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.SqlParameterMode;
import com.douglei.orm.sessionfactory.sessions.session.sql.SQLSession;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.ProcedurePurposeEntity;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.PurposeEntity;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.QueryPurposeEntity;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.UpdatePurposeEntity;
import com.douglei.orm.sessionfactory.sessions.sqlsession.ProcedureExecutionException;
import com.douglei.orm.sessionfactory.sessions.sqlsession.ProcedureExecutor;
import com.douglei.orm.sessionfactory.sessions.sqlsession.SqlSessionImpl;
import com.douglei.orm.sql.AutoIncrementID;
import com.douglei.orm.sql.pagequery.PageResult;
import com.douglei.orm.sql.statement.InsertResult;
import com.douglei.orm.sql.statement.util.ResultSetUtil;
import com.douglei.tools.reflect.IntrospectorUtil;

/**
 * 
 * @author DougLei
 */
public class SQLSessionImpl extends SqlSessionImpl implements SQLSession {
	private static final Logger logger = LoggerFactory.getLogger(SQLSessionImpl.class);
	private final Map<String, SqlMetadata> sqlMetadataCache = new HashMap<String, SqlMetadata>(8);
	
	public SQLSessionImpl(ConnectionWrapper connection, Environment environment) {
		super(connection, environment);
	}
	
	private SqlMetadata getSqlMetadata(String namespace) {
		SqlMetadata sqlMetadata = null;
		if(sqlMetadataCache.isEmpty() || (sqlMetadata = sqlMetadataCache.get(namespace)) == null) {
			sqlMetadata= mappingHandler.getSqlMetadata(namespace);
			sqlMetadataCache.put(namespace, sqlMetadata);
		}
		return sqlMetadata;
	}
	
	// 获取ExecutableSqlHolder
	private ExecutableSqlHolder getExecutableSqlHolder(PurposeEntity purposeEntity, String namespace, String name, Object sqlParameter) {
		SqlMetadata sqlMetadata = getSqlMetadata(namespace);
		return new ExecutableSqlHolder(purposeEntity, sqlMetadata, name, sqlParameter);
	}

	@Override
	public List<Map<String, Object>> query(String namespace, String name, Object sqlParameter) {
		ExecutableSqlHolder executableSqlHolder = getExecutableSqlHolder(QueryPurposeEntity.getSingleton(), namespace, name, sqlParameter);
		return super.query(executableSqlHolder.getCurrentSql(), executableSqlHolder.getCurrentParameterValues());
	}
	
	@Override
	public <T> List<T> query(Class<T> targetClass, String namespace, String name, Object sqlParameter) {
		ExecutableSqlHolder executableSqlHolder = getExecutableSqlHolder(QueryPurposeEntity.getSingleton(), namespace, name, sqlParameter);
		return super.query(targetClass, executableSqlHolder.getCurrentSql(), executableSqlHolder.getCurrentParameterValues());
	}
	
	@Override
	public List<Object[]> query_(String namespace, String name, Object sqlParameter) {
		ExecutableSqlHolder executableSqlHolder = getExecutableSqlHolder(QueryPurposeEntity.getSingleton(), namespace, name, sqlParameter);
		return super.query_(executableSqlHolder.getCurrentSql(), executableSqlHolder.getCurrentParameterValues());
	}

	@Override
	public Map<String, Object> uniqueQuery(String namespace, String name, Object sqlParameter) {
		ExecutableSqlHolder executableSqlHolder = getExecutableSqlHolder(QueryPurposeEntity.getSingleton(), namespace, name, sqlParameter);
		return super.uniqueQuery(executableSqlHolder.getCurrentSql(), executableSqlHolder.getCurrentParameterValues());
	}

	@Override
	public <T> T uniqueQuery(Class<T> targetClass, String namespace, String name, Object sqlParameter) {
		ExecutableSqlHolder executableSqlHolder = getExecutableSqlHolder(QueryPurposeEntity.getSingleton(), namespace, name, sqlParameter);
		return super.uniqueQuery(targetClass, executableSqlHolder.getCurrentSql(), executableSqlHolder.getCurrentParameterValues());
	}
	
	@Override
	public Object[] uniqueQuery_(String namespace, String name, Object sqlParameter) {
		ExecutableSqlHolder executableSqlHolder = getExecutableSqlHolder(QueryPurposeEntity.getSingleton(), namespace, name, sqlParameter);
		return super.uniqueQuery_(executableSqlHolder.getCurrentSql(), executableSqlHolder.getCurrentParameterValues());
	}
	
	@Override
	public List<Map<String, Object>> limitQuery(String namespace, String name, int startRow, int length, Object sqlParameter) {
		ExecutableSqlHolder executableSqlHolder = getExecutableSqlHolder(QueryPurposeEntity.getSingleton(), namespace, name, sqlParameter);
		return super.limitQuery(executableSqlHolder.getCurrentSql(), startRow, length, executableSqlHolder.getCurrentParameterValues());
	}

	@Override
	public <T> List<T> limitQuery(Class<T> targetClass, String namespace, String name, int startRow, int length, Object sqlParameter) {
		ExecutableSqlHolder executableSqlHolder = getExecutableSqlHolder(QueryPurposeEntity.getSingleton(), namespace, name, sqlParameter);
		return super.limitQuery(targetClass, executableSqlHolder.getCurrentSql(), startRow, length, executableSqlHolder.getCurrentParameterValues());
	}

	@Override
	public List<Object[]> limitQuery_(String namespace, String name, int startRow, int length, Object sqlParameter) {
		ExecutableSqlHolder executableSqlHolder = getExecutableSqlHolder(QueryPurposeEntity.getSingleton(), namespace, name, sqlParameter);
		return super.limitQuery_(executableSqlHolder.getCurrentSql(), startRow, length, executableSqlHolder.getCurrentParameterValues());
	}

	@Override
	public long countQuery(String namespace, String name, Object sqlParameter) {
		ExecutableSqlHolder executableSqlHolder = getExecutableSqlHolder(QueryPurposeEntity.getSingleton(), namespace, name, sqlParameter);
		return super.countQuery(executableSqlHolder.getCurrentSql(), executableSqlHolder.getCurrentParameterValues());
	}

	@Override
	public PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String namespace, String name, Object sqlParameter) {
		ExecutableSqlHolder executableSqlHolder = getExecutableSqlHolder(QueryPurposeEntity.getSingleton(), namespace, name, sqlParameter);
		return super.pageQuery(pageNum, pageSize, executableSqlHolder.getCurrentSql(), executableSqlHolder.getCurrentParameterValues());
	}
	
	@Override
	public <T> PageResult<T> pageQuery(Class<T> targetClass, int pageNum, int pageSize, String namespace, String name, Object sqlParameter) {
		ExecutableSqlHolder executableSqlHolder = getExecutableSqlHolder(QueryPurposeEntity.getSingleton(), namespace, name, sqlParameter);
		return super.pageQuery(targetClass, pageNum, pageSize, executableSqlHolder.getCurrentSql(), executableSqlHolder.getCurrentParameterValues());
	}
	
	// 执行update, 传入的sqlParameter为null或对象
	private int executeUpdate_(String namespace, String name, Object sqlParameter) {
		ExecutableSqlHolder executableSqlHolder = getExecutableSqlHolder(UpdatePurposeEntity.getSingleton(), namespace, name, sqlParameter);
		int updateRowCount = 0;
		AutoIncrementIDMetadata autoIncrementID = null;
		do {
			if(executableSqlHolder.getCurrentType() == ContentType.INSERT && (autoIncrementID = executableSqlHolder.getCurrentAutoIncrementID()) != null) {
				InsertResult insertResult = super.executeInsert(executableSqlHolder.getCurrentSql(), executableSqlHolder.getCurrentParameterValues(), new AutoIncrementID(autoIncrementID.getSequence()));
				updateRowCount += insertResult.getRow();
				IntrospectorUtil.setValue(autoIncrementID.getKey(), insertResult.getAutoIncrementIDValue(), autoIncrementID.getTargetObject(sqlParameter));
			}else {
				updateRowCount += super.executeUpdate(executableSqlHolder.getCurrentSql(), executableSqlHolder.getCurrentParameterValues());
			}
		}while(executableSqlHolder.next());
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
	public int executeUpdates(String namespace, String name, List<? extends Object> sqlParameters) {
		SqlMetadata sqlMetadata = getSqlMetadata(namespace);
		int updateRowCount = 0;
		AutoIncrementIDMetadata autoIncrementID = null;
		ExecutableSqlHolder executableSqlHolder = null;
		for (Object sqlParameter : sqlParameters) {
			executableSqlHolder = new ExecutableSqlHolder(UpdatePurposeEntity.getSingleton(), sqlMetadata, name, sqlParameter);
			do {
				if(executableSqlHolder.getCurrentType() == ContentType.INSERT && (autoIncrementID = executableSqlHolder.getCurrentAutoIncrementID()) != null) {
					InsertResult insertResult = super.executeInsert(executableSqlHolder.getCurrentSql(), executableSqlHolder.getCurrentParameterValues(), new AutoIncrementID(autoIncrementID.getSequence()));
					updateRowCount += insertResult.getRow();
					IntrospectorUtil.setValue(autoIncrementID.getKey(), insertResult.getAutoIncrementIDValue(), autoIncrementID.getTargetObject(sqlParameter));
				}else {
					updateRowCount += super.executeUpdate(executableSqlHolder.getCurrentSql(), executableSqlHolder.getCurrentParameterValues());
				}
			}while(executableSqlHolder.next());
		}
		return updateRowCount;
	}

	@Override
	public Object executeProcedure(String namespace, String name, Object sqlParameter) {
		ExecutableSqlHolder executableSqlHolder = new ExecutableSqlHolder(ProcedurePurposeEntity.getSingleton(), getSqlMetadata(namespace), name, null);
		return executeProcedure(executableSqlHolder.getCurrentSql(), executableSqlHolder.getCurrentParameters(), sqlParameter);
	}
	
	@Override
	public List<Object> executeProcedures(String namespace, String name, List<? extends Object> sqlParameters) {
		SqlMetadata sqlMetadata = getSqlMetadata(namespace);
		List<Object> objects = new ArrayList<Object>(sqlParameters.size());
		ExecutableSqlHolder executableSqlHolder = new ExecutableSqlHolder(ProcedurePurposeEntity.getSingleton(), sqlMetadata, name, null);
		for (Object sqlParameter : sqlParameters) 
			objects.add(executeProcedure(executableSqlHolder.getCurrentSql(), executableSqlHolder.getCurrentParameters(), sqlParameter));
		return objects;
	}
	
	// 执行存储过程
	private Object executeProcedure(String callableSqlContent, List<SqlParameterNode> callableSqlParameters, Object sqlParameter) {
		return super.executeProcedure(new ProcedureExecutor() {
			@Override
			public Object execute(Connection connection) throws ProcedureExecutionException {
				try (CallableStatement callableStatement = connection.prepareCall(callableSqlContent)){
					logger.debug("执行的存储过程sql语句为: {}", callableSqlContent);
					
					short outParameterCount = 0;
					short[] outParameterIndex = null;
					if(callableSqlParameters != null) {
						outParameterIndex = new short[callableSqlParameters.size()];
						
						short index = 1;
						Object value = null;
						for (SqlParameterNode sqlParameterMetadata : callableSqlParameters) {
							if(!sqlParameterMetadata.isPlaceholder())
								continue;
							
							logger.debug("执行的sql参数值为: index={}, parameter={}", index, sqlParameterMetadata);
							if(sqlParameterMetadata.getMode() != SqlParameterMode.OUT) {
								value = sqlParameterMetadata.getValue(sqlParameter);
								if(value == null) {
									callableStatement.setNull(index, sqlParameterMetadata.getDBDataType().getSqlType());
								}else {
									sqlParameterMetadata.getDBDataType().setValue(callableStatement, index, value);
								}
							}
							
							if(sqlParameterMetadata.getMode() != SqlParameterMode.IN) {
								callableStatement.registerOutParameter(index, sqlParameterMetadata.getDBDataType().getSqlType());
								outParameterIndex[outParameterCount] = index;
								outParameterCount++;
							}
							index++;
						}
					}
					
					boolean returnResultSet = callableStatement.execute();// 记录执行后, 是否返回结果集, 该参数值针对supportProcedureDirectlyReturnResultSet=true的数据库有用
					boolean supportProcedureDirectlyReturnResultSet = EnvironmentContext.getEnvironment().getDialect().getDatabaseType().supportProcedureDirectlyReturnResultSet();
					if(outParameterCount > 0 || supportProcedureDirectlyReturnResultSet) {
						Map<String, Object> outMap = new HashMap<String, Object>(8);
						
						if(outParameterCount > 0) {
							SqlParameterNode sqlParameterMetadata = null;
							for(short i=0;i<outParameterCount;i++) {
								sqlParameterMetadata = callableSqlParameters.get(outParameterIndex[i]-1);
								outMap.put(sqlParameterMetadata.getName(), sqlParameterMetadata.getDBDataType().getValue(outParameterIndex[i], callableStatement));
							}
						}
						
						if(supportProcedureDirectlyReturnResultSet) 
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
				int sequence = 1;
				do {
					if(returnResultSet) {
						outMap.put("_procedure_resultset_" + sequence++, ResultSetUtil.getListMap(callableStatement.getResultSet()));
					}else {
						if(callableStatement.getUpdateCount() == -1)
							break;
					}
					returnResultSet = callableStatement.getMoreResults();
				}while(true);
			}
		});
	}

	@Override
	public void close() {
		if(!isClosed) {
			if(logger.isDebugEnabled()) 
				logger.debug("close {}", getClass().getName());
			
			if(sqlMetadataCache.size() > 0)
				sqlMetadataCache.clear();
			super.close();
		}
	}
}
