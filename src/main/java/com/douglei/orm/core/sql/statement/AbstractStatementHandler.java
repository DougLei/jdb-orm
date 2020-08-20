package com.douglei.orm.core.sql.statement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.core.sql.ReturnID;
import com.douglei.orm.core.sql.statement.entity.SqlResultsetMetadata;
import com.douglei.orm.core.utils.ResultSetUtil;
import com.douglei.tools.utils.CloseUtil;
import com.douglei.tools.utils.CollectionUtil;
import com.douglei.tools.utils.ExceptionUtil;

/**
 * StatementHander抽象父类
 * @author DougLei
 */
public abstract class AbstractStatementHandler implements StatementHandler{
	private static final Logger logger = LoggerFactory.getLogger(AbstractStatementHandler.class);
	
	protected String sql;// 执行的sql语句
	protected ReturnID returnID;
	private boolean isClosed;// 是否关闭
	
	private List<SqlResultsetMetadata> resultsetMetadatas; // 结果集的元数据
	
	protected AbstractStatementHandler(String sql, ReturnID returnID) {
		this.sql = sql;
		this.returnID = returnID;
	}
	
	/**
	 * 获取Statement实例
	 * @return
	 */
	protected abstract Statement getStatement();
	
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
			logger.debug("查询结果集元数据信息为: {}", resultsetMetadatas);
			return true;
		}
		logger.debug("查询结果集中没有数据, 无法获取结果集元数据信息");
		return false;
	}
	
	/**
	 * 执行查询结果集
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	protected List<Map<String, Object>> executeQuery(ResultSet resultSet) throws SQLException {
		try {
			if(setResutSetColumnNames(resultSet)) {
				return ResultSetUtil.getResultSetListMap(resultsetMetadatas, resultSet);
			}
			return Collections.emptyList();
		} catch (SQLException e) {
			throw e;
		} finally {
			CloseUtil.closeDBConn(resultSet);
		}
	}
	
	/**
	 * 执行查询(唯一)结果
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	protected Map<String, Object> executeUniqueQuery(ResultSet resultSet) throws SQLException {
		try {
			if(setResutSetColumnNames(resultSet)) {
				Map<String, Object> result = ResultSetUtil.getResultSetMap(resultsetMetadatas, resultSet);
				if(resultSet.next()) {
					throw new NonUniqueDataException("进行唯一查询时, 查询出多条数据");
				}
				return result;
			}
			return null;
		} catch (SQLException e) {
			throw e;
		} finally {
			CloseUtil.closeDBConn(resultSet);
		}
	}
	
	/**
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	protected List<Object[]> executeQuery_(ResultSet resultSet) throws SQLException {
		try {
			if(setResutSetColumnNames(resultSet)) {
				return ResultSetUtil.getResultSetListArray(resultsetMetadatas, resultSet);
			}
			return Collections.emptyList();
		} catch (SQLException e) {
			throw e;
		} finally {
			CloseUtil.closeDBConn(resultSet);
		}
	}
	
	/**
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	protected Object[] executeUniqueQuery_(ResultSet resultSet) throws SQLException {
		try {
			if(setResutSetColumnNames(resultSet)) {
				Object[] result = ResultSetUtil.getResultSetArray(resultsetMetadatas, resultSet);
				if(resultSet.next()) {
					throw new NonUniqueDataException("进行唯一查询时, 查询出多条数据");
				}
				return result;
			}
			return null;
		} catch (SQLException e) {
			throw e;
		} finally {
			CloseUtil.closeDBConn(resultSet);
		}
	}
	
	@Override
	public boolean isClosed() {
		return isClosed;
	} 
	
	@Override
	public void close() {
		if(!isClosed) {
			isClosed = true;
			if(CollectionUtil.unEmpty(resultsetMetadatas)) 
				resultsetMetadatas.clear();
			
			Statement statement = getStatement();
			try {
				statement.close();
			} catch (SQLException e) {
				logger.error("关闭[{}]时出现异常: {}", statement.getClass().getName(), ExceptionUtil.getExceptionDetailMessage(e));
				throw new CloseStatementException(statement, e);
			}
		}
	}
	
	/**
	 * 获取oracle数据库的序列值
	 * @param statement 执行对象
	 * @return
	 * @throws SQLException 
	 */
	protected int getOracleSeqCurval(Statement statement) throws SQLException {
		if(logger.isDebugEnabled())
			logger.debug("查询ORACLE序列值SQL = select {}.currval from dual", returnID.getOracleSequenceName());
		
		int seqVal = -1;
		ResultSet rs = statement.executeQuery("select " + returnID.getOracleSequenceName() + ".currval from dual");
		if(rs.next())
			seqVal = rs.getInt(1);
		
		rs.close();
		if(statement != getStatement())
			statement.close();
		return seqVal;
	}
}
