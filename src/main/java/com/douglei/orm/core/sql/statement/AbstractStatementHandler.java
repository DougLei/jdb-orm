package com.douglei.orm.core.sql.statement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.core.sql.statement.entity.SqlResultsetMetadata;
import com.douglei.orm.core.utils.ResultSetUtil;
import com.douglei.tools.utils.CloseUtil;

/**
 * StatementHander抽象父类
 * @author DougLei
 */
public abstract class AbstractStatementHandler implements StatementHandler{
	private static final Logger logger = LoggerFactory.getLogger(AbstractStatementHandler.class);
	
	protected String sql;// 执行的sql语句
	private boolean isExecuted;// 是否已经执行过
	private boolean isClosed;// 是否关闭
	private short currentEecutedIndex;// 当前执行的index, 可以理解为当前共执行了多少次, 从0开始
	
	// <列名:值>
	private List<Map<String, Object>> queryUniqueResult;
	private List<List<Map<String, Object>>> queryResultList;
	// 结果集的元数据
	private List<SqlResultsetMetadata> resultsetMetadatas;
	
	// <值> [数组]
	private List<Object[]> queryUniqueResult_;
	private List<List<Object[]>> queryResultList_;
	
	protected AbstractStatementHandler(String sql) {
		this.sql = sql;
	}

	/**
	 * 记录[标识]该StatementHandler已经执行过
	 */
	private void recordStatementHandlerIsExecuted() {
		if(isExecuted) {
			currentEecutedIndex++;
		}else {
			isExecuted = true;
		}
	}
	
	/**
	 * 设置查询结果集的列名集合
	 * @param resultSet
	 * @return 返回resultSet是否包含结果集
	 * @throws SQLException 
	 */
	private boolean setResutSetColumnNames(ResultSet resultSet) throws SQLException {
		logger.debug("开始获取查询结果集的元数据信息");
		if(resultSet != null && resultSet.next()) {
			logger.debug("查询结果集ResultSet实例中存在数据");
			if(resultsetMetadatas == null) {
				logger.debug("resultsetMetadatas集合为空, 需要设置结果集元数据");
				resultsetMetadatas = ResultSetUtil.getSqlResultSetMetadata(resultSet);
			}
			if(logger.isDebugEnabled()) {
				logger.debug("查询结果集元数据信息为: {}", resultsetMetadatas.toString());
			}
			return true;
		}
		logger.debug("查询结果集中没有数据, 无法获取结果集元数据信息");
		return false;
	}
	
	/**
	 * <pre>
	 * 	根据resultSet设置查询结果集信息
	 * 	同时, 记录[标识]该StatementHandler已经执行过
	 * </pre>
	 * @param resultSet
	 * @throws SQLException 
	 */
	private void setQueryResultList(ResultSet resultSet) throws SQLException {
		logger.debug("开始设置查询结果集集合");
		if(queryResultList == null) {
			queryResultList = new ArrayList<List<Map<String,Object>>>(3);
		}
		if(setResutSetColumnNames(resultSet)) {
			queryResultList.add(ResultSetUtil.getResultSetListMap(resultsetMetadatas, resultSet));
		}else {
			queryResultList.add(null);
		}
		recordStatementHandlerIsExecuted();
	}
	
	/**
	 * <pre>
	 * 	根据resultSet设置查询唯一结果信息
	 * 	同时, 记录[标识]该StatementHandler已经执行过
	 * </pre>
	 * @param resultSet
	 * @throws SQLException 
	 */
	private void setQueryUniqueResult(ResultSet resultSet) throws SQLException {
		logger.debug("开始设置查询唯一结果集");
		if(queryUniqueResult == null) {
			queryUniqueResult = new ArrayList<Map<String,Object>>(3);
		}
		if(setResutSetColumnNames(resultSet)) {
			queryUniqueResult.add(ResultSetUtil.getResultSetMap(resultsetMetadatas, resultSet));
			if(resultSet.next()) {
				throw new NonUniqueDataException("进行唯一查询时, 查询出多条数据");
			}
		}else {
			queryUniqueResult.add(null);
		}
		recordStatementHandlerIsExecuted();
	}
	
	/**
	 * <pre>
	 * 	根据resultSet设置查询结果集信息
	 * 	同时, 记录[标识]该StatementHandler已经执行过
	 * </pre>
	 * @param resultSet
	 * @throws SQLException 
	 */
	private void setQueryResultList_(ResultSet resultSet) throws SQLException {
		logger.debug("开始设置查询结果集集合");
		if(queryResultList_ == null) {
			queryResultList_ = new ArrayList<List<Object[]>>(3);
		}
		if(setResutSetColumnNames(resultSet)) {
			queryResultList_.add(ResultSetUtil.getResultSetListArray(resultsetMetadatas, resultSet));
		}else {
			queryResultList_.add(null);
		}
		recordStatementHandlerIsExecuted();
	}
	
	/**
	 * <pre>
	 * 	根据resultSet设置查询唯一结果信息
	 * 	同时, 记录[标识]该StatementHandler已经执行过
	 * </pre>
	 * @param resultSet
	 * @throws SQLException 
	 */
	private void setQueryUniqueResult_(ResultSet resultSet) throws SQLException {
		logger.debug("开始设置查询唯一结果集");
		if(queryUniqueResult_ == null) {
			queryUniqueResult_ = new ArrayList<Object[]>(3);
		}
		if(setResutSetColumnNames(resultSet)) {
			queryUniqueResult_.add(ResultSetUtil.getResultSetArray(resultsetMetadatas, resultSet));
			if(resultSet.next()) {
				throw new NonUniqueDataException("进行唯一查询时, 查询出多条数据");
			}
		}else {
			queryUniqueResult_.add(null);
		}
		recordStatementHandlerIsExecuted();
	}
	
	/**
	 * 验证Statement是否已经被关闭
	 * @throws StatementIsClosedException 
	 */
	protected void validateStatementIsClosed() throws StatementIsClosedException {
		if(isClosed()) {
			throw new StatementIsClosedException();
		}
	}
	
	protected List<Map<String, Object>> executeQuery(ResultSet resultSet) throws SQLException {
		try {
			setQueryResultList(resultSet);
			return getQueryResultList(currentEecutedIndex);
		} catch (SQLException e) {
			throw e;
		} finally {
			CloseUtil.closeDBConn(resultSet);
		}
	}
	
	protected Map<String, Object> executeUniqueQuery(ResultSet resultSet) throws SQLException {
		try {
			setQueryUniqueResult(resultSet);
			return getQueryUniqueResult(currentEecutedIndex);
		} catch (SQLException e) {
			throw e;
		} finally {
			CloseUtil.closeDBConn(resultSet);
		}
	}
	
	protected List<Object[]> executeQuery_(ResultSet resultSet) throws SQLException {
		try {
			setQueryResultList_(resultSet);
			return getQueryResultList_(currentEecutedIndex);
		} catch (SQLException e) {
			throw e;
		} finally {
			CloseUtil.closeDBConn(resultSet);
		}
	}
	
	protected Object[] executeUniqueQuery_(ResultSet resultSet) throws SQLException {
		try {
			setQueryUniqueResult_(resultSet);
			return getQueryUniqueResult_(currentEecutedIndex);
		} catch (SQLException e) {
			throw e;
		} finally {
			CloseUtil.closeDBConn(resultSet);
		}
	}
	
	protected List<Map<String, Object>> getQueryResultList(int index) {
		return queryResultList.get(index);
	}
	protected Map<String, Object> getQueryUniqueResult(int index) {
		return queryUniqueResult.get(index);
	}
	protected List<Object[]> getQueryResultList_(int index) {
		return queryResultList_.get(index);
	}
	protected Object[] getQueryUniqueResult_(int index) {
		return queryUniqueResult_.get(index);
	}
	
	@Override
	public boolean isExecuted() {
		return isExecuted;
	}
	
	@Override
	public boolean isClosed() {
		return isClosed;
	} 
	
	@Override
	public void close() throws StatementExecutionException{
		isClosed = true;
		if(resultsetMetadatas != null && resultsetMetadatas.size() > 0) {
			resultsetMetadatas.clear();
		}
	}
	
	/**
	 * 关闭 {@link Statement} or {@link PreparedStatement}
	 * @param statement
	 * @throws StatementExecutionException 
	 */
	protected void closeStatement(Statement statement) throws StatementExecutionException {
		try {
			statement.close();
		} catch (SQLException e) {
			throw new StatementExecutionException("关闭["+statement.getClass().getName()+"]时出现异常", e);
		}
	}
}
