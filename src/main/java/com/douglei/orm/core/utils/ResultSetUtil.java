package com.douglei.orm.core.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.douglei.orm.core.sql.statement.entity.SqlResultsetMetadata;

/**
 * ResultSet工具类
 * <pre>
 * 	注意: 
 * 	 1.参数: resultSet 需要判断!= null, 同时必须执行过一次next(), 如果返回是false, 则不要调用该类中的工具方法
 * 	 2.参数: resultSet 需要手动关闭, 该类中的工具方法不对ResultSet进行close操作
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
		for(int i=1;i<=columnCount;i++) {
			resultsetMetadatas.add(new SqlResultsetMetadata(resultSetMetaData.getColumnName(i), resultSetMetaData.getColumnType(i), resultSetMetaData.getColumnTypeName(i)));
		}
		return resultsetMetadatas;
	}
	
	/**
	 * 获取ResultSet Map
	 * @param resultSet
	 * @return
	 * @throws SQLException 
	 */
	public static Map<String, Object> getResultSetMap(ResultSet resultSet) throws SQLException {
		return getResultSetMap(getSqlResultSetMetadata(resultSet), resultSet);
	}
	
	/**
	 * 获取ResultSet ListMap
	 * @param resultSet
	 * @return
	 * @throws SQLException 
	 */
	public static List<Map<String, Object>> getResultSetListMap(ResultSet resultSet) throws SQLException {
		return getResultSetListMap(getSqlResultSetMetadata(resultSet), resultSet);
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
			map.put(sqlResultsetMetadata.getColumnName(), sqlResultsetMetadata.getDataTypeHandler().getValue((short)(i+1), resultSet));
		}
		return map;
	}
	
	/**
	 * 获取ResultSet ListMap
	 * @param resultsetMetadatas
	 * @param resultSet
	 * @return
	 * @throws SQLException 
	 */
	public static List<Map<String, Object>> getResultSetListMap(List<SqlResultsetMetadata> resultsetMetadatas, ResultSet resultSet) throws SQLException {
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
	}
	
	
	/**
	 * 获取ResultSet Object[]
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public static Object[] getResultSetArray(ResultSet resultSet) throws SQLException {
		return getResultSetArray(getSqlResultSetMetadata(resultSet), resultSet);
	}
	
	/**
	 * 获取ResultSet Object[]
	 * @param resultsetMetadatas
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public static Object[] getResultSetArray(List<SqlResultsetMetadata> resultsetMetadatas, ResultSet resultSet) throws SQLException {
		int count = resultsetMetadatas.size();
		Object[] array = new Object[count];
		
		for(short i=0;i<count;i++) {
			array[i] = resultsetMetadatas.get(i).getDataTypeHandler().getValue((short)(i+1), resultSet); 
		}
		return array;
	}
	
	/**
	 * 获取ResultSet List Object[]
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public static List<Object[]> getResultSetListArray(ResultSet resultSet) throws SQLException {
		return getResultSetListArray(getSqlResultSetMetadata(resultSet), resultSet);
	}
	
	/**
	 * 获取ResultSet List Object[]
	 * @param resultsetMetadatas
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public static List<Object[]> getResultSetListArray(List<SqlResultsetMetadata> resultsetMetadatas, ResultSet resultSet) throws SQLException {
		List<Object[]> arrayList = new ArrayList<Object[]>(16);
		
		int count = resultsetMetadatas.size();
		Object[] array = null;
		SqlResultsetMetadata sqlResultsetMetadata = null;
		do {
			array = new Object[count];
			for(short i=0;i<count;i++) {
				sqlResultsetMetadata = resultsetMetadatas.get(i);
				array[i] = sqlResultsetMetadata.getDataTypeHandler().getValue((short)(i+1), resultSet); 
			}
			arrayList.add(array);
		}while(resultSet.next());
		
		return arrayList;
	}
}
