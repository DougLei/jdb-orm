package com.douglei.orm.sql.statement.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.douglei.orm.sql.statement.entity.SqlResultsetMetadata;

/**
 * ResultSet工具类
 * <pre>
 * 	注意: 
 * 	 1.参数: resultSet 需要判断!= null, 同时必须执行过一次next(), 如果返回是false, 则不要调用该类中的任何方法
 * 	 2.参数: resultSet 需要手动关闭, 该类中的所有方法不对ResultSet参数进行close操作
 * </pre>
 * @author DougLei
 */
public class ResultSetUtil {
	
	/**
	 * 获取 SqlResultsetMetadata 实例集合
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public static List<SqlResultsetMetadata> getSqlResultSetMetadata(ResultSet resultSet) throws SQLException{
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		
		int columnCount = resultSetMetaData.getColumnCount();
		List<SqlResultsetMetadata> resultsetMetadatas = new ArrayList<SqlResultsetMetadata>(columnCount);
		for(int i=1;i<=columnCount;i++) 
			resultsetMetadatas.add(new SqlResultsetMetadata(resultSetMetaData.getColumnName(i).toUpperCase(), resultSetMetaData.getColumnType(i), resultSetMetaData.getColumnTypeName(i)));
		return resultsetMetadatas;
	}
	
	/**
	 * 获取ResultSet Map
	 * @param resultsetMetadatas
	 * @param resultSet
	 * @return
	 * @throws SQLException 
	 */
	public static Map<String, Object> getResultSetMap(List<SqlResultsetMetadata> resultsetMetadatas, ResultSet resultSet) throws SQLException {
		int count = resultsetMetadatas.size();
		Map<String, Object> map = new HashMap<String, Object>(count);
		
		SqlResultsetMetadata sqlResultsetMetadata = null;
		for(short i=0;i<count;i++) {
			sqlResultsetMetadata = resultsetMetadatas.get(i);
			map.put(sqlResultsetMetadata.getColumnName(), sqlResultsetMetadata.getDBDataType().getValue(i+1, resultSet));
		}
		return map;
	}
	
	/**
	 * 获取ResultSet ListMap
	 * @param startRow 起始的行数, 值从1开始
	 * @param length 长度, 小于1表示不限制长度, 大于等于1表示要查询的数据量
	 * @param resultsetMetadatas
	 * @param resultSet
	 * @return
	 * @throws SQLException 
	 */
	public static List<Map<String, Object>> getResultSetListMap(int startRow, int length, List<SqlResultsetMetadata> resultsetMetadatas, ResultSet resultSet) throws SQLException {
		if(!move2StartRow(startRow, resultSet))
			return Collections.emptyList();
		
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>(length<1?10:length);
		Map<String, Object> map = null;
		SqlResultsetMetadata sqlResultsetMetadata = null;
		do {
			map = new HashMap<String, Object>();
			for(int i=0;i<resultsetMetadatas.size();i++) {
				sqlResultsetMetadata = resultsetMetadatas.get(i);
				map.put(sqlResultsetMetadata.getColumnName(), sqlResultsetMetadata.getDBDataType().getValue(i+1, resultSet));
			}
			listMap.add(map);
			length--;
		}while(resultSet.next() && length != 0);
		return listMap;
	}
	
	
	/**
	 * 获取ResultSet Object[]
	 * @param resultsetMetadatas
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public static Object[] getResultSetArray(List<SqlResultsetMetadata> resultsetMetadatas, ResultSet resultSet) throws SQLException {
		Object[] array = new Object[resultsetMetadatas.size()];
		for(short i=0;i<resultsetMetadatas.size();i++) 
			array[i] = resultsetMetadatas.get(i).getDBDataType().getValue((i+1), resultSet); 
		return array;
	}
	
	
	/**
	 * 获取ResultSet List Object[]
	 * @param startRow 起始的行数, 值从1开始, 不能传入小于1的数字
	 * @param length 长度, 小于1表示不限制长度, 大于等于1表示要查询的数据量
	 * @param resultsetMetadatas
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public static List<Object[]> getResultSetListArray(int startRow, int length, List<SqlResultsetMetadata> resultsetMetadatas, ResultSet resultSet) throws SQLException {
		if(!move2StartRow(startRow, resultSet))
			return Collections.emptyList();
		
		List<Object[]> arrayList = new ArrayList<Object[]>(length<1?10:length);
		Object[] array = null;
		SqlResultsetMetadata sqlResultsetMetadata = null;
		do {
			array = new Object[resultsetMetadatas.size()];
			for(short i=0;i<resultsetMetadatas.size();i++) {
				sqlResultsetMetadata = resultsetMetadatas.get(i);
				array[i] = sqlResultsetMetadata.getDBDataType().getValue(i+1, resultSet); 
			}
			arrayList.add(array);
			length--;
		}while(resultSet.next() && length != 0);
		return arrayList;
	}
	
	/**
	 * 移动到起始行
	 * @param startRow
	 * @param resultSet
	 * @return 是否存在数据
	 * @throws SQLException
	 */
	private static boolean move2StartRow(int startRow, ResultSet resultSet) throws SQLException{
		if(startRow < 1)
			throw new SQLException("查询的起始行数不能小于1");
		if(startRow == 1)
			return true;
		
		startRow--;
		while(resultSet.next()) {
			startRow--;
			if(startRow == 0)
				return true;
		}
		return false;
	}
	
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	 * 直接从ResultSet中获取ListMap
	 * @param resultSet
	 * @return
	 * @throws SQLException 
	 */
	public static List<Map<String, Object>> getListMap(ResultSet resultSet) throws SQLException {
		if(resultSet == null)
			return Collections.emptyList();
		
		try {
			if(resultSet.next()) 
				return getResultSetListMap(1, -1, getSqlResultSetMetadata(resultSet), resultSet);
			return Collections.emptyList();
		} finally {
			resultSet.close();
		}
	}
}
