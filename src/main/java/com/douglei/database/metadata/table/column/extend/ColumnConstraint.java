package com.douglei.database.metadata.table.column.extend;

import java.util.List;

import com.douglei.database.dialect.datatype.handler.classtype.ClassDataTypeHandler;
import com.douglei.database.metadata.table.ColumnMetadata;

/**
 * 列约束
 * @author DougLei
 */
public class ColumnConstraint {
	
	private ConstraintType constraintType;
	
	private String tableName;// 表名
	private String columnName;// 列名
	private String defaultValue;// 默认值
	
	private String name;// 约束名(约束前缀+表名+列名)
	
	public ColumnConstraint(ClassDataTypeHandler dataType, ConstraintType constraintType, String tableName, String columnName) {
		this(dataType, constraintType, tableName, columnName, null);
	}
	public ColumnConstraint(ClassDataTypeHandler dataType, ConstraintType constraintType, String tableName, String columnName, String defaultValue) {
		this.constraintType = constraintType;
		
		this.tableName = tableName;
		this.columnName = columnName;
		this.defaultValue = defaultValue;
		
		this.name = constraintType.getConstraintPrefix() + "_" + tableName + "_" + columnName;
	}

	public ColumnConstraint(ConstraintType constraintType, String tableName, List<ColumnMetadata> columns) {
		this.constraintType = constraintType;
		this.tableName = tableName;
		
		int length = columns.size();
		
		StringBuilder columnName = new StringBuilder(length*20);
		StringBuilder name = new StringBuilder(length*30);
		name.append(constraintType.getConstraintPrefix()).append("_").append(tableName).append("_");
		
		String cname = null;
		for(int i=0; i<length; i++) {
			cname = columns.get(i).getColumnProperty().getName();
			columnName.append(cname);
			name.append(cname);
			
			if(i < length-1) {
				columnName.append(",");
				name.append("_");
			}
		}
		
		this.columnName = columnName.toString();
		this.name = name.toString();
	}
	
	public ConstraintType getConstraintType() {
		return constraintType;
	}
	public String getTableName() {
		return tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public String getName() {
		return name;
	}
}
