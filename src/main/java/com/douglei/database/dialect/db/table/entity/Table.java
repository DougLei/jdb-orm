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
		addConstraint(column);
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
	
	public String getName() {
		return name;
	}
	public Collection<Column> getColumns() {
		return columns.values();
	}
	public Collection<Constraint> getConstraints() {
		return constraints.values();
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
	
	/**
	 * 清空数据
	 */
	public void clear() {
		if(columns != null) {
			columns.clear();
			columns = null;
		}
		if(primaryKeyColumns != null) {
			primaryKeyColumns.clear();
			primaryKeyColumns = null;
		}
		if(constraints != null) {
			constraints.clear();
			constraints = null;
		}
	}
	
	/**
	 * 转换为表xml映射配置内容
	 * @return
	 */
	public String toXmlTableMappingConfigurationContent() {
		StringBuilder xml = new StringBuilder(3000);
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<mapping-configuration>");
		xml.append("<table name=\"").append(name).append("\">");
		setXmlColumnContent(xml, columns.values());
		setXmlConstraintContent(xml, constraints.values());
		xml.append("</table>");
		xml.append("</mapping-configuration>");
		return xml.toString();
	}
	private void setXmlColumnContent(StringBuilder xml, Collection<Column> columns) {
		if(columns != null) {
			xml.append("<columns>");
			for (Column column : columns) {
				xml.append("<column ");
				xml.append("name=\"").append(column.getName()).append("\" ");
				xml.append("dataType=\"").append(column.getDataTypeHandler().getCode()).append("\" ");
				if(column.isPrimaryKey()) {
					xml.append("primaryKey=\"true\" ");
				}
				xml.append("length=\"").append(column.getLength()).append("\" ");
				xml.append("precision=\"").append(column.getPrecision()).append("\" ");
				if(column.getDefaultValue() != null) {
					xml.append("defaultValue=\"").append(column.getDefaultValue()).append("\" ");
				}
				if(column.isUnique()) {
					xml.append("unique=\"true\" ");
				}
				if(!column.isNullabled()) {
					xml.append("nullabled=\"false\" ");
				}
				xml.append(" />");
			}
			xml.append("</columns>");
		}
	}
	private void setXmlConstraintContent(StringBuilder xml, Collection<Constraint> constraints) {
		if(constraints != null) {
			Collection<Column> columns = null;
			
			xml.append("<constraints>");
			for (Constraint constraint : constraints) {
				xml.append("<constraint type=\"").append(constraint.getConstraintType().name()).append("\">");
				columns = constraint.getColumns();
				for (Column column : columns) {
					xml.append("<column-name value=\"").append(column.getName()).append("\" />");
				}
				xml.append("</constraint>");
			}
			xml.append("</constraints>");
		}
	}
}
