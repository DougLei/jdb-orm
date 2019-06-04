package com.douglei.core.metadata.table;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.douglei.core.dialect.db.table.entity.Column;
import com.douglei.core.dialect.db.table.entity.Table;
import com.douglei.core.metadata.Metadata;
import com.douglei.core.metadata.MetadataType;
import com.douglei.utils.StringUtil;

/**
 * 表元数据
 * @author DougLei
 */
public class TableMetadata extends Table implements Metadata{
	
	private String code;
	private String className;// 映射的代码类名
	private boolean classNameIsNull;
	private CreateMode createMode;// 表create的模式
	
	private Map<String, ColumnMetadata> columnMetadatas;// 列
	private Map<String, ColumnMetadata> primaryKeyColumnMetadatas;// 主键列
	
	public TableMetadata(String name, String className, CreateMode createMode) {
		setNameByValidate(name);
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
	private void setCode() {
		if(className == null) {
			code = name;
		}else {
			code = className;
		}
	}
	
	/**
	 * <pre>
	 * 	列数据迁移
	 * 	将table中的columns和primaryKeyColumns迁移到columnMetadatas和primaryKeyColumnMetadatas中
	 * </pre>
	 */
	public void columnDataMigration() {
		ColumnMetadata cm = null;
		
		// 迁移columns数据
		Collection<Column> cls = columns.values();
		columnMetadatas = new HashMap<String, ColumnMetadata>(cls.size());
		for (Column column : cls) {
			cm = (ColumnMetadata) column;
			columnMetadatas.put(cm.getCode(), cm);
		}
		
		// 迁移primaryKeyColumns数据
		cls = primaryKeyColumns.values();
		primaryKeyColumnMetadatas = new HashMap<String, ColumnMetadata>(cls.size());
		for (Column column : cls) {
			cm = (ColumnMetadata) column;
			primaryKeyColumnMetadatas.put(cm.getCode(), cm);
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
		return code;
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
	public Set<String> getColumnMetadataCodes() {
		return columnMetadatas.keySet();
	}
	public ColumnMetadata getColumnMetadata(String code) {
		return columnMetadatas.get(code);
	}
	public boolean isColumnMetadata(String code) {
		return columnMetadatas.containsKey(code);
	}
	public Set<String> getPrimaryKeyColumnMetadataCodes(){
		return primaryKeyColumnMetadatas.keySet();
	}
	public ColumnMetadata getPrimaryKeyColumnMetadata(String code) {
		return primaryKeyColumnMetadatas.get(code);
	}
	public boolean isPrimaryKeyColumnMetadata(String code) {
		return primaryKeyColumnMetadatas.containsKey(code);
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.TABLE;
	}
}
