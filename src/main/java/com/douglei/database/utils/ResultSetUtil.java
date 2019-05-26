package com.douglei.database.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.douglei.database.sql.statement.entity.SqlResultsetMetadata;
import com.douglei.utils.CloseUtil;

/**
 * ResultSet工具类
 * @author DougLei
 */
public class ResultSetUtil {
	
	/**
	 * 获取 SqlResultsetMetadata 实例集合
	 * <p>参数: resultSet 需要判断!= null, 同时必须执行过一次next(), 如果返回是false, 则不要调用该方法</p>
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public static List<SqlResultsetMetadata> getSqlResultSetMetadata(ResultSet resultSet) throws SQLException{
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		
		int columnCount = resultSetMetaData.getColumnCount();
		List<SqlResultsetMetadata> resultsetMetadatas = new ArrayList<SqlResultsetMetadata>(columnCount);
		for(int i=1;i<=columnCount;i++) {
			resultsetMetadatas.add(new SqlResultsetMetadata(resultSetMetaData.getColumnName(i), resultSetMetaData.getColumnType(i), resultSetMetaData.getColumnTypeName(i)));
		}
		return resultsetMetadatas;
	}
	
	/**
	 * 获取ResultSet Map
	 * <p>参数: resultSet 需要判断!= null, 同时必须执行过一次next(), 如果返回是false, 则不要调用该方法</p>
	 * @param resultSet
	 * @return
	 * @throws SQLException 
	 */
	public static Map<String, Object> getResultSetMap(ResultSet resultSet) throws SQLException {
		return getResultSetMap(getSqlResultSetMetadata(resultSet), resultSet);
	}
	
	/**
	 * 获取ResultSet ListMap
	 * <p>参数: resultSet 需要判断!= null, 同时必须执行过一次next(), 如果返回是false, 则不要调用该方法</p>
	 * @param resultSet
	 * @return
	 * @throws SQLException 
	 */
	public static List<Map<String, Object>> getResultSetListMap(ResultSet resultSet) throws SQLException {
		return getResultSetListMap(getSqlResultSetMetadata(resultSet), resultSet);
	}
	
	/**
	 * 获取ResultSet Map
	 * <p>参数: resultSet 需要判断!= null, 同时必须执行过一次next(), 如果返回是false, 则不要调用该方法</p>
	 * @param resultsetMetadatas
	 * @param resultSet
	 * @return
	 * @throws SQLException 
	 */
	public static Map<String, Object> getResultSetMap(List<SqlResultsetMetadata> resultsetMetadatas, ResultSet resultSet) throws SQLException {
		try {
			int count = resultsetMetadatas.size();
			Map<String, Object> map = new HashMap<String, Object>(count);
			
			SqlResultsetMetadata sqlResultsetMetadata = null;
			for(short i=0;i<count;i++) {
				sqlResultsetMetadata = resultsetMetadatas.get(i);
				map.put(sqlResultsetMetadata.getColumnName(), sqlResultsetMetadata.getDataTypeHandler().getValue((short)(i+1), resultSet));
			}
			return map;
		} catch(SQLException e){
			throw e;
		} finally {
			CloseUtil.closeDBConn(resultSet);
		}
	}
	
	/**
	 * 获取ResultSet ListMap
	 * <p>参数: resultSet 需要判断!= null, 同时必须执行过一次next(), 如果返回是false, 则不要调用该方法</p>
	 * @param resultsetMetadatas
	 * @param resultSet
	 * @return
	 * @throws SQLException 
	 */
	public static List<Map<String, Object>> getResultSetListMap(List<SqlResultsetMetadata> resultsetMetadatas, ResultSet resultSet) throws SQLException {
		try {
			List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>(16);
			
			int count = resultsetMetadatas.size();
			Map<String, Object> map = null;
			SqlResultsetMetadata sqlResultsetMetadata = null;
			
			do {
				map = new HashMap<String, Object>(count);
				for(short i=0;i<count;i++) {
					sqlResultsetMetadata = resultsetMetadatas.get(i);
					map.put(sqlResultsetMetadata.getColumnName(), sqlResultsetMetadata.getDataTypeHandler().getValue((short)(i+1), resultSet));
				}
				listMap.add(map);
			}while(resultSet.next());
			
			return listMap;
		} catch (SQLException e) {
			throw e;
		} finally {
			CloseUtil.closeDBConn(resultSet);
		}
	}
}
