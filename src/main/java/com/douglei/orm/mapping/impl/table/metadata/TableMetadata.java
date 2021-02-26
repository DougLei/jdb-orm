package com.douglei.orm.mapping.impl.table.metadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.douglei.orm.configuration.environment.CreateMode;
import com.douglei.orm.mapping.metadata.AbstractMetadata;
import com.douglei.tools.StringUtil;

/**
 * 表元数据
 * @author DougLei
 */
public class TableMetadata extends AbstractMetadata {
	private String className;// 映射的代码类名
	private CreateMode createMode;// 创建模式
	
	private List<ColumnMetadata> columns; // 列集合
	private transient Map<String, ColumnMetadata> columnMapByName; // <列名: 列>
	private transient Map<String, ColumnMetadata> columnMapByCode; // <code: 列>
	
	private List<ConstraintMetadata> constraints; // 约束集合
	private PrimaryKeyHandlerMetadata primaryKeyHandlerMetadata; // 主键处理器元数据
	
	public TableMetadata(String name, String oldName, String className, CreateMode createMode) {
		this.name = name;
		this.oldName = oldName;
		
		this.className = StringUtil.isEmpty(className)?null:className;
		this.createMode = createMode;
	}
	public void setColumns(List<ColumnMetadata> columns) {
		this.columns = columns;
	}
	public void setConstraints(List<ConstraintMetadata> constraints) {
		this.constraints = constraints;
	}
	public void setPrimaryKeyHandlerMetadata(PrimaryKeyHandlerMetadata primaryKeyHandlerMetadata) {
		this.primaryKeyHandlerMetadata = primaryKeyHandlerMetadata;
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
	 * 获取创建模式
	 * @return
	 */
	public CreateMode getCreateMode() {
		return createMode;
	}
	
	/**
	 * 获取 (key=列名, value=列) 的Map集合
	 * @return
	 */
	public Map<String, ColumnMetadata> getColumnMapByName() {
		if(columnMapByName == null) {
			columnMapByName = new HashMap<String, ColumnMetadata>();
			columns.forEach(column -> columnMapByName.put(column.getName(), column));
		}
		return columnMapByName;
	}

	/**
	 * 获取 (key=code, value=列) 的Map集合
	 * @return
	 */
	public Map<String, ColumnMetadata> getColumnMapByCode() {
		if(columnMapByCode == null) {
			columnMapByCode = new HashMap<String, ColumnMetadata>();
			columns.forEach(column -> columnMapByCode.put(column.getCode(), column));
		}
		return columnMapByCode;
	}
	
	/**
	 * 获取约束集合
	 * @return
	 */
	public List<ConstraintMetadata> getConstraints() {
		return constraints;
	}
	
	/**
	 * 获取主键处理器元数据
	 * @return
	 */
	public PrimaryKeyHandlerMetadata getPrimaryKeyHandlerMetadata() {
		return primaryKeyHandlerMetadata;
	}
}
