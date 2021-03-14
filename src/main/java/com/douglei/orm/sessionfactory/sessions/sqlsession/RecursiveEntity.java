package com.douglei.orm.sessionfactory.sessions.sqlsession;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/**
 * 
 * @author DougLei
 */
public class RecursiveEntity {
	private int deep = -1; // 递归深度; -1标识为最大深度
	private String column = "ID"; // 存储主键的列名
	private String parentColumn = "PARENT_ID"; // 存储父主键的列名
	private String children = "children"; // 父实例中存储子实例集合用的name
	private Object[] values; // 初始条件值数组
	
	/**
	 * 设置递归深度; -1标识为最大深度
	 * @param deep 
	 * @return
	 */
	public RecursiveEntity setDeep(int deep) {
		if(deep < -1)
			deep = -1;
		this.deep = deep;
		return this;
	}
	
	/**
	 * 设置存储主键的列名
	 * @param column
	 * @return
	 */
	public RecursiveEntity setColumn(String column) {
		this.column = column.toUpperCase();
		return this;
	}

	/**
	 * 设置存储父主键的列名
	 * @param parentColumn
	 * @return
	 */
	public RecursiveEntity setParentColumn(String parentColumn) {
		this.parentColumn = parentColumn.toUpperCase();
		return this;
	}

	/**
	 * 设置父实例中存储子实例集合用的name
	 * @param children
	 * @return
	 */
	public RecursiveEntity setChildren(String children) {
		this.children = children;
		return this;
	}

	/**
	 * 设置初始条件值
	 * @param value 可为null, Array(数组), Collection(集合)
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public RecursiveEntity setValue(Object value) {
		if(this.values != null)
			this.values = null;
		
		if(value == null)
			return this;
		
		if(value instanceof Collection && !((Collection)value).isEmpty()) {
			this.values = ((Collection)value).toArray();
		}else if(value.getClass().isArray() && ((Object[]) value).length > 0) {
			this.values = (Object[]) value;
		}
		
		if(this.values != null) {
			if(this.values.length > 1)
				Arrays.sort(this.values, new Comparator<Object>() {
					@Override
					public int compare(Object o1, Object o2) {
						if(o1 == null && o2 != null)
							return 1;
						if(o1 != null && o2 == null)
							return -1;
						return 0;
					}
				});
			if(this.values[0] == null)
				this.values = null;
		}
		return this;
	}
	
	/**
	 * 是否继续(递归)
	 * @return
	 */
	boolean isContinue() {
		return deep==-1 || deep-->0;
	}
	
	/**
	 * 获取递归深度; -1标识为最大深度
	 * @return
	 */
	public int getDeep() {
		return deep;
	}
	/**
	 * 获取存储主键的列名
	 * @return
	 */
	public String getColumn() {
		return column;
	}
	/**
	 * 获取存储父主键的列名
	 * @return
	 */
	public String getParentColumn() {
		return parentColumn;
	}
	/**
	 * 获取父实例中存储子实例集合用的name
	 * @return
	 */
	public String getChildren() {
		return children;
	}
	/**
	 * 获取初始条件值数组
	 * @return
	 */
	public Object[] getValues() {
		return values;
	}
}
