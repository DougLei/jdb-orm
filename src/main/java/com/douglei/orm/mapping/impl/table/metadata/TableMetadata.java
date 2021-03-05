package com.douglei.orm.mapping.impl.table.metadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.douglei.orm.configuration.environment.CreateMode;
import com.douglei.orm.mapping.metadata.AbstractDBObjectMetadata;

/**
 * 表元数据
 * @author DougLei
 */
public class TableMetadata extends AbstractDBObjectMetadata {
	private String className;// 映射的代码类名
	
	private List<ColumnMetadata> columns; // 列集合
	private transient Map<String, ColumnMetadata> columnMap4Name; // <列名: 列>
	private transient Map<String, ColumnMetadata> columnMap4OldName; // <旧列名: 列>
	private transient Map<String, ColumnMetadata> columnMap4Code; // <code: 列>
	
	private List<ConstraintMetadata> constraints; // 约束集合
	private transient Map<String, ConstraintMetadata> constraintMap4Name; // <约束名: 约束>
	
	private AutoincrementPrimaryKey autoincrementPrimaryKey; // 自增主键
	
	public TableMetadata(String name, String oldName, String className, CreateMode createMode) {
		this.name = name;
		this.oldName = oldName;
		
		this.className = className;
		this.createMode = createMode;
	}
	public void setColumns(List<ColumnMetadata> columns) {
		this.columns = columns;
	}
	public void setConstraints(List<ConstraintMetadata> constraints) {
		this.constraints = constraints;
	}
	public void setAutoincrementPrimaryKey(AutoincrementPrimaryKey autoincrementPrimaryKey) {
		this.autoincrementPrimaryKey = autoincrementPrimaryKey;
	}
	
	@Override
	public String getCode() {
		if(className == null) 
			return name;
		return className;
	}
	
	/**
	 * 获取映射的class
	 * @return
	 */
	public String getClassName() {
		return className;
	}
	
	/**
	 * 获取列集合; 按照定义的顺序
	 * @return
	 */
	public List<ColumnMetadata> getColumns() {
		return columns;
	}
	
	/**
	 * 获取 (key=列名, value=列) 的Map集合
	 * @return
	 */
	public Map<String, ColumnMetadata> getColumnMap4Name() {
		if(columnMap4Name == null) {
			columnMap4Name = new HashMap<String, ColumnMetadata>();
			columns.forEach(column -> columnMap4Name.put(column.getName(), column));
		}
		return columnMap4Name;
	}
	
	/**
	 * 获取 (key=旧列名, value=列) 的Map集合
	 * @return
	 */
	public Map<String, ColumnMetadata> getColumnMap4OldName() {
		if(columnMap4OldName == null) {
			columnMap4OldName = new HashMap<String, ColumnMetadata>();
			columns.forEach(column -> columnMap4OldName.put(column.getOldName(), column));
		}
		return columnMap4OldName;
	}

	/**
	 * 获取 (key=code, value=列) 的Map集合
	 * @return
	 */
	public Map<String, ColumnMetadata> getColumnMap4Code() {
		if(columnMap4Code == null) {
			columnMap4Code = new HashMap<String, ColumnMetadata>();
			columns.forEach(column -> columnMap4Code.put(column.getCode(), column));
		}
		return columnMap4Code;
	}
	
	/**
	 * 获取约束集合
	 * @return
	 */
	public List<ConstraintMetadata> getConstraints() {
		return constraints;
	}
	
	/**
	 * 获取 (key=约束名, value=约束) 的Map集合
	 * @return
	 */
	public Map<String, ConstraintMetadata> getConstraintMap4Name() {
		if(constraintMap4Name == null) {
			constraintMap4Name = new HashMap<String, ConstraintMetadata>();
			constraints.forEach(constraint -> constraintMap4Name.put(constraint.getName(), constraint));
		}
		return constraintMap4Name;
	}
	
	/**
	 * 获取主键约束; 返回null表示当前表未设置主键
	 * @return
	 */
	public ConstraintMetadata getPrimaryKeyConstraint() {
		if(constraints == null)
			return null;
		
		for (ConstraintMetadata constraint : constraints) {
			if(constraint.getType().isPrimaryKey())
				return constraint;
		}
		return null;
	}
	
	/**
	 * 获取自增主键; 返回null表示当前表未设置自增主键
	 * @return
	 */
	public AutoincrementPrimaryKey getAutoincrementPrimaryKey() {
		return autoincrementPrimaryKey;
	}
}