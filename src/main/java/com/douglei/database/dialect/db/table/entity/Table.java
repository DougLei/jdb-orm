package com.douglei.database.dialect.db.table.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.douglei.context.DBRunEnvironmentContext;

/**
 * 
 * @author DougLei
 */
public class Table {
	protected String name;// 表名
	protected Map<String, Column> columns;// 列
	protected Map<String, Column> primaryKeyColumns;// 主键列
	protected Map<String, Constraint> constraints;// 约束
	protected Map<String, Index> indexes;// 索引
	
	public Table(String name) {
		DBRunEnvironmentContext.getDialect().getDBObjectNameHandler().validateDBObjectName(name);
		this.name = name.toUpperCase();
	}
	
	/**
	 * 添加列
	 * @param column
	 */
	public void addColumn(Column column) {
		if(columns == null) {
			columns = new HashMap<String, Column>(16);
		}else if(columns.containsKey(column.getName())) {
			throw new ColumnException("列名"+column.getName()+"重复");
		}
		columns.put(column.getName(), column);
		addPrimaryKeyColumn(column);
		addConstraint(column);
	}
	// 通过列, 添加单列主键
	private void addPrimaryKeyColumn(Column column) {
		if(column.isPrimaryKey()) {
			validatePrimaryKeyColumnExists();
			primaryKeyColumns = new HashMap<String, Column>(1);
			primaryKeyColumns.put(column.getName(), column);
		}
	}
	// 通过列, 添加单列约束
	private void addConstraint(Column column) {
		if(column.isPrimaryKey()) {
			addConstraint((Constraint)new Constraint(ConstraintType.PRIMARY_KEY, name).addColumn(column));
		}else {
			if(column.isUnique()) {
				addConstraint((Constraint)new Constraint(ConstraintType.UNIQUE, name).addColumn(column));
			}
			if(column.getDefaultValue() != null) {
				addConstraint((Constraint)new Constraint(ConstraintType.DEFAULT_VALUE, name).addColumn(column));
			}
		}
	}
	
	/**
	 * 添加约束
	 * @param constraint
	 */
	public void addConstraint(Constraint constraint) {
		if(constraints == null) {
			constraints = new HashMap<String, Constraint>(10);
		}else if(constraints.containsKey(constraint.getName())) {
			throw new ConstraintException("约束名"+constraint.getName()+"重复");
		}
		if(constraint.getConstraintType() == ConstraintType.PRIMARY_KEY) {
			validatePrimaryKeyColumnExists();
			Collection<Column> pkColumn = constraint.getColumns();
			primaryKeyColumns = new HashMap<String, Column>(pkColumn.size());
			for (Column column : pkColumn) {
				primaryKeyColumns.put(column.getName(), column);
			}
		}
		constraints.put(constraint.getName(), constraint);
	}
	
	// 验证主键列是否存在
	private void validatePrimaryKeyColumnExists() {
		if(primaryKeyColumns != null) {
			throw new ColumnException("已存在主键["+primaryKeyColumns.keySet()+"], 不能重复配置");
		}
	}
	
	/**
	 * 添加索引
	 * @param index
	 */
	public void addIndex(Index index) {
		if(indexes == null) {
			indexes = new HashMap<String, Index>(10);
		}else if(indexes.containsKey(index.getName())) {
			throw new IndexException("索引名"+index.getName()+"重复");
		}
		indexes.put(index.getName(), index);
	}
	
	public String getName() {
		return name;
	}
	public Collection<Column> getColumns() {
		return columns.values();
	}
	public Collection<Constraint> getConstraints() {
		return constraints.values();
	}
	public Collection<Index> getIndexes() {
		return indexes.values();
	}
	public Column getColumnByName(String columnName) {
		Column column = columns.get(columnName);
		if(column == null) {
			throw new NullPointerException("不存在column name=["+columnName+"]的列");
		}
		return column;
	}
	
	
	/**
	 * 是否存在主键
	 * @return
	 */
	public boolean existsPrimaryKey() {
		return primaryKeyColumns != null;
	}
}
