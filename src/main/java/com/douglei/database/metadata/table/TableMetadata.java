package com.douglei.database.metadata.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.douglei.database.metadata.Metadata;
import com.douglei.utils.StringUtil;

/**
 * 表元数据
 * @author DougLei
 */
public class TableMetadata extends Metadata{
	
	/**
	 * 表名
	 */
	private String name;
	/**
	 * 映射的代码类名
	 * <pre>
	 * 	如果没有配置，默认用java.util.HashMap，HashMap的size为columns的size
	 * </pre>
	 */
	private String className;
	private static final String DEFAULT_CLASS_NAME = "java.util.HashMap";
	
	/**
	 * 包含的列元数据集合
	 * <pre>
	 * 	key=列元数据的code
	 * 	value=列元数据对象
	 * </pre>
	 */
	private Map<String, ColumnMetadata> columns;
	
	/**
	 * 主键列元数据集合
	 */
	private List<ColumnMetadata> primaryKeyColumns;
	
	public TableMetadata(String name, String className) {
		setName(name);
		setClassName(className);
	}
	
	public void addColumnMetadata(ColumnMetadata columnMetadata) {
		if(columns == null) {
			columns = new HashMap<String, ColumnMetadata>();
		}
		columns.put(columnMetadata.getName(), columnMetadata);
	}
	
	public void addPrimaryKeyColumnMetadata(ColumnMetadata columnMetadata) {
		if(primaryKeyColumns == null) {
			primaryKeyColumns = new ArrayList<ColumnMetadata>(2);
		}
		primaryKeyColumns.add(columnMetadata);
	}
	
	/**
	 * <pre>
	 * 	如果指定了className, 则返回className
	 * 	否则返回name, 即表名
	 * </pre>
	 * @return
	 */
	public String getCode() {
		if(className == DEFAULT_CLASS_NAME) {
			return name;
		}
		return className;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	private void setClassName(String className) {
		if(StringUtil.isEmpty(className)) {
			className = DEFAULT_CLASS_NAME;
		}
		this.className = className;
	}
	public Map<String, ColumnMetadata> getColumns() {
		return columns;
	}
}
