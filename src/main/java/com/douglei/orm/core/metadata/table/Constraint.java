package com.douglei.orm.core.metadata.table;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.configuration.impl.element.environment.mapping.table.exception.ConstraintConfigurationException;
import com.douglei.orm.core.dialect.datatype.handler.DataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.classtype.AbstractBlobDataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.classtype.AbstractClobDataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeFeatures;
import com.douglei.tools.utils.StringUtil;

/**
 * 列约束
 * @author DougLei
 */
public class Constraint implements Serializable{
	private static final long serialVersionUID = -6924155959689840702L;

	private String name;// (前缀+表名+列名)
	
	private String tableName;// 表名
	private ConstraintType constraintType;
	
	private Map<String, ColumnMetadata> columns;// 相关的列集合
	
	private String constraintColumnNames;// 约束的列名集合, 多个用,分割
	
	private String defaultValue;// 默认值
	private String check;// 检查约束表达式
	private String fkTableName;// 外键约束关联的表名
	private String fkColumnName;// 外键约束关联的列名
	
	public Constraint(ConstraintType constraintType, String tableName) {
		this.constraintType = constraintType;
		this.tableName = tableName;
	}
	
	/**
	 * 添加约束列
	 * @param column
	 */
	public Constraint addColumn(ColumnMetadata column) {
		validateDataType(column.getDataTypeHandler());
		if(columns == null) {
			this.columns = new HashMap<String, ColumnMetadata>(constraintType.supportComposite()?4:2);
		}else if(columns.containsKey(column.getName())) {
			throw new ConstraintConfigurationException("在同一个["+this.constraintType.name()+"]约束中, 出现重复的列["+column.getName()+"]");
		}
		columns.put(column.getName(), column);
		processColumnMetadata(column);
		return this;
	}
	
	// 验证数据类型是否符合创建约束
	private void validateDataType(DataTypeHandler dataType) {
		if(dataType instanceof AbstractClobDataTypeHandler || dataType instanceof AbstractBlobDataTypeHandler) {
			throw new ConstraintConfigurationException("不支持给clob类型或blob类型(即大数据字段类型)的字段配置约束");
		}
	}
	
	// 处理列对象的元数据
	private void processColumnMetadata(ColumnMetadata column) {
		switch(constraintType) {
			case PRIMARY_KEY:
			case UNIQUE:
				break;
			case DEFAULT_VALUE:
				this.defaultValue = column.getDefaultValue();
				break;
			case CHECK:
				this.check = column.getCheck();
				break;
			case FOREIGN_KEY:
				this.fkTableName = column.getFkTableName();
				this.fkColumnName = column.getFkColumnName();
				break;
		}
	}
	
	private boolean processConstraint;// 是否处理约束
	private void processConstraint() {
		if(!processConstraint) {
			if(columns == null) {
				throw new NullPointerException("在表["+tableName+"], 名为["+constraintType.name()+"]约束中, 关联的列不能为空");
			}
			
			StringBuilder nameBuilder = new StringBuilder(columns.size()*40);
			nameBuilder.append(constraintType.getConstraintPrefix()).append("_").append(tableName).append("_");
			
			if(columns.size() > 1) {
				Collection<ColumnMetadata> cs = columns.values();
				int index = 0, lastIndex = cs.size()-1;
				
				StringBuilder constraintColumnNamesBuilder = new StringBuilder(cs.size()*20);
				for (ColumnMetadata column : cs) {
					constraintColumnNamesBuilder.append(column.getName());
					nameBuilder.append(column.getName());
					
					if(index < lastIndex) {
						constraintColumnNamesBuilder.append(",");
						nameBuilder.append("_");
						index++;
					}
				}
				this.constraintColumnNames = constraintColumnNamesBuilder.toString();
			}else {
				ColumnMetadata column = columns.values().iterator().next();
				this.constraintColumnNames = column.getName();
				nameBuilder.append(column.getName());
				
				switch(constraintType) {
					case DEFAULT_VALUE:
						if(this.defaultValue == null) {
							throw new NullPointerException("配置的默认值约束, 默认值不能为空");
						}
						if(((DBDataTypeFeatures)column.getDataTypeHandler()).isCharacterType()) {
							this.defaultValue = "'"+this.defaultValue+"'";
						}
						break;
					case CHECK:
						if(StringUtil.isEmpty(this.check)) {
							throw new NullPointerException("配置的检查约束, 检查约束表达式不能为空");
						}
						break;
					case FOREIGN_KEY:
						if(StringUtil.isEmpty(fkTableName)) {
							throw new NullPointerException("配置的外键约束, 关联的表名不能为空");
						}
						if(StringUtil.isEmpty(fkColumnName)) {
							throw new NullPointerException("配置的外键约束, 关联的列名不能为空");
						}
						nameBuilder.append("_").append(fkTableName).append("_").append(fkColumnName);
						break;
					default:
						// 不处理其他约束类型
						break;
				}
			}
			this.name = EnvironmentContext.getDialect().getDBObjectHandler().fixDBObjectName(nameBuilder.toString());// 设置约束名
			processConstraint = true;
		}
	}
	
	Collection<ColumnMetadata> getColumns(){
		processConstraint();
		return columns.values();
	}
	
	public ConstraintType getConstraintType() {
		return constraintType;
	}
	public String getTableName() {
		return tableName;
	}
	public String getName() {
		processConstraint();
		return name;
	}
	public String getConstraintColumnNames() {
		processConstraint();
		return constraintColumnNames;
	}
	
	public String getDefaultValue() {
		if(constraintType == ConstraintType.DEFAULT_VALUE) {
			processConstraint();
			return defaultValue;
		}
		throw new ConstraintConfigurationException("非默认值约束, 而是["+constraintType.name()+"]约束, 无法获取默认值");
	}
	public String getCheck() {
		if(constraintType == ConstraintType.CHECK) {
			processConstraint();
			return check;
		}
		throw new ConstraintConfigurationException("非检查约束, 而是["+constraintType.name()+"]约束, 无法获取检查约束表达式");
	}
	public String getFkTableName() {
		if(constraintType == ConstraintType.FOREIGN_KEY) {
			processConstraint();
			return fkTableName;
		}
		throw new ConstraintConfigurationException("非外键约束, 而是["+constraintType.name()+"]约束, 无法获取关联的外键表名");
	}
	public String getFkColumnName() {
		if(constraintType == ConstraintType.FOREIGN_KEY) {
			processConstraint();
			return fkColumnName;
		}
		throw new ConstraintConfigurationException("非外键约束, 而是["+constraintType.name()+"]约束, 无法获取关联的外键列名");
	}
}
