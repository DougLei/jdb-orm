package com.douglei.database.metadata.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataType;
import com.douglei.database.metadata.table.column.extend.ColumnConstraint;
import com.douglei.database.metadata.table.column.extend.ColumnIndex;
import com.douglei.database.metadata.table.column.extend.ConstraintType;
import com.douglei.utils.StringUtil;

/**
 * 表元数据
 * @author DougLei
 */
public class TableMetadata implements Metadata{
	
	private String name;// 表名
	private String className;// 映射的代码类名
	private boolean classNameIsNull;
	
	private CreateMode createMode;// 表create的模式
	
	private Map<String, ColumnMetadata> columns;// 包含的列元数据集合, key=列元数据的code, value=列元数据对象
	private Map<String, ColumnMetadata> primaryKeyColumns;// 主键列元数据集合
	
	private List<ColumnConstraint> constraints;// 约束集合
	private List<ColumnIndex> indexes;// 索引集合
	
	public TableMetadata(String name, String className, CreateMode createMode) {
		this.name = name.toUpperCase();
		this.createMode = createMode;
		setClassName(className);
		setCode();
	}
	private void setClassName(String className) {
		if(StringUtil.isEmpty(className)) {
			classNameIsNull = true;
		}else {
			this.className = className;
		}
	}
	
	public void addColumnMetadata(ColumnMetadata columnMetadata) {
		if(columns == null) {
			columns = new HashMap<String, ColumnMetadata>(16);
		}else {
			if(columns.containsKey(columnMetadata.getCode())) {
				throw new RepeatColumnException(columnMetadata.getName()+" 列重复");
			}
		}
		columns.put(columnMetadata.getCode(), columnMetadata);
		addPrimaryKeyColumnMetadata(columnMetadata, 1);
		addConstraint(columnMetadata);
	}
	private void addConstraint(ColumnMetadata columnMetadata) {
		if(columnMetadata.isPrimaryKey()) {
			addConstraint(new ColumnConstraint(columnMetadata.getDataType(), ConstraintType.PRIMARY_KEY, name, columnMetadata.getName()));
		}else {
			if(columnMetadata.isUnique()) {
				addConstraint(new ColumnConstraint(columnMetadata.getDataType(), ConstraintType.UNIQUE, name, columnMetadata.getName()));
			}
			if(columnMetadata.getDefaultValue() != null) {
				addConstraint(new ColumnConstraint(columnMetadata.getDataType(), ConstraintType.DEFAULT_VALUE, name, columnMetadata.getName(), columnMetadata.getDefaultValue()));
			}
		}
	}
	
	public void addPrimaryKeyColumnMetadata(ColumnMetadata columnMetadata, int initialPrimaryKeyColumnsSize) {
		if(columnMetadata.isPrimaryKey()) {
			if(primaryKeyColumns == null) {
				primaryKeyColumns = new HashMap<String, ColumnMetadata>(initialPrimaryKeyColumnsSize);
			}
			primaryKeyColumns.put(columnMetadata.getCode(), columnMetadata);
		}
	}
	
	public void addConstraint(ColumnConstraint columnConstraint) {
		if(constraints == null) {
			constraints = new ArrayList<ColumnConstraint>(10);
		}
		constraints.add(columnConstraint);
	}
	
	public void addIndex(ColumnIndex columnIndex) {
		if(indexes == null) {
			indexes = new ArrayList<ColumnIndex>(6);
		}
		indexes.add(columnIndex);
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
		return code;
	}
	private String code;
	private void setCode() {
		if(className == null) {
			code = name;
		}else {
			code = className;
		}
	}
	
	public String getName() {
		return name;
	}
	public String getClassName() {
		return className;
	}
	public CreateMode getCreateMode() {
		return createMode;
	}
	public boolean classNameIsNull() {
		return classNameIsNull;
	}
	public List<ColumnConstraint> getConstraints() {
		return constraints;
	}
	public List<ColumnIndex> getIndexes() {
		return indexes;
	}
	public Collection<ColumnMetadata> getColumnMetadatas(){
		return columns.values();
	}
	public Set<String> getColumnMetadataCodes() {
		return columns.keySet();
	}
	public ColumnMetadata getColumnMetadata(String code) {
		return columns.get(code);
	}
	public boolean isColumn(String code) {
		return columns.containsKey(code);
	}
	
	public Set<String> getPrimaryKeyColumnMetadataCodes(){
		return primaryKeyColumns.keySet();
	}
	public ColumnMetadata getPrimaryKeyColumnMetadata(String code) {
		return primaryKeyColumns.get(code);
	}
	public boolean isPrimaryKeyColumn(String code) {
		return primaryKeyColumns.containsKey(code);
	}
	
	// 根据列名获取对应的ColumnMetadata
	public ColumnMetadata getColumnMetadataByColumnName(String columnName) {
		Collection<ColumnMetadata> cs = columns.values();
		for (ColumnMetadata column : cs) {
			if(column.getName().equals(columnName)) {
				return column;
			}
		}
		throw new NullPointerException("不存在column name=["+columnName+"]的列");
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.TABLE;
	}
}
