package com.douglei.database.sql.statement;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.utils.CloseUtil;

/**
 * StatementHander抽象父类
 * @author DougLei
 */
public abstract class AbstractStatementHandler implements StatementHandler{
	private static final Logger logger = LoggerFactory.getLogger(AbstractStatementHandler.class);
	
	private boolean isExecuted;// 是否已经执行过
	private boolean isClosed;// 是否关闭
	
	private ResultSet resultSet;
	
	// <列名:值>
	private Map<String, Object> queryUniqueResult;
	private List<Map<String, Object>> queryResultList;
	// 结果集的列名, map中的key来自这里
	private List<String> resutSetColumnNames;
	
	// <值> [数组]
	private Object[] queryUniqueResult_;
	private List<Object[]> queryResultList_;
	
	/**
	 * 记录[标识]该StatementHandler已经执行过
	 */
	private void recordStatementHandlerIsExecuted() {
		this.isExecuted = true;
	}
	
	/**
	 * 设置查询结果集的列名集合
	 * @return 返回resultSet是否包含结果集
	 * @throws SQLException 
	 */
	private boolean setResutSetColumnNames() throws SQLException {
		logger.debug("开始获取查询结果集的列名信息");
		if(resultSet != null && resultSet.next()) {
			logger.debug("查询结果集ResultSet实例中存在数据");
			if(resutSetColumnNames == null) {
				logger.debug("resutSetColumnNames集合为空, 需要设置结果集列名");
				
				ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
				int columnCount = resultSetMetaData.getColumnCount();
				resutSetColumnNames = new ArrayList<String>(columnCount);
				for(int i=1;i<=columnCount;i++) {
					resutSetColumnNames.add(resultSetMetaData.getColumnName(i).toUpperCase());
				}
			}
			if(logger.isDebugEnabled()) {
				logger.debug("查询结果集列名集合为: {}", resutSetColumnNames.toString());
			}
			return true;
		}
		logger.debug("查询结果集中没有数据, 无法获取结果集列名信息");
		return false;
	}
	
	private void initialQueryResultList() {
		logger.debug("初始化 queryResultList list-map集合");
		if(queryResultList == null) {
			queryResultList = new ArrayList<Map<String,Object>>(20);
		}else if(queryResultList.size() > 0){
			queryResultList.clear();
		}
	}
	/**
	 * <pre>
	 * 	根据resultSet设置查询结果集信息
	 * 	同时, 记录[标识]该StatementHandler已经执行过
	 * </pre>
	 * @throws SQLException 
	 */
	private void setQueryResultList() throws SQLException {
		logger.debug("开始设置查询结果集集合");
		initialQueryResultList();
		
		if(setResutSetColumnNames()) {
			int columnCount = resutSetColumnNames.size();
			Map<String,Object> map = null;
			do {
				map = new HashMap<String, Object>(columnCount);
				for(int i=0;i<columnCount;i++) {
					map.put(resutSetColumnNames.get(i), resultSet.getObject((i+1)));
				}
				queryResultList.add(map);
			}while(resultSet.next());
		}
		recordStatementHandlerIsExecuted();
	}
	
	private void initialQueryUniqueResult(int initialSize) {
		logger.debug("初始化 queryUniqueResult map集合");
		if(queryUniqueResult == null) {
			queryUniqueResult = new HashMap<String, Object>(initialSize);
		}else if(queryUniqueResult.size() > 0){
			queryUniqueResult.clear();
		}
	}
	/**
	 * <pre>
	 * 	根据resultSet设置查询唯一结果信息
	 * 	同时, 记录[标识]该StatementHandler已经执行过
	 * </pre>
	 * @throws SQLException 
	 */
	private void setQueryUniqueResult() throws SQLException {
		logger.debug("开始设置查询唯一结果集");
		if(setResutSetColumnNames()) {
			int columnCount = resutSetColumnNames.size();
			initialQueryUniqueResult(columnCount);
			
			for(int i=0;i<columnCount;i++) {
				queryUniqueResult.put(resutSetColumnNames.get(i), resultSet.getObject((i+1)));
			}
			
			if(resultSet.next()) {
				throw new NonUniqueDataException("进行唯一查询时, 查询出多条数据");
			}
		}else {
			initialQueryUniqueResult(16);
		}
		recordStatementHandlerIsExecuted();
	}
	
	private void initialQueryResultList_() {
		logger.debug("初始化 queryResultList_ list-数组集合");
		if(queryResultList_ == null) {
			queryResultList_ = new ArrayList<Object[]>(20);
		}else if(queryResultList_.size() > 0){
			queryResultList_.clear();
		}
	}
	/**
	 * <pre>
	 * 	根据resultSet设置查询结果集信息
	 * 	同时, 记录[标识]该StatementHandler已经执行过
	 * </pre>
	 * @throws SQLException 
	 */
	private void setQueryResultList_() throws SQLException {
		logger.debug("开始设置查询结果集集合");
		initialQueryResultList_();
		if(resultSet.next()) {
			int columnCount = resultSet.getMetaData().getColumnCount();
			Object[] array = null;
			do {
				array = new Object[columnCount];
				for(int i=0;i<columnCount;i++) {
					array[i] = resultSet.getObject((i+1)); 
				}
				queryResultList_.add(array);
			}while(resultSet.next());
		}
		recordStatementHandlerIsExecuted();
	}
	
	/**
	 * <pre>
	 * 	根据resultSet设置查询唯一结果信息
	 * 	同时, 记录[标识]该StatementHandler已经执行过
	 * </pre>
	 * @throws SQLException 
	 */
	private void setQueryUniqueResult_() throws SQLException {
		logger.debug("开始设置查询唯一结果集");
		if(resultSet.next()) {
			int columnCount = resultSet.getMetaData().getColumnCount();
			queryUniqueResult_ = new Object[columnCount];
			
			for(int i=0;i<columnCount;i++) {
				queryUniqueResult_[i] = resultSet.getObject((i+1)); 
			}
			if(resultSet.next()) {
				throw new NonUniqueDataException("进行唯一查询时, 查询出多条数据");
			}
		}else {
			queryUniqueResult_ = new Object[1];
		}
		recordStatementHandlerIsExecuted();
	}
	
	protected List<Map<String, Object>> executeQuery(ResultSet resultSet) throws SQLException {
		this.resultSet = resultSet;
		setQueryResultList();
		return getQueryResultList();
	}
	
	protected Map<String, Object> executeUniqueQuery(ResultSet resultSet) throws SQLException {
		this.resultSet = resultSet;
		setQueryUniqueResult();
		return getQueryUniqueResult();
	}
	
	protected List<Object[]> executeQuery_(ResultSet resultSet) throws SQLException {
		this.resultSet = resultSet;
		setQueryResultList_();
		return getQueryResultList_();
	}
	
	protected Object[] executeUniqueQuery_(ResultSet resultSet) throws SQLException {
		this.resultSet = resultSet;
		setQueryUniqueResult_();
		return getQueryUniqueResult_();
	}
	
	protected List<Map<String, Object>> getQueryResultList() {
		return queryResultList;
	}
	protected Map<String, Object> getQueryUniqueResult() {
		return queryUniqueResult;
	}
	protected List<Object[]> getQueryResultList_() {
		return queryResultList_;
	}
	public Object[] getQueryUniqueResult_() {
		return queryUniqueResult_;
	}
	
	@Override
	public boolean isClosed() {
		return isClosed;
	} 
	
	@Override
	public boolean isExecuted() {
		return isExecuted;
	}

	@Override
	public void close() {
		isClosed = true;
		if(resutSetColumnNames != null && resutSetColumnNames.size() > 0) {
			resutSetColumnNames.clear();
		}
		CloseUtil.closeDBConn(resultSet);
	}
}
