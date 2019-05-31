package com.douglei.core.dialect.db.table.entity;

import java.util.Collection;

import com.douglei.core.dialect.datatype.handler.DataTypeHandler;
import com.douglei.core.dialect.datatype.handler.classtype.AbstractBlobDataTypeHandler;
import com.douglei.core.dialect.datatype.handler.classtype.AbstractClobDataTypeHandler;
import com.douglei.core.dialect.datatype.handler.classtype.AbstractStringDataTypeHandler;

/**
 * 列约束
 * @author DougLei
 */
public class Constraint extends DB_CI_Object{
	private ConstraintType constraintType;
	private String constraintColumnNames;// 约束的列名集合, 多个用,分割
	private String defaultValue;// 默认值
	
	public Constraint(ConstraintType constraintType, String tableName) {
		super(tableName);
		this.constraintType = constraintType;
	}
	
	@Override
	protected void processDBObject() {
		Collection<Column> cs = columns.values();
		
		StringBuilder nameBuilder = new StringBuilder(cs.size()*40);
		nameBuilder.append(constraintType.getConstraintPrefix()).append("_").append(tableName).append("_");
		
		if(constraintType == ConstraintType.DEFAULT_VALUE) {
			if(cs.size() > 1) {
				throw new ConstraintException("不支持给多个列添加联合默认值约束 [设置默认值约束时列的数量多于1个]");
			}
			Column column = cs.iterator().next();
			validateDataType(column.getDataTypeHandler());

			setDefaultValue(column.getDefaultValue());
			if(this.defaultValue == null) {
				throw new ConstraintException("添加默认值约束时, 默认值不能为空");
			}
			
			this.constraintColumnNames = column.getName();
			setName(nameBuilder.append(column.getName()).toString());
			
			if(column.getDataTypeHandler() instanceof AbstractStringDataTypeHandler) {
				this.defaultValue = "'"+this.defaultValue+"'";
			}
		}else {
			int index = 0, lastIndex = cs.size()-1;
			
			StringBuilder constraintColumnNamesBuilder = new StringBuilder(cs.size()*20);
			
			String cname = null;
			for (Column column : cs) {
				validateDataType(column.getDataTypeHandler());
				
				cname = column.getName();
				constraintColumnNamesBuilder.append(cname);
				nameBuilder.append(cname);
				
				if(index < lastIndex) {
					constraintColumnNamesBuilder.append(",");
					nameBuilder.append("_");
					index++;
				}
			}
			this.constraintColumnNames = constraintColumnNamesBuilder.toString();
			setName(nameBuilder.toString());
		}
	}
	
	private void validateDataType(DataTypeHandler dataType) {
		if(dataType instanceof AbstractClobDataTypeHandler || dataType instanceof AbstractBlobDataTypeHandler) {
			throw new ConstraintException("不支持给clob类型或blob类型的字段配置约束");
		}
	}
	
	@Override
	protected String getObjectName() {
		return "约束";
	}
	public void setDefaultValue(String defaultValue) {
		if(this.defaultValue == null && defaultValue != null) {
			this.defaultValue = defaultValue;
		}
	}
	public ConstraintType getConstraintType() {
		return constraintType;
	}
	public String getConstraintColumnNames() {
		process();
		return constraintColumnNames;
	}
	public String getDefaultValue() {
		process();
		return defaultValue;
	}
	@Override
	public DBObjectType getDBObjectType() {
		return DBObjectType.CONSTRAINT;
	}
}
