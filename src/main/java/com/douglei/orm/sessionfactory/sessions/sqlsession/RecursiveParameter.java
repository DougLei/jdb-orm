package com.douglei.orm.sessionfactory.sessions.sqlsession;

/**
 * 
 * @author DougLei
 */
public class RecursiveParameter {
	private int deep = -1; // 递归深度; -1标识为最大深度
	private String column = "ID"; // 存储主键的列名
	private String parentColumn = "PARENT_ID"; // 存储父主键的列名
	private String children = "children"; // 父实例中存储子实例集合用的name
	private Object value; // 初始条件值; 可为null, Array(数组), Collection(集合)

	
	/**
	 * 设置递归深度; -1标识为最大深度
	 * @param deep 
	 * @return
	 */
	public RecursiveParameter setDeep(int deep) {
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
	public RecursiveParameter setColumn(String column) {
		this.column = column;
		return this;
	}

	/**
	 * 设置存储父主键的列名
	 * @param parentColumn
	 * @return
	 */
	public RecursiveParameter setParentColumn(String parentColumn) {
		this.parentColumn = parentColumn;
		return this;
	}

	/**
	 * 设置父实例中存储子实例集合用的name
	 * @param children
	 * @return
	 */
	public RecursiveParameter setChildren(String children) {
		this.children = children;
		return this;
	}

	/**
	 * 设置初始条件值
	 * @param value 可为null, Array(数组), Collection(集合)
	 * @return
	 */
	public RecursiveParameter setValue(Object value) {
		this.value = value;
		return this;
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
	 * 获取初始条件值; 可为null, Array(数组), Collection(集合)
	 * @return
	 */
	public Object getValue() {
		return value;
	}
}
