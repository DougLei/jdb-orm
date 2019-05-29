package com.douglei.database.metadata.table.column.extend;

import java.util.List;

import com.douglei.context.DBRunEnvironmentContext;
import com.douglei.database.dialect.datatype.handler.DataTypeHandler;
import com.douglei.database.dialect.datatype.handler.classtype.AbstractBlobDataTypeHandler;
import com.douglei.database.dialect.datatype.handler.classtype.AbstractClobDataTypeHandler;
import com.douglei.database.dialect.datatype.handler.classtype.AbstractStringDataTypeHandler;
import com.douglei.database.dialect.datatype.handler.classtype.ClassDataTypeHandler;
import com.douglei.database.metadata.table.ColumnMetadata;
import com.douglei.database.metadata.table.TableMetadata;

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
		validateDataType(dataType);
		this.constraintType = constraintType;
		
		this.tableName = tableName;
		this.columnName = columnName;
		
		setDefaultValue(dataType, defaultValue);
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
	public ColumnConstraint(ConstraintType constraintType, TableMetadata tableMetadata, List<ColumnMetadata> columns) {
		for (ColumnMetadata column : columns) {
			validateDataType(column.getDataType());
		}
		this.constraintType = constraintType;
		this.tableName = tableMetadata.getName();
		
		StringBuilder columnName = new StringBuilder(columns.size()*20);
		StringBuilder name = new StringBuilder(columns.size()*30);
		name.append(constraintType.getConstraintPrefix()).append("_").append(tableName).append("_");
		
		ColumnMetadata column = null;
		String cname = null;
		for(int i=0; i<columns.size(); i++) {
			column = columns.get(i);
			if(constraintType == ConstraintType.PRIMARY_KEY) {
				column.setPrimaryKeyAndNullabled(true, false);// 如果是主键约束, 则该列必须不能为空
				tableMetadata.addPrimaryKeyColumnMetadata(column, columns.size());
			}
			
			cname = column.getName();
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
