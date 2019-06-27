package com.douglei.orm.core.dialect.db.table.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.douglei.orm.context.DBRunEnvironmentContext;
import com.douglei.orm.core.converter.Entity2MappingContentConverter;
import com.douglei.orm.core.metadata.table.CreateMode;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public abstract class Table implements Entity2MappingContentConverter, Serializable{
	private static final long serialVersionUID = -824613028695129467L;
	
	protected String name;// 表名
	protected String oldName;// 旧表名
	protected CreateMode createMode;// 表create的模式
	
	protected Map<String, Column> columns;// 列
	protected Map<String, Column> primaryKeyColumns;// 主键列
	
	protected Map<String, Constraint> constraints;// 约束
	protected Map<String, Index> indexes;// 索引
	
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
			addConstraint(new Constraint(ConstraintType.PRIMARY_KEY, name).addColumn(column));
		}
		if(column.isUnique()) {
			addConstraint(new Constraint(ConstraintType.UNIQUE, name).addColumn(column));
		}
		if(column.getDefaultValue() != null) {
			addConstraint(new Constraint(ConstraintType.DEFAULT_VALUE, name).addColumn(column));
		}
		if(column.getCheck() != null) {
			addConstraint(new Constraint(ConstraintType.CHECK, name).addColumn(column));
		}
		if(column.getFkTableName() != null) {
			addConstraint(new Constraint(ConstraintType.FOREIGN_KEY, name).addColumn(column));
		}
	}
	
	/**
	 * 添加约束
	 * @param constraint
	 */
	public void addConstraint(Constraint constraint) {
		if(constraints == null) {
			constraints = new HashMap<String, Constraint>(8);
		}else if(constraints.containsKey(constraint.getName())) {
			throw new ConstraintException("约束名"+constraint.getName()+"重复");
		}
		if(constraint.getConstraintType() == ConstraintType.PRIMARY_KEY) {
			validatePrimaryKeyColumnExists();
			addPrimaryKeyColumns(constraint.getColumns());
		}
		constraints.put(constraint.getName(), constraint);
	}
	
	/**
	 * 添加索引
	 * @param index
	 */
	public void addIndex(Index index) {
		if(indexes == null) {
			indexes = new HashMap<String, Index>(8);
		}else if(indexes.containsKey(index.getName())) {
			throw new IndexException("索引名"+index.getName()+"重复");
		}
		indexes.put(index.getName(), index);
	}
	
	// 验证主键列是否存在
	private void validatePrimaryKeyColumnExists() {
		if(existsPrimaryKey()) {
			throw new ColumnException("已配置主键["+primaryKeyColumns.keySet()+"], 不能重复配置");
		}
	}
	
	// 添加主键列
	private void addPrimaryKeyColumns(Collection<Column> primaryKeyColumns) {
		this.primaryKeyColumns = new HashMap<String, Column>(primaryKeyColumns.size());
		for (Column primaryKeyColumn : primaryKeyColumns) {
			this.primaryKeyColumns.put(primaryKeyColumn.getName(), primaryKeyColumn);
		}
	}
	
	public String getName() {
		return name;
	}
	public String getOldName() {
		return oldName;
	}
	public CreateMode getCreateMode() {
		return createMode;
	}
	public void setNameByValidate(String name, String oldName) {
		DBRunEnvironmentContext.getEnvironmentProperty().getDialect().getDBObjectNameHandler().validateDBObjectName(name);
		this.name = name.toUpperCase();
		
		if(StringUtil.isEmpty(oldName)) {
			this.oldName = this.name;
		}else {
			this.oldName = oldName.toUpperCase();
		}
	}
	public Collection<Column> getColumns() {
		return columns.values();
	}
	public Column getColumnByName(String columnName, boolean validateColumnExists) { // validateColumnExists: 验证列是否存在
		Column column = columns.get(columnName);
		if(validateColumnExists && column == null) {
			throw new NullPointerException("不存在column name=["+columnName+"]的列");
		}
		return column;
	}
	public Collection<Constraint> getConstraints() {
		if(constraints == null) {
			return null;
		}
		return constraints.values();
	}
	public Collection<Index> getIndexes(){
		if(indexes == null) {
			return null;
		}
		return indexes.values();
	}
	
	/**
	 * 是否存在主键
	 * @return
	 */
	public boolean existsPrimaryKey() {
		return primaryKeyColumns != null;
	}
	
	@Override
	public String toXmlMappingContent() {
		StringBuilder xml = new StringBuilder(3000);
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<mapping-configuration>");
		xml.append("<table name=\"").append(name).append("\" ").append("oldName=\"").append(getOldName()).append("\" ").append("createMode=\"").append(getCreateMode().name()).append("\">");
		toXmlColumnContent(xml, columns.values());
		toXmlConstraintContent(xml, getConstraints());
		toXmlIndexContent(xml, getIndexes());
		xml.append("</table>");
		xml.append("</mapping-configuration>");
		return xml.toString();
	}
	private void toXmlColumnContent(StringBuilder xml, Collection<Column> columns) {
		if(columns != null) {
			xml.append("<columns>");
			for (Column column : columns) {
				xml.append("<column ");
				xml.append("name=\"").append(column.getName()).append("\" ").append("oldName=\"").append(column.getOldName()).append("\" ");
				xml.append("dataType=\"").append(column.getDataTypeHandler().getCode()).append("\" ");
				xml.append("length=\"").append(column.getLength()).append("\" ");
				xml.append("precision=\"").append(column.getPrecision()).append("\"");
				if(!column.isNullabled()) {
					xml.append(" nullabled=\"false\"");
				}
				if(column.isValidate()) {
					xml.append(" validate=\"true\"");
				}
				xml.append("/>");
			}
			xml.append("</columns>");
		}
	}
	private void toXmlConstraintContent(StringBuilder xml, Collection<Constraint> constraints) {
		if(constraints != null) {
			Collection<Column> columns = null;
			
			xml.append("<constraints>");
			for (Constraint constraint : constraints) {
				xml.append("<constraint type=\"").append(constraint.getConstraintType().name()).append("\"");
				toXmlConstraintTypeExtendProperties(xml, constraint);
				xml.append(">");
				columns = constraint.getColumns();
				for (Column column : columns) {
					xml.append("<column-name value=\"").append(column.getName()).append("\"/>");
				}
				xml.append("</constraint>");
			}
			xml.append("</constraints>");
		}
	}
	private void toXmlConstraintTypeExtendProperties(StringBuilder xml, Constraint constraint) {// 约束类型扩展属性的xml内容
		switch(constraint.getConstraintType()) {
			case DEFAULT_VALUE:
				xml.append(" value=\"").append(constraint.getDefaultValue()).append("\"");
				break;
			case CHECK:
				xml.append(" expression=\"").append(constraint.getCheck()).append("\"");
				break;
			case FOREIGN_KEY:
				xml.append(" fkTableName=\"").append(constraint.getFkTableName()).append("\"");
				xml.append(" fkColumnName=\"").append(constraint.getFkColumnName()).append("\"");
				break;
			default:
				// 不用处理 primaryKey unique, 他们没有扩展属性
				break;	
		}
	}
	private void toXmlIndexContent(StringBuilder xml, Collection<Index> indexes) {
		if(indexes != null) {
			xml.append("<indexes>");
			for (Index index : indexes) {// TODO 索引的临时处理
				xml.append("<index name=\"").append(index.getName()).append("\">");
				xml.append("<create>").append(index.getCreateSqlStatement()).append("</create>");
				xml.append("<drop>").append(index.getDropSqlStatement()).append("</drop>");
				xml.append("</index>");
			}
			xml.append("</indexes>");
		}
	}
}
