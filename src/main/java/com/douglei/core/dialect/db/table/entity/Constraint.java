package com.douglei.core.dialect.db.table.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.douglei.context.DBRunEnvironmentContext;
import com.douglei.core.dialect.datatype.handler.DataTypeHandler;
import com.douglei.core.dialect.datatype.handler.classtype.AbstractBlobDataTypeHandler;
import com.douglei.core.dialect.datatype.handler.classtype.AbstractClobDataTypeHandler;
import com.douglei.core.dialect.datatype.handler.classtype.AbstractStringDataTypeHandler;

/**
 * 列约束
 * @author DougLei
 */
public class Constraint {
	private String name;// (前缀+表名+列名)
	private Map<String, Column> columns;// 相关的列集合
	private String tableName;// 表名
	
	private ConstraintType constraintType;
	private String constraintColumnNames;// 约束的列名集合, 多个用,分割
	private String defaultValue;// 默认值
	
	public Constraint(ConstraintType constraintType, String tableName) {
		this.tableName = tableName;
		this.constraintType = constraintType;
	}
	
	/**
	 * 添加约束列
	 * @param column
	 */
	public Constraint addColumn(Column column) {
		if(column != null) {
			if(columns == null) {
				columns = new HashMap<String, Column>(4);
			}else if(columns.containsKey(column.getName())) {
				throw new ConstraintException("在同一个约束中, 出现重复的列["+column.getName()+"]");
			}
			columns.put(column.getName(), column);
		}
		return this;
	}
	
	private boolean unProcessConstraint=true;// 是否未处理约束
	private void processConstraint() {
		if(unProcessConstraint) {
			if(columns == null) {
				throw new NullPointerException("在约束中, 关联的列不能为空");
			}
			unProcessConstraint = false;
			
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
	}
	
	// 验证数据类型是否符合创建约束
	private void validateDataType(DataTypeHandler dataType) {
		if(dataType instanceof AbstractClobDataTypeHandler || dataType instanceof AbstractBlobDataTypeHandler) {
			throw new ConstraintException("不支持给clob类型或blob类型(即大数据字段类型)的字段配置约束");
		}
	}
	// 设置约束名
	private void setName(String name) {
		this.name = DBRunEnvironmentContext.getDialect().getDBObjectNameHandler().fixDBObjectName(name);
	}
	
	/**
	 * 设置默认值约束的默认值
	 * @param defaultValue
	 */
	public void setDefaultValue(String defaultValue) {
		if(this.defaultValue == null && defaultValue != null) {
			this.defaultValue = defaultValue;
		}
	}
	public ConstraintType getConstraintType() {
		return constraintType;
	}
	public String getName() {
		processConstraint();
		return name;
	}
	public Collection<Column> getColumns(){
		processConstraint();
		return columns.values();
	}
	public String getTableName() {
		processConstraint();
		return tableName;
	}
	public String getConstraintColumnNames() {
		processConstraint();
		return constraintColumnNames;
	}
	public String getDefaultValue() {
		processConstraint();
		return defaultValue;
	}
}
