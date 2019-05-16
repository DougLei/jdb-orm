package com.douglei.database.metadata.table;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataType;
import com.douglei.utils.StringUtil;

/**
 * 表元数据
 * @author DougLei
 */
public class TableMetadata implements Metadata{
	
	/**
	 * 表名
	 */
	private String name;
	/**
	 * 映射的代码类名
	 */
	private String className;
	private boolean classNameIsNull;
	
	/**
	 * 包含的列元数据集合
	 * <pre>
	 * 	key=列元数据的code
	 * 	value=列元数据对象
	 * </pre>
	 */
	private Map<String, ColumnMetadata> columns;
	/**
	 * 主键列元数据集合
	 */
	private Map<String, ColumnMetadata> primaryKeyColumns;
	
	public TableMetadata(String name, String className) {
		this.name = name.toUpperCase();
		setClassName(className);
		setCode();
	}
	
	public void addColumnMetadata(ColumnMetadata columnMetadata) {
		if(columns == null) {
			columns = new HashMap<String, ColumnMetadata>();
		}
		columns.put(columnMetadata.getCode(), columnMetadata);
	}
	
	public void addPrimaryKeyColumnMetadata(ColumnMetadata columnMetadata) {
		if(primaryKeyColumns == null) {
			primaryKeyColumns = new HashMap<String, ColumnMetadata>(3);
		}
		primaryKeyColumns.put(columnMetadata.getCode(), columnMetadata);
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
	public void setName(String name) {
		
	}
	public String getClassName() {
		return className;
	}
	private void setClassName(String className) {
		if(StringUtil.isEmpty(className)) {
			classNameIsNull = true;
		}else {
			this.className = className;
		}
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
	
	public boolean classNameIsNull() {
		return classNameIsNull;
	}

	@Override
	public MetadataType getMetadataType() {
		return MetadataType.TABLE;
	}
}
