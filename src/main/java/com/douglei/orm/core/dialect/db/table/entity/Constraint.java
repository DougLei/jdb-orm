package com.douglei.orm.core.dialect.db.table.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.douglei.orm.configuration.environment.mapping.table.UnsupportConstraintConfigurationException;
import com.douglei.orm.context.DBRunEnvironmentContext;
import com.douglei.orm.core.dialect.datatype.handler.DataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.classtype.AbstractBlobDataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.classtype.AbstractClobDataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeFeatures;
import com.douglei.tools.utils.StringUtil;

/**
 * 列约束
 * @author DougLei
 */
public class Constraint {
	private String name;// (前缀+表名+列名)
	private Column column;// 记录第一个add的列对象
	private Map<String, Column> columns;// 相关的列集合
	private String tableName;// 表名
	
	private ConstraintType constraintType;
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
	public Constraint addColumn(Column column) {
		if(columns == null) {
			columns = new HashMap<String, Column>(constraintType.supportMultipleColumn()?4:1);
			this.column = column;
		}else if(columns.containsKey(column.getName())) {
			throw new ConstraintException("在同一个["+this.constraintType.name()+"]约束中, 出现重复的列["+column.getName()+"]");
		}
		columns.put(column.getName(), column);
		processColumnMetadata(column);
		return this;
	}
	
	// 处理列对象的元数据
	private void processColumnMetadata(Column column) {
		switch(constraintType) {
			case PRIMARY_KEY:
				// 将列改为主键列
				column.processPrimaryKeyAndNullabledAndUnique(true, false, false);
				break;
			case UNIQUE:
				// 判断列是否是主键, 如果不是, 则给列加上唯一约束
				if(column.isPrimaryKey()) {
					throw new UnsupportConstraintConfigurationException("列["+column.getName()+"]已经为主键列, 不能配置唯一约束");
				}
				column.unique = true;
				break;
			case DEFAULT_VALUE:
				if(columns.size() > 1) {
					throw new ConstraintException("不支持给多个列添加联合默认值约束 , 即设置默认值约束时列的数量只能有一个");
				}
				this.defaultValue = column.getDefaultValue();// 将列中的默认值(不论是否为空)给this.defaultValue属性, 后续也可以再通过setDefaultValue方法设置默认值
				break;
			case CHECK:
				if(columns.size() > 1) {
					throw new ConstraintException("不支持给多个列添加联合检查约束 , 即设置检查约束时列的数量只能有一个");
				}
				this.check = column.getCheck();// 同默认值
				break;
			case FOREIGN_KEY:
				if(columns.size() > 1) {
					throw new ConstraintException("不支持给多个列添加联合外键约束 , 即设置外键约束时列的数量只能有一个");
				}
				this.fkTableName = column.getFkTableName();// 同默认值
				this.fkColumnName = column.getFkColumnName();
				break;
		}
	}
	
	private boolean unProcessConstraint=true;// 是否未处理约束
	private void processConstraint() {
		if(unProcessConstraint) {
			if(columns == null) {
				throw new NullPointerException("在表["+tableName+"]的["+constraintType.name()+"]约束中, 关联的列不能为空");
			}
			unProcessConstraint = false;
			
			Collection<Column> cs = columns.values();
			StringBuilder nameBuilder = new StringBuilder(cs.size()*40);
			nameBuilder.append(constraintType.getConstraintPrefix()).append("_").append(tableName).append("_");
			
			if(constraintType.supportMultipleColumn()) {
				int index = 0, lastIndex = cs.size()-1;
				
				StringBuilder constraintColumnNamesBuilder = new StringBuilder(cs.size()*20);
				for (Column column : cs) {
					validateDataType(column.getDataTypeHandler());
					
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
				validateDataType(this.column.getDataTypeHandler());
				switch(constraintType) {
					case DEFAULT_VALUE:
						if(this.defaultValue == null) {
							throw new NullPointerException("配置的默认值约束, 默认值不能为空");
						}
						if(((DBDataTypeFeatures)this.column.getDataTypeHandler()).isCharacterType()) {
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
						break;
					default:
						// 不处理其他约束类型
						break;
				}
				this.constraintColumnNames = this.column.getName();
				nameBuilder.append(this.column.getName());
			}
			setName(nameBuilder.toString());
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
	public Collection<Column> getColumns(){
		processConstraint();
		return columns.values();
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
		throw new UnsupportConstraintConfigurationException("非默认值约束, 而是["+constraintType.name()+"]约束, 无法获取默认值");
	}
	public String getCheck() {
		if(constraintType == ConstraintType.CHECK) {
			return check;
		}
		throw new UnsupportConstraintConfigurationException("非检查约束, 而是["+constraintType.name()+"]约束, 无法获取检查约束表达式");
	}
	public String getFkTableName() {
		if(constraintType == ConstraintType.FOREIGN_KEY) {
			return fkTableName;
		}
		throw new UnsupportConstraintConfigurationException("非外键约束, 而是["+constraintType.name()+"]约束, 无法获取关联的外键表名");
	}
	public String getFkColumnName() {
		if(constraintType == ConstraintType.FOREIGN_KEY) {
			return fkColumnName;
		}
		throw new UnsupportConstraintConfigurationException("非外键约束, 而是["+constraintType.name()+"]约束, 无法获取关联的外键列名");
	}
	
	public void setDefaultValue(String defaultValue) {
		if(constraintType == ConstraintType.DEFAULT_VALUE) {
			if(this.column == null) {
				throw new NullPointerException("创建默认值约束, set默认值前, 请先添加列对象(addColumn(...))");
			}
			if(defaultValue == null) {
				throw new NullPointerException("配置的默认值约束, 默认值不能为空");
			}
			if(this.column.isPrimaryKey()) {
				throw new UnsupportConstraintConfigurationException("列["+this.column.getName()+"]已经为主键列, 不能配置默认值");
			}
			this.defaultValue = defaultValue;
			this.column.defaultValue = defaultValue;
		}
		throw new UnsupportConstraintConfigurationException("非默认值约束, 而是["+constraintType.name()+"]约束, 无法设置默认值");
	}
	
	public void setCheck(String check) {
		if(constraintType == ConstraintType.CHECK) {
			if(this.column == null) {
				throw new NullPointerException("创建检查约束, set检查约束表达式前, 请先添加列对象(addColumn(...))");
			}
			if(StringUtil.isEmpty(check)) {
				throw new NullPointerException("配置的检查约束, 检查约束表达式不能为空");
			}
			this.check = check;
			this.column.check = check;
		}
		throw new UnsupportConstraintConfigurationException("非检查约束, 而是["+constraintType.name()+"]约束, 无法设置检查约束表达式");
	}
	
	public void setForeignKey(String fkTableName, String fkColumnName) {
		if(constraintType == ConstraintType.FOREIGN_KEY) {
			if(this.column == null) {
				throw new NullPointerException("创建外键约束, set关联的外键表名和列名前, 请先添加列对象(addColumn(...))");
			}
			if(StringUtil.isEmpty(fkTableName)) {
				throw new NullPointerException("配置的外键约束, 关联的表名不能为空");
			}
			if(StringUtil.isEmpty(fkColumnName)) {
				throw new NullPointerException("配置的外键约束, 关联的列名不能为空");
			}
			this.fkTableName = fkTableName;
			this.fkColumnName = fkColumnName;
			this.column.fkTableName = fkTableName;
			this.column.fkColumnName = fkColumnName;
		}
		throw new UnsupportConstraintConfigurationException("非外键约束, 而是["+constraintType.name()+"]约束, 无法设置关联的外键表名和列名");
	}
}
