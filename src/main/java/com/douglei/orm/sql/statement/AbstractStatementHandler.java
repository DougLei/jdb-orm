package com.douglei.orm.sql.statement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.sql.ReturnID;
import com.douglei.orm.sql.statement.entity.SqlResultsetMetadata;
import com.douglei.orm.sql.statement.util.ResultSetUtil;
import com.douglei.tools.ExceptionUtil;

/**
 * StatementHander抽象父类
 * @author DougLei
 */
public abstract class AbstractStatementHandler implements StatementHandler{
	private static final Logger logger = LoggerFactory.getLogger(AbstractStatementHandler.class);
	
	protected String sql;// 执行的sql语句
	protected ReturnID returnID;
	
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
	protected final List<Map<String, Object>> executeQuery(ResultSet resultSet) throws SQLException {
		try {
			if(setResutSetColumnNames(resultSet)) 
				return ResultSetUtil.getResultSetListMap(1, -1, resultsetMetadatas, resultSet);
			return Collections.emptyList();
		} catch (SQLException e) {
			throw e;
		} finally {
			resultSet.close();
		}
	}
	
	/**
	 * 执行限制查询
	 * @param startRow 起始的行数, 值从1开始, 小于1时会修正为1
	 * @param length 长度, 小于1时会修正为1
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	protected final List<Map<String, Object>> executeLimitQuery(int startRow, int length, ResultSet resultSet) throws SQLException {
		try {
			if(setResutSetColumnNames(resultSet)) {
				if(startRow < 1) startRow=1;
				if(length < 1) length=1;
				return ResultSetUtil.getResultSetListMap(startRow, length, resultsetMetadatas, resultSet);
			}
			return Collections.emptyList();
		} catch (SQLException e) {
			throw e;
		} finally {
			resultSet.close();
		}
	}
	
	/**
	 * 执行查询(唯一)结果
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	protected final Map<String, Object> executeUniqueQuery(ResultSet resultSet) throws SQLException {
		try {
			if(setResutSetColumnNames(resultSet)) {
				Map<String, Object> result = ResultSetUtil.getResultSetMap(resultsetMetadatas, resultSet);
				if(resultSet.next()) 
					throw new NonUniqueDataException("进行唯一查询时, 查询出多条数据");
				return result;
			}
			return null;
		} catch (SQLException e) {
			throw e;
		} finally {
			resultSet.close();
		}
	}
	
	/**
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	protected final List<Object[]> executeQuery_(ResultSet resultSet) throws SQLException {
		try {
			if(setResutSetColumnNames(resultSet)) 
				return ResultSetUtil.getResultSetListArray(1, -1, resultsetMetadatas, resultSet);
			return Collections.emptyList();
		} catch (SQLException e) {
			throw e;
		} finally {
			resultSet.close();
		}
	}
	
	/**
	 * 
	 * @param startRow
	 * @param length
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	protected final List<Object[]> executeLimitQuery_(int startRow, int length, ResultSet resultSet) throws SQLException {
		try {
			if(setResutSetColumnNames(resultSet)) {
				if(startRow < 1) startRow=1;
				if(length < 1) length=1;
				return ResultSetUtil.getResultSetListArray(startRow, length, resultsetMetadatas, resultSet);
			}
			return Collections.emptyList();
		} catch (SQLException e) {
			throw e;
		} finally {
			resultSet.close();
		}
	}
	
	/**
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	protected final Object[] executeUniqueQuery_(ResultSet resultSet) throws SQLException {
		try {
			if(setResutSetColumnNames(resultSet)) {
				Object[] result = ResultSetUtil.getResultSetArray(resultsetMetadatas, resultSet);
				if(resultSet.next()) 
					throw new NonUniqueDataException("进行唯一查询时, 查询出多条数据");
				return result;
			}
			return null;
		} catch (SQLException e) {
			throw e;
		} finally {
			resultSet.close();
		}
	}
	
	@Override
	public final void close() {
		try {
			if(resultsetMetadatas != null && resultsetMetadatas.size() > 0) 
				resultsetMetadatas.clear();
			getStatement().close();
		} catch (SQLException e) {
			logger.error("关闭[{}]时出现异常: {}", getStatement().getClass().getName(), ExceptionUtil.getExceptionDetailMessage(e));
		}
	}
	
	/**
	 * 获取oracle数据库的序列值
	 * @param statement 执行对象
	 * @return
	 * @throws SQLException 
	 */
	protected final int getOracleSeqCurval(Statement statement) throws SQLException {
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
