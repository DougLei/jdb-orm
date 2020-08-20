package com.douglei.orm.core.metadata.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.douglei.orm.configuration.environment.mapping.table.ColumnConfigurationException;
import com.douglei.orm.configuration.environment.mapping.table.ConstraintConfigurationException;
import com.douglei.orm.configuration.environment.mapping.table.IndexConfigurationException;
import com.douglei.orm.configuration.environment.mapping.table.RepeatedPrimaryKeyException;
import com.douglei.orm.context.EnvironmentContext;
import com.douglei.orm.core.dialect.db.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.MetadataType;
import com.douglei.orm.core.metadata.table.pk.PrimaryKeyHandler;
import com.douglei.orm.core.metadata.table.pk.impl.SequencePrimaryKeyHandler;
import com.douglei.tools.utils.StringUtil;

/**
 * 表元数据
 * @author DougLei
 */
public class TableMetadata implements Metadata{
	private static final long serialVersionUID = -7003748045936176394L;
	private String name;// 表名
	private String className;// 映射的代码类名
	
	private String oldName;// 旧表名
	private CreateMode createMode;// 表create的模式
	
	private List<ColumnMetadata> declareColumns;// 按声明顺序的列
	private Map<String, ColumnMetadata> columns;// 列<列名: 列>
	
	private Map<String, ColumnMetadata> columns_;// 列<code: 列>
	private Map<String, ColumnMetadata> primaryKeyColumns_;// 主键列<code: 列>
	
	private PrimaryKeyHandler primaryKeyHandler;
	private PrimaryKeySequence primaryKeySequence;
	
	private List<ColumnMetadata> validateColumns;// 需要验证的列
	
	private Map<String, Constraint> constraints;// 约束
	private List<UniqueConstraint> uniqueConstraints;// 唯一约束集合
	
	private Map<String, Index> indexes;// 索引
	
	public TableMetadata(String name, String oldName, String className, CreateMode createMode) {
		setNameByValidate(name, oldName);
		this.className = className;
		this.createMode = createMode;
	}
	
	// 设置name的同时, 对name进行验证
	private void setNameByValidate(String name, String oldName) {
		EnvironmentContext.getDialect().getDBObjectHandler().validateDBObjectName(name);
		this.name = name.toUpperCase();
		if(StringUtil.isEmpty(oldName)) {
			this.oldName = this.name;
		}else {
			this.oldName = oldName.toUpperCase();
		}
	}
	
	/**
	 * 同步操作, 将<列名: 列>的集合数据, 同步到<code: 列>的集合中
	 */
	public void sync() {
		columns_ = new HashMap<String, ColumnMetadata>(columns.size());
		columns.forEach((key, value) -> columns_.put(value.getCode(), value));
	}

	/**
	 * 添加列
	 * @param column
	 */
	public void addColumn(ColumnMetadata column) {
		if(columns == null) {
			columns = new HashMap<String, ColumnMetadata>();
		}else if(columns.containsKey(column.getName())) {
			throw new ColumnConfigurationException("列名"+column.getName()+"重复");
		}
		columns.put(column.getName(), column);
		addConstraint(column);
		addDeclareColumn(column);
	}
	
	// 按照声明顺序添加列
	private void addDeclareColumn(ColumnMetadata column) {
		if(declareColumns == null) {
			declareColumns = new ArrayList<ColumnMetadata>();
		}
		declareColumns.add(column);
	}

	// 添加主键列
	private void addPrimaryKeyColumns(Collection<ColumnMetadata> pkColumns) {
		this.primaryKeyColumns_ = new HashMap<String, ColumnMetadata>(pkColumns.size());
		for (ColumnMetadata pkcolumn : pkColumns) {
			this.primaryKeyColumns_.put(pkcolumn.getCode(), pkcolumn);
		}
	}
	
	/**
	 * 设置要验证的列
	 * @param column
	 */
	public void setValidateColumn(ColumnMetadata column) {
		if(column != null) {
			if(validateColumns == null) {
				validateColumns = new ArrayList<ColumnMetadata>(declareColumns.size());
			}
			validateColumns.add(column);
		}
	}
	
	// 通过列, 添加单列约束
	private void addConstraint(ColumnMetadata column) {
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
			throw new ConstraintConfigurationException("约束名"+constraint.getName()+"重复");
		}
		if(constraint.getConstraintType() == ConstraintType.PRIMARY_KEY) {
			validatePrimaryKeyColumnExists();
			addPrimaryKeyColumns(constraint.getColumns());
		}else if(constraint.getConstraintType() == ConstraintType.UNIQUE) {
			if(uniqueConstraints == null) {
				uniqueConstraints = new ArrayList<UniqueConstraint>(4);
			}
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
	
	// 获取约束集合
	public Collection<Constraint> getConstraints() {
		if(constraints == null) {
			return null;
		}
		return constraints.values();
	}
	// 根据约束名, 获取约束
	public Constraint getConstraintByName(String constraintName) {
		if(constraints == null) {
			return null;
		}
		return constraints.get(constraintName);
	}
	
	// 获取索引集合
	public Collection<Index> getIndexes(){
		if(indexes == null) {
			return null;
		}
		return indexes.values();
	}
	// 根据索引名, 获取索引
	public Index getIndexByName(String indexName) {
		if(indexes == null) {
			return null;
		}
		return indexes.get(indexName);
	}

	/**
	 * <pre>
	 * 	如果指定了className, 则返回className
	 * 	否则返回name, 即表名
	 * </pre>
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
	public String getName() {
		return name;
	}
	public String getOldName() {
		return oldName;
	}
	public CreateMode getCreateMode() {
		return createMode;
	}
	public String getClassName() {
		return className;
	}
	
	public void setPrimaryKeyHandler(PrimaryKeyHandler primaryKeyHandler) {
		this.primaryKeyHandler = primaryKeyHandler;
	}
	public boolean existsPrimaryKeyHandler() {
		return primaryKeyHandler != null;
	}
	public void setPrimaryKeySequence(PrimaryKeySequence primaryKeySequence) {
		this.primaryKeySequence = primaryKeySequence;
	}
	public PrimaryKeySequence getPrimaryKeySequence() {
		return primaryKeySequence;
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

	// 获取列的code集合
	public Set<String> getColumnCodes() {
		return columns_.keySet();
	}
	// 根据code获取列
	public ColumnMetadata getColumnByCode(String code) {
		return columns_.get(code);
	}
	// 根据code判断是否是列
	public boolean isColumnByCode(String code) {
		return columns_.containsKey(code);
	}
	
	// 验证主键列是否存在
	private void validatePrimaryKeyColumnExists() {
		if(existsPrimaryKey()) {
			throw new RepeatedPrimaryKeyException("已配置主键["+primaryKeyColumns_.keySet()+"], 不能重复配置");
		}
	}
	// 是否存在主键
	public boolean existsPrimaryKey() {
		return primaryKeyColumns_ != null;
	}
	
	// 获取主键列的code集合
	public Set<String> getPrimaryKeyColumnCodes(){
		return primaryKeyColumns_.keySet();
	}
	// 根据code获取主键列
	public ColumnMetadata getPrimaryKeyColumnByCode(String code) {
		return primaryKeyColumns_.get(code);
	}
	// 根据code判断是否是主键列
	public boolean isPrimaryKeyColumnByCode(String code) {
		return primaryKeyColumns_.containsKey(code);
	}
	// 获取主键列的数量
	public byte primaryKeyCount() {
		return (byte) primaryKeyColumns_.size();
	}
	
	// 是否存在需要验证的列
	public boolean existsValidateColumns() {
		return validateColumns != null;
	}
	// 获取要验证的列集合
	public List<ColumnMetadata> getValidateColumns() {
		return validateColumns;
	}
	
	// 获取唯一约束集合
	public List<UniqueConstraint> getUniqueConstraints() {
		return uniqueConstraints;
	}

	// 获取按照定义顺序的列集合
	public List<ColumnMetadata> getDeclareColumns() {
		return declareColumns;
	}
	
	/**
	 * 根据列名获取列对象
	 * @param columnName
	 * @param validateColumnExists 验证列是否存在, 如果为true, 且列不存在则会抛出异常
	 * @return
	 */
	public ColumnMetadata getColumnByName(String columnName, boolean validateColumnExists) {
		ColumnMetadata column = columns.get(columnName);
		if(validateColumnExists && column == null) {
			throw new NullPointerException("不存在column name=["+columnName+"]的列");
		}
		return column;
	}
	
	/**
	 * 根据列名, 验证列是否存在
	 * @param columnName
	 * @return 返回列的code值 {@link ColumnMetadata#getCode()}
	 */
	public String validateColumnExistsByName(String columnName) {
		ColumnMetadata column = columns.get(columnName);
		if(column == null) {
			throw new NullPointerException("不存在column name=["+columnName+"]的列");
		}
		return column.getCode();
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.TABLE;
	}
}
