package com.douglei.database.dialect.db.table.entity;

/**
 * 列约束
 * @author DougLei
 */
public class Constraint extends DBObject{
	private ConstraintType constraintType;
	private String ConstraintColumnNames;// 约束的列名集合, 多个用,分割
	private String defaultValue;// 默认值
	
	public Constraint(ConstraintType constraintType, String tableName) {
		super(tableName);
		this.constraintType = constraintType;
	}
	public Constraint(ConstraintType constraintType, String tableName, Column column) {
		super(tableName, column);
		this.constraintType = constraintType;
	}
	
	@Override
	protected void processDBObject() {
//		private void setDefaultValue(ConstraintType constraintType, String tableName, String defaultValue) {
//		
//		
//		if(defaultValue != null) {
//			if(dataType instanceof AbstractStringDataTypeHandler) {
//				defaultValue = "'"+defaultValue+"'";
//			}
//			this.defaultValue = defaultValue;
//		}
//	}
	
	// 联合约束
//	public Constraint(ConstraintType constraintType, String tableName, List<ColumnMetadata> columns) {
//		for (ColumnMetadata column : columns) {
//			validateDataType(column.getDataTypeHandler());
//		}
//		this.constraintType = constraintType;
//		this.tableName = tableName;
//		
//		StringBuilder columnName = new StringBuilder(columns.size()*20);
//		StringBuilder name = new StringBuilder(columns.size()*30);
//		name.append(constraintType.getConstraintPrefix()).append("_").append(tableName).append("_");
//		
//		String cname = null;
//		for(int i=0; i<columns.size(); i++) {
//			cname = columns.get(i).getName();
//			columnName.append(cname);
//			name.append(cname);
//			
//			if(i < columns.size()-1) {
//				columnName.append(",");
//				name.append("_");
//			}
//		}
//		
//		this.columnName = columnName.toString();
//		setConstraintName(name.toString());
//	}
//	private void setConstraintName(String constraintName) {
//		this.name = DBRunEnvironmentContext.getDialect().getDBObjectNameHandler().fixDBObjectName(constraintName);
//	}
//	private void validateDataType(DataTypeHandler dataType) {
//		if(dataType instanceof AbstractClobDataTypeHandler || dataType instanceof AbstractBlobDataTypeHandler) {
//			throw new UnsupportConstraintDataTypeException("不支持给clob类型或blob类型的字段配置任何约束");
//		}
//	}

	
//	validateDataType(column.getDataTypeHandler());
//	this.constraintType = constraintType;
//	
//	this.tableName = tableName;
//	this.columnName = column.getName();
//	
//	setDefaultValue(column.getDataTypeHandler(), column.getDefaultValue());
//	setConstraintName(constraintType.getConstraintPrefix() + "_" + tableName + "_" + columnName);
	}
	
	@Override
	protected String getObjectName() {
		return "约束";
	}
	public ConstraintType getConstraintType() {
		return constraintType;
	}
	public String getConstraintColumnNames() {
		process();
		return ConstraintColumnNames;
	}
	public String getDefaultValue() {
		process();
		return defaultValue;
	}
}
