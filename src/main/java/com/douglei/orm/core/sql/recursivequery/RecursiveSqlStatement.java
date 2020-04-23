package com.douglei.orm.core.sql.recursivequery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.douglei.orm.core.dialect.db.sql.SqlHandler;
import com.douglei.orm.core.sql.pagequery.PageSqlStatement;

/**
 *递归查询用的sql语句对象
 * @author DougLei
 */
public class RecursiveSqlStatement extends PageSqlStatement {
	private String pkColumnName; // 存储主键的列名
	private String parentPkColumnName; // 存储父级主键的列名
	private String childNodeName; // 父级存储子集的节点名称
	private List<Object> parentValueList; // 父级主键值集合
	private boolean parentValueExistNull; // 父级主键值是否有null, 如果有, 则要增加条件 is null
	
	public RecursiveSqlStatement(SqlHandler sqlHandler, String originSql, String pkColumnName, String parentPkColumnName, String childNodeName, Object parentValue) {
		super(sqlHandler, originSql);
		this.pkColumnName = pkColumnName;
		this.parentPkColumnName = parentPkColumnName;
		this.childNodeName = childNodeName;
		setParentValueList(parentValue);
	}
	
	
	public String getPkColumnName() {
		return pkColumnName;
	}
	public String getParentPkColumnName() {
		return parentPkColumnName;
	}
	public String getChildNodeName() {
		return childNodeName;
	}
	
	
	/**
	 * 父级主键值是否有null
	 * @return
	 */
	public boolean parentValueExistNull() {
		return parentValueExistNull;
	}
	/**
	 * 获取父级主键值集合的长度
	 * @return
	 */
	public int parentValueListSize() {
		return parentValueList.size();
	}
	

	/**
	 * 设置父级主键值集合
	 * @param parentValue
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Object> setParentValueList(Object parentValue) {
		if(parentValue instanceof List) {
			List<Object> pvs = (List<Object>)parentValue;
			if(pvs.isEmpty()) {
				parentValue = null;
			}else {
				parentValueList = new ArrayList<Object>(pvs.size());
				for (Object pv : pvs) {
					addParentValue(pv);
				}
			}
		}else if(parentValue != null && parentValue.getClass().isArray()) {
			Object[] pvs = (Object[]) parentValue;
			if(pvs.length == 0) {
				parentValue = null;
			}else {
				parentValueList = new ArrayList<Object>(pvs.length);
				for (Object pv : pvs) {
					addParentValue(pv);
				}
			}
		}
		
		if(parentValueList == null) {
			parentValueList = new ArrayList<Object>();
			addParentValue(parentValue);
		}
		return parentValueList;
	}
	
	/**
	 * 添加父级主键值
	 * @param parentValue
	 */
	private void addParentValue(Object parentValue) {
		if(parentValue == null) {
			parentValueExistNull = true;
		}else {
			parentValueList.add(parentValue);
		}
	}
	
	/**
	 * 更新父级主键值集合
	 * @param parentList 父级数据集合 
	 */
	public void updateParentValueList(List<Map<String, Object>> parentList) {
		parentValueList.clear();
		parentValueExistNull = false;
		for (Map<String, Object> parent : parentList) {
			addParentValue(parent.get(pkColumnName));
		}
	}
	
	// 记录上一次父级主键值集合的长度
	private int lastParentValueListSize; 
	
	/**
	 * 将父级主键值集合追加到sql参数集合后
	 * @param parameters
	 * @return 返回参数parameters
	 */
	public List<Object> appendParameterValues(List<Object> parameters) {
		removeParentValueList(parameters);
		for (Object parentValue : parentValueList) {
			parameters.add(parentValue);
			lastParentValueListSize++;
		}
		return parameters;
	}
	
	/**
	 * 从sql参数集合中移除父级主键值的数据
	 * @param parameters
	 */
	public void removeParentValueList(List<Object> parameters) {
		if(lastParentValueListSize > 0) {
			int lastIndex = parameters.size()-1;
			while(lastParentValueListSize > 0) {
				parameters.remove(lastIndex--);
				lastParentValueListSize--;
			}
		}
	}
	
	/**
	 * 获取递归查询sql
	 * @return
	 */
	public String getRecursiveSql() {
		return sqlHandler.getRecursiveSql(this);
	}
	
	
	/**
	 * 递归查询的sql缓存
	 */
	private StringBuilder recursiveQuerySqlCache;
	private int recursiveQuerySqlEndIndex;
	public StringBuilder getRecursiveQuerySqlCache() {
		recursiveQuerySqlCache.setLength(recursiveQuerySqlEndIndex);
		return recursiveQuerySqlCache;
	}
	public void setRecursiveQuerySqlCache(StringBuilder recursiveQuerySql) {
		this.recursiveQuerySqlCache = recursiveQuerySql;
		this.recursiveQuerySqlEndIndex = recursiveQuerySql.length();
	}
}
