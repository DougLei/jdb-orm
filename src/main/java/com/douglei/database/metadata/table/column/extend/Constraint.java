package com.douglei.database.metadata.table.column.extend;

import java.util.List;

import com.douglei.context.DBRunEnvironmentContext;
import com.douglei.database.dialect.datatype.handler.DataTypeHandler;
import com.douglei.database.dialect.datatype.handler.classtype.AbstractBlobDataTypeHandler;
import com.douglei.database.dialect.datatype.handler.classtype.AbstractClobDataTypeHandler;
import com.douglei.database.dialect.datatype.handler.classtype.AbstractStringDataTypeHandler;
import com.douglei.database.dialect.datatype.handler.classtype.ClassDataTypeHandler;
import com.douglei.database.metadata.table.ColumnMetadata;

/**
 * 列约束
 * @author DougLei
 */
public class Constraint {
	
	private ConstraintType constraintType;
	
	private String tableName;// 表名
	private String columnName;// 列名
	private String defaultValue;// 默认值
	
	private String name;// 约束名(约束前缀+表名+列名)
	
	public Constraint(ConstraintType constraintType, String tableName, ColumnMetadata column) {
		validateDataType(column.getDataType());
		this.constraintType = constraintType;
		
		this.tableName = tableName;
		this.columnName = column.getName();
		
		setDefaultValue(column.getDataType(), column.getDefaultValue());
		setConstraintName(constraintType.getConstraintPrefix() + "_" + tableName + "_" + columnName);
	}
	private void setDefaultValue(ClassDataTypeHandler dataType, String defaultValue) {
		if(defaultValue != null) {
			if(dataType instanceof AbstractStringDataTypeHandler) {
				defaultValue = "'"+defaultValue+"'";
			}
			this.defaultValue = defaultValue;
		}
	}
	
	// 联合约束
	public Constraint(ConstraintType constraintType, String tableName, List<ColumnMetadata> columns) {
		for (ColumnMetadata column : columns) {
			validateDataType(column.getDataType());
		}
		this.constraintType = constraintType;
		this.tableName = tableName;
		
		StringBuilder columnName = new StringBuilder(columns.size()*20);
		StringBuilder name = new StringBuilder(columns.size()*30);
		name.append(constraintType.getConstraintPrefix()).append("_").append(tableName).append("_");
		
		String cname = null;
		for(int i=0; i<columns.size(); i++) {
			cname = columns.get(i).getName();
			columnName.append(cname);
			name.append(cname);
			
			if(i < columns.size()-1) {
				columnName.append(",");
				name.append("_");
			}
		}
		
		this.columnName = columnName.toString();
		setConstraintName(name.toString());
	}
	private void setConstraintName(String constraintName) {
		this.name = DBRunEnvironmentContext.getDialect().getDBObjectNameHandler().fixDBObjectName(constraintName);
	}
	private void validateDataType(DataTypeHandler dataType) {
		if(dataType instanceof AbstractClobDataTypeHandler || dataType instanceof AbstractBlobDataTypeHandler) {
			throw new UnsupportConstraintDataTypeException("不支持给clob类型或blob类型的字段配置任何约束");
		}
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
