package com.douglei.orm.mapping.impl.table.metadata;

import java.util.List;

import com.douglei.orm.mapping.metadata.Metadata;

/**
 * 
 * @author DougLei
 */
public class ConstraintMetadata implements Metadata{
	private String name; // 约束名
	private ConstraintType type; // 约束类型
	private List<String> columnNameList; // 约束的列名集合
	
	private String sequence; // oracle中自增主键的序列名
	private String defaultValue;// 默认值约束的值
	private String check; // 检查约束的值
	private String table;// 外键约束关联的表名
	private String column;// 外键约束关联的列名
	
	public ConstraintMetadata(String name, ConstraintType type, List<String> columnNames) {
		this.name = name;
		this.type = type;
		this.columnNameList = columnNames;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public void setCheck(String check) {
		this.check = check;
	}
	public void setTableAndColumn(String table, String column) {
		this.table = table;
		this.column = column;
	}

	@Override
	public final boolean equals(Object obj) {
		return name.equals(((ConstraintMetadata)obj).name);
	}
	
	public String getName() {
		return name;
	}
	public ConstraintType getType() {
		return type;
	}
	public List<String> getColumnNameList() {
		return columnNameList;
	}
	public String getSequence() {
		return sequence;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public String getCheck() {
		return check;
	}
	public String getTable() {
		return table;
	}
	public String getColumn() {
		return column;
	}
}
