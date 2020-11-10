package com.douglei.orm.mapping.impl.table.metadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.douglei.orm.dialect.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.mapping.impl.table.exception.ColumnConfigurationException;
import com.douglei.orm.mapping.impl.table.exception.ConstraintConfigurationException;
import com.douglei.orm.mapping.impl.table.exception.IndexConfigurationException;
import com.douglei.orm.mapping.impl.table.exception.RepeatedPrimaryKeyException;
import com.douglei.orm.mapping.impl.table.metadata.pk.PrimaryKeyHandler;
import com.douglei.orm.mapping.impl.table.metadata.pk.impl.SequencePrimaryKeyHandler;
import com.douglei.orm.mapping.metadata.AbstractMetadata;
import com.douglei.tools.utils.StringUtil;

/**
 * 表元数据
 * @author DougLei
 */
public class TableMetadata extends AbstractMetadata {
	private static final long serialVersionUID = -921176736727336961L;
	
	private String className;// 映射的代码类名
	private CreateMode createMode;// 创建模式
	
	private List<ColumnMetadata> declareColumns;// 按声明顺序的列
	private Map<String, ColumnMetadata> columns;// <列名: 列>
	private Map<String, ColumnMetadata> columns_;// <code: 列>
	
	private Map<String, ColumnMetadata> primaryKeyColumns_;// <code: 列>
	private PrimaryKeyHandler primaryKeyHandler;
	private PrimaryKeySequence primaryKeySequence;
	
	private Map<String, Constraint> constraints;// 约束
	private List<UniqueConstraint> uniqueConstraints;// 唯一约束集合
	
	private Map<String, Index> indexes;// 索引
	
	private List<ColumnMetadata> validateColumns;// 需要验证的列
	
	public TableMetadata(String name, String oldName, String className, CreateMode createMode) {
		super(name, oldName);
		this.className = StringUtil.isEmpty(className)?null:className;
		this.createMode = createMode;
	}
	
	/**
	 * 同步操作, 将<列名: 列>的集合数据, 同步到<code: 列>的集合中
	 */
	public void sync() {
		columns_ = new HashMap<String, ColumnMetadata>(columns.size());
		columns.values().forEach(col -> columns_.put(col.getCode(), col));
	}

	/**
	 * 添加列	
	 * @param column
	 */
	public void addColumn(ColumnMetadata column) {
		if(columns == null) {
			declareColumns = new ArrayList<ColumnMetadata>();
			columns = new HashMap<String, ColumnMetadata>();
		}else if(columns.containsKey(column.getName())) {
			throw new ColumnConfigurationException("列名"+column.getName()+"重复");
		}
		declareColumns.add(column);
		columns.put(column.getName(), column);
		addConstraint(column);
	}
	
	// 通过列, 添加单列约束
	private void addConstraint(ColumnMetadata column) {
		if(column.isPrimaryKey()) 
			addConstraint(new Constraint(ConstraintType.PRIMARY_KEY, name).addColumn(column));
		if(column.isUnique()) 
			addConstraint(new Constraint(ConstraintType.UNIQUE, name).addColumn(column));
		if(column.getDefaultValue() != null) 
			addConstraint(new Constraint(ConstraintType.DEFAULT_VALUE, name).addColumn(column));
		if(column.getCheck() != null) 
			addConstraint(new Constraint(ConstraintType.CHECK, name).addColumn(column));
		if(column.getFkTableName() != null) 
			addConstraint(new Constraint(ConstraintType.FOREIGN_KEY, name).addColumn(column));
	}
	
	/**
	 * 添加要验证的列
	 * @param column
	 */
	public void addValidateColumn(ColumnMetadata column) {
		if(column != null) {
			if(validateColumns == null) 
				validateColumns = new ArrayList<ColumnMetadata>(declareColumns.size());
			validateColumns.add(column);
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
			throw new ConstraintConfigurationException("约束名"+constraint.getName()+"重复");
		}
		if(constraint.getConstraintType() == ConstraintType.PRIMARY_KEY) {
			if(primaryKeyColumns_ != null) // 验证主键列是否存在
				throw new RepeatedPrimaryKeyException("已配置主键["+primaryKeyColumns_.keySet()+"], 不能重复配置");
			
			Collection<ColumnMetadata> pkColumns = constraint.getColumns();
			this.primaryKeyColumns_ = new HashMap<String, ColumnMetadata>(pkColumns.size());
			for (ColumnMetadata pkcolumn : pkColumns) 
				this.primaryKeyColumns_.put(pkcolumn.getCode(), pkcolumn);
		}else if(constraint.getConstraintType() == ConstraintType.UNIQUE) {
			if(uniqueConstraints == null) 
				uniqueConstraints = new ArrayList<UniqueConstraint>(5);
			uniqueConstraints.add(new UniqueConstraint(constraint));
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
			throw new IndexConfigurationException("索引名"+index.getName()+"重复");
		}
		indexes.put(index.getName(), index);
	}
	
	/**
	 * 获取约束集合
	 * @return
	 */
	public Collection<Constraint> getConstraints() {
		if(constraints == null) 
			return null;
		return constraints.values();
	}
	/**
	 * 获取指定name的约束
	 * @param name
	 * @return
	 */
	public Constraint getConstraint(String name) {
		if(constraints == null) 
			return null;
		return constraints.get(name);
	}
	
	/**
	 * 获取索引集合
	 * @return
	 */
	public Collection<Index> getIndexes(){
		if(indexes == null) 
			return null;
		return indexes.values();
	}
	/**
	 * 获取指定name的索引
	 * @param name
	 * @return
	 */
	public Index getIndex(String name) {
		if(indexes == null) 
			return null;
		return indexes.get(name);
	}

	public void setPrimaryKeyHandler(PrimaryKeyHandler primaryKeyHandler) {
		this.primaryKeyHandler = primaryKeyHandler;
	}
	public void setPrimaryKeySequence(PrimaryKeySequence primaryKeySequence) {
		this.primaryKeySequence = primaryKeySequence;
	}

	/**
	 * 	如果指定了className, 则返回className, 否则返回name, 即表名
	 * @return
	 */
	@Override
	public String getCode() {
		if(className == null) {
			return name;
		}else {
			return className;
		}
	}
	
	public CreateMode getCreateMode() {
		return createMode;
	}
	public PrimaryKeyHandler getPrimaryKeyHandler() {
		return primaryKeyHandler;
	}
	public PrimaryKeySequence getPrimaryKeySequence() {
		return primaryKeySequence;
	}
	
	/**
	 * 获取列集合
	 * @return key为列名, value为ColumnMetadata
	 */
	public Map<String, ColumnMetadata> getColumns(){
		return columns;
	}
	
	/**
	 * 获取列集合
	 * @return key为code, value为ColumnMetadata
	 */
	public Map<String, ColumnMetadata> getColumns_() {
		return columns_;
	}

	/**
	 * 获取主键列集合
	 * @return key为code, value为ColumnMetadata
	 */
	public Map<String, ColumnMetadata> getPrimaryKeyColumns_(){
		return primaryKeyColumns_;
	}
	
	/**
	 * 获取要验证的列集合
	 * @return
	 */
	public List<ColumnMetadata> getValidateColumns() {
		return validateColumns;
	}
	
	/**
	 * 获取唯一约束集合 
	 * @return
	 */
	public List<UniqueConstraint> getUniqueConstraints() {
		return uniqueConstraints;
	}

	/**
	 * 获取按照定义顺序的列集合 
	 * @return
	 */
	public List<ColumnMetadata> getDeclareColumns() {
		return declareColumns;
	}
	
	/**
	 * 给实体map设置主键值
	 * @param objectMap 实体map
	 * @param originObject 源实体, 如果可以将生成的id值, 也保存到源实例中, 通过引用传递, 返回给调用者, 例如uuid32, uuid36这种主键生成器
	 */
	public void setPrimaryKeyValue2ObjectMap(Map<String, Object> objectMap, Object originObject) {
		if(primaryKeyHandler != null) {
			if(primaryKeyHandler instanceof SequencePrimaryKeyHandler) {
				primaryKeyHandler.setValue2ObjectMap(primaryKeyColumns_.keySet(), objectMap, primaryKeySequence);
			}else {
				primaryKeyHandler.setValue2ObjectMap(primaryKeyColumns_.keySet(), objectMap, originObject);
			}
		}
	}
}
