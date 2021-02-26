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
	private List<String> columnNames; // 列名集合
	
	private String value;// 默认值或检查约束表达式
	private String fkTableName;// 外键约束关联的表名
	private String fkColumnName;// 外键约束关联的列名
	
	public ConstraintMetadata(String name, ConstraintType type, List<String> columnNames) {
		this.name = name;
		this.type = type;
		this.columnNames = columnNames;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setFkValue(String fkTableName, String fkColumnName) {
		this.fkTableName = fkTableName;
		this.fkColumnName = fkColumnName;
	}

	@Override
	public boolean equals(Object obj) {
		return name.equals(((ConstraintMetadata)obj).name);
	}
	
	public String getName() {
		return name;
	}
	public ConstraintType getType() {
		return type;
	}
	public List<String> getColumnNames() {
		return columnNames;
	}
	public String getValue() {
		return value;
	}
	public String getFkTableName() {
		return fkTableName;
	}
	public String getFkColumnName() {
		return fkColumnName;
	}
}
