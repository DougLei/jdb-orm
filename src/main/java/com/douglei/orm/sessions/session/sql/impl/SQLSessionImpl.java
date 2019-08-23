package com.douglei.orm.sessions.session.sql.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.configuration.environment.mapping.MappingWrapper;
import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.context.EnvironmentContext;
import com.douglei.orm.context.MappingConfigurationContext;
import com.douglei.orm.core.metadata.sql.SqlContentMetadata;
import com.douglei.orm.core.metadata.sql.SqlMetadata;
import com.douglei.orm.core.metadata.sql.SqlParameterMetadata;
import com.douglei.orm.core.metadata.sql.SqlParameterMode;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;
import com.douglei.orm.core.metadata.sql.content.node.impl.TextSqlNode;
import com.douglei.orm.core.sql.ConnectionWrapper;
import com.douglei.orm.core.sql.pagequery.PageResult;
import com.douglei.orm.core.utils.ResultSetUtil;
import com.douglei.orm.sessions.session.MappingMismatchingException;
import com.douglei.orm.sessions.session.execution.ExecutionHolder;
import com.douglei.orm.sessions.session.sql.ExecuteSql;
import com.douglei.orm.sessions.session.sql.SQLSession;
import com.douglei.orm.sessions.session.sql.impl.execution.SqlExecutionHolder;
import com.douglei.orm.sessions.sqlsession.ProcedureExecutionException;
import com.douglei.orm.sessions.sqlsession.ProcedureExecutor;
import com.douglei.orm.sessions.sqlsession.impl.SqlSessionImpl;
import com.douglei.tools.utils.CloseUtil;
import com.douglei.tools.utils.StringUtil;

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
		SqlMetadata sm= (SqlMetadata) mapping.getMetadata();
		MappingConfigurationContext.setCurrentExecuteMappingDescription("执行code=["+code+"], dialect=["+EnvironmentContext.getEnvironmentProperty().getDialect().getType()+"]的SQL映射");
		return sm;
	}
	
	// 获取ExecutionHolder
	private ExecutionHolder getExecutionHolder(String namespace, String name, Object sqlParameter) {
		SqlMetadata sqlMetadata = getSqlMetadata(namespace, name);
		return new SqlExecutionHolder(sqlMetadata, sqlParameter);
	}

	@Override
	public List<Map<String, Object>> query(String namespace, String name, Object sqlParameter) {
		ExecutionHolder executionHolder = getExecutionHolder(namespace, name, sqlParameter);
		return super.query(executionHolder.getCurrentSql(), executionHolder.getCurrentParameters());
	}

	@Override
	public Map<String, Object> uniqueQuery(String namespace, String name, Object sqlParameter) {
		ExecutionHolder executionHolder = getExecutionHolder(namespace, name, sqlParameter);
		return super.uniqueQuery(executionHolder.getCurrentSql(), executionHolder.getCurrentParameters());
	}

	@Override
	public List<Object[]> query_(String namespace, String name, Object sqlParameter) {
		ExecutionHolder executionHolder = getExecutionHolder(namespace, name, sqlParameter);
		return super.query_(executionHolder.getCurrentSql(), executionHolder.getCurrentParameters());
	}

	@Override
	public Object[] uniqueQuery_(String namespace, String name, Object sqlParameter) {
		ExecutionHolder executionHolder = getExecutionHolder(namespace, name, sqlParameter);
		return super.uniqueQuery_(executionHolder.getCurrentSql(), executionHolder.getCurrentParameters());
	}

	@Override
	public PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String namespace, String name, Object sqlParameter) {
		ExecutionHolder executionHolder = getExecutionHolder(namespace, name, sqlParameter);
		return super.pageQuery(pageNum, pageSize, executionHolder.getCurrentSql(), executionHolder.getCurrentParameters());
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
	public <T> List<T> query(Class<T> targetClass, String namespace, String name, Object sqlParameter) {
		ExecutionHolder executionHolder = getExecutionHolder(namespace, name, sqlParameter);
		return super.query(targetClass, executionHolder.getCurrentSql(), executionHolder.getCurrentParameters());
	}

	@Override
	public <T> T uniqueQuery(Class<T> targetClass, String namespace, String name, Object sqlParameter) {
		ExecutionHolder executionHolder = getExecutionHolder(namespace, name, sqlParameter);
		return super.uniqueQuery(targetClass, executionHolder.getCurrentSql(), executionHolder.getCurrentParameters());
	}

	@Override
	public <T> PageResult<T> pageQuery(Class<T> targetClass, int pageNum, int pageSize, String namespace, String name, Object sqlParameter) {
		ExecutionHolder executionHolder = getExecutionHolder(namespace, name, sqlParameter);
		return super.pageQuery(targetClass, pageNum, pageSize, executionHolder.getCurrentSql(), executionHolder.getCurrentParameters());
	}
	
	@Override
	public ExecuteSql getExecuteSql(String namespace, String name, Object sqlParameter) {
		ExecutionHolder executionHolder = getExecutionHolder(namespace, name, sqlParameter);
		return new ExecuteSql(executionHolder);
	}

	@Override
	public Object executeProcedure(String namespace, String name, Object sqlParameter) {
		SqlMetadata sqlMetadata = getSqlMetadata(namespace, name);
		
		List<SqlContentMetadata> contents = sqlMetadata.getContents();
		if(contents == null || contents.size() == 0) {
			throw new NullPointerException(MappingConfigurationContext.getCurrentExecuteMappingDescription()+", 不存在可以执行的存储过程sql语句");
		}
		
		int length = contents.size();
		List<Object> list = new ArrayList<Object>(length);
		
		StringBuilder sqlContent = new StringBuilder();
		List<SqlParameterMetadata> sqlParameters = new ArrayList<SqlParameterMetadata>(10);
		
		List<SqlNode> sqlNodes = null;
		TextSqlNode textSqlNode = null;
		for (SqlContentMetadata content : contents) {
			reset(sqlContent, sqlParameters);
			sqlNodes = content.getRootSqlNodes();
			
			for(SqlNode sn: sqlNodes) {
				textSqlNode = (TextSqlNode) sn;
				sqlContent.append(textSqlNode.getContent()).append(" ");
				if(textSqlNode.getSqlParametersByDefinedOrder() != null) {
					sqlParameters.addAll(textSqlNode.getSqlParametersByDefinedOrder());
				}
			}
			
			list.add(executeProcedure(sqlContent.toString(), sqlParameters, sqlParameter));
		}
		if(length == 1) {
			return list.get(0);
		}
		return list;
	}
	
	private void reset(StringBuilder sqlContent, List<SqlParameterMetadata> sqlParameters) {
		if(sqlContent.length() > 0) {
			sqlContent.setLength(0);
		}
		if(sqlParameters.size() > 0) {
			sqlParameters.clear();
		}
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
					callableStatement.execute();
					
					boolean procedureSupportDirectlyReturnResultSet = EnvironmentContext.getEnvironmentProperty().getDialect().getDBFeatures().supportProcedureDirectlyReturnResultSet();
					if(outParameterCount > 0 || procedureSupportDirectlyReturnResultSet) {
						Map<String, Object> outMap = new HashMap<String, Object>(outParameterCount+(procedureSupportDirectlyReturnResultSet?4:0));
						
						SqlParameterMetadata sqlParameterMetadata = null;
						for(short i=0;i<outParameterCount;i++) {
							sqlParameterMetadata = callableSqlParameters.get(outParameterIndex[i]);
							outMap.put(sqlParameterMetadata.getName(), sqlParameterMetadata.getDBDataType().getValue(outParameterIndex[i], callableStatement));
						}
						
						if(procedureSupportDirectlyReturnResultSet) {
							processDirectlyReturnResultSet(outMap, callableStatement);
						}
						return outMap;
					}
					return null;
				} catch(SQLException e){
					throw new ProcedureExecutionException("调用并执行存储过程时出现异常", e);
				}
			}

			// 处理直接返回 ResultSet
			private void processDirectlyReturnResultSet(Map<String, Object> outMap, CallableStatement callableStatement) throws SQLException {
				byte sequence = 1;
				ResultSet rs = null;
				while((rs = callableStatement.getResultSet()) != null && rs.next()) {
					outMap.put(PROCEDURE_DIRECTLY_RETURN_RESULTSET_NAME_PREFIX + sequence, ResultSetUtil.getResultSetListMap(rs));
					CloseUtil.closeDBConn(rs);
					sequence++;
				}
			}
		});
		return executeResult;
	}
}
