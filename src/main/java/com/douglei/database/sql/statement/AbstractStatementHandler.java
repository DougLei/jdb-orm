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

import com.douglei.database.sql.statement.impl.Parameter;
import com.douglei.utils.CloseUtil;

/**
 * StatementHander抽象父类
 * @author DougLei
 */
public abstract class AbstractStatementHandler implements StatementHandler{
	private static final Logger logger = LoggerFactory.getLogger(AbstractStatementHandler.class);
	
	private boolean isExecuted;// 是否已经执行过
	private boolean isClosed;
	
	private ResultSet resultSet;
	private List<String> resutSetColumnNames;// 结果集的列名
	private List<Map<String, Object>> queryResultList;
	
	/**
	 * 记录[标识]该StatementHandler已经执行过
	 */
	private void recordStatementHandlerIsExecuted() {
		this.isExecuted = true;
	}
	
	/**
	 * 将List<Object>转换为List<Parameter>集合
	 * @param parameters
	 * @return
	 */
	protected List<Parameter> turnToParameters(List<Object> parameters){
		List<Parameter> actualParameters = new ArrayList<Parameter>(parameters.size());
		for (Object object : parameters) {
			if(object instanceof Parameter) {
				actualParameters.add((Parameter)object);
			}else {
				actualParameters.add(new Parameter(object));
			}
		}
		return actualParameters;
	}
	
	/**
	 * 设置查询结果集的列名集合
	 * @return 返回resultSet是否包含结果集
	 * @throws SQLException 
	 */
	private boolean setResutSetColumnNames() throws SQLException {
		logger.debug("开始获取查询结果集的列名信息");
		if(resultSet != null && resultSet.next()) {
			if(resutSetColumnNames == null) {
				ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
				int columnCount = resultSetMetaData.getColumnCount();
				resutSetColumnNames = new ArrayList<String>(columnCount);
				for(int i=1;i<=columnCount;i++) {
					resutSetColumnNames.add(resultSetMetaData.getColumnName(i));
				}
			}
			if(logger.isDebugEnabled()) {
				logger.debug("查询结果集列名集合为: {}", resutSetColumnNames.toString());
			}
			return true;
		}
		logger.debug("查询结果集中没有数据, 获取结果集列名信息失败");
		return false;
	}
	
	private void initialQueryResultList() {
		logger.debug("初始化 queryResultList 集合");
		if(queryResultList == null) {
			queryResultList = new ArrayList<Map<String,Object>>(16);
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
	
	/**
	 * 执行查询
	 * @param resultSet
	 * @throws SQLException
	 */
	protected List<Map<String, Object>> executeQuery(ResultSet resultSet) throws SQLException {
		this.resultSet = resultSet;
		setQueryResultList();
		return getQueryResultList();
	}
	
	/**
	 * 获取查询结果集
	 * @return
	 */
	protected List<Map<String, Object>> getQueryResultList() {
		return queryResultList;
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
