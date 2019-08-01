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
import com.douglei.orm.context.DBRunEnvironmentContext;
import com.douglei.orm.core.dialect.db.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.MetadataType;
import com.douglei.orm.core.metadata.table.pk.PrimaryKeyHandler;
import com.douglei.tools.utils.StringUtil;

/**
 * 表元数据
 * @author DougLei
 */
public class TableMetadata implements Metadata{
	private static final long serialVersionUID = 3501492159316983725L;
	
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
	
	private byte validateColumnLength;// 需要验证列的数量
	private Map<String, ColumnMetadata> validateColumns;// 需要验证列<code: 列>
	
	private Map<String, Constraint> constraints;// 约束
	private Map<String, Index> indexes;// 索引
	
	public TableMetadata(String name, String oldName, String className, CreateMode createMode) {
		setNameByValidate(name, oldName);
		this.className = className;
		this.createMode = createMode;
	}
	
	// 设置name的同时, 对name进行验证
	private void setNameByValidate(String name, String oldName) {
		DBRunEnvironmentContext.getEnvironmentProperty().getDialect().getDBObjectHandler().validateDBObjectName(name);
		this.name = name.toUpperCase();
		if(StringUtil.isEmpty(oldName)) {
			this.oldName = this.name;
		}else {
			this.oldName = oldName.toUpperCase();
		}
	}
	
	/**
	 * 同步操作, 将<列名: 列>的集合数据, 同步到<code: 列>的集合中, 以及设置需要验证的列
	 */
	public void sync() {
		if(classNameEmpty()) {
			columns_ = columns;
		}else {
			columns_ = new HashMap<String, ColumnMetadata>(columns.size());
			columns.forEach((key, value) -> columns_.put(value.getProperty(), value));
		}
		setValidateColumns();
	}

	// 设置需要验证的列
	private void setValidateColumns() {
		if(validateColumnLength > 0) {
			validateColumns = new HashMap<String, ColumnMetadata>(validateColumnLength);
			columns_.forEach((key, value) -> {
				if(value.isValidate()) {
					validateColumns.put(value.getProperty(), value);
				}
			});
		}
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
		if(column.isValidate()) validateColumnLength++;
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
			this.primaryKeyColumns_.put(pkcolumn.getProperty(), pkcolumn);
		}
	}
	
	/**
	 * 验证主键列是否存在
	 */
	public void validatePrimaryKeyColumnExists() {
		if(existsPrimaryKey()) {
			throw new RepeatedPrimaryKeyException("已配置主键["+primaryKeyColumns_.keySet()+"], 不能重复配置");
		}
	}
	
	/**
	 * 是否存在主键
	 * @return
	 */
	public boolean existsPrimaryKey() {
		return primaryKeyColumns_ != null;
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
	 * 是否有需要验证字段
	 * @return
	 */
	public boolean isValidateColumn() {
		return validateColumns != null;
	}
	
	/**
	 * 给实体map设置主键值
	 * @param entityMap
	 */
	public void setPrimaryKeyValue2EntityMap(Map<String, Object> entityMap) {
		if(primaryKeyHandler != null) {
			primaryKeyHandler.setValue2EntityMap(primaryKeyColumns_.keySet(), this, entityMap, primaryKeySequence);
		}
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
	public boolean classNameEmpty() {
		return StringUtil.isEmpty(className);
	}
	public void setPrimaryKeyHandler(PrimaryKeyHandler primaryKeyHandler) {
		this.primaryKeyHandler = primaryKeyHandler;
	}
	public void setPrimaryKeySequence(PrimaryKeySequence primaryKeySequence) {
		this.primaryKeySequence = primaryKeySequence;
	}
	public PrimaryKeySequence getPrimaryKeySequence() {
		return primaryKeySequence;
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
	public int getPrimaryKeyCount() {
		return primaryKeyColumns_.size();
	}
	
	// 根据code获取要验证的列
	public ColumnMetadata getValidateColumnByCode(String code) {
		return validateColumns.get(code);
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
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.TABLE;
	}
}
