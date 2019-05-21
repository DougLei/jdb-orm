package com.douglei.database.metadata.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.douglei.database.dialect.DialectType;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataType;
import com.douglei.utils.StringUtil;

/**
 * sql元数据
 * @author DougLei
 */
public class SqlMetadata implements Metadata{
	
	private String namespace;
	private String name;
	private Map<String, List<SqlContentMetadata>> contentMap;
	
	public SqlMetadata(String namespace, String name) {
		setNamespace(namespace);
		this.name = name;
	}
	private void setNamespace(String namespace) {
		if(StringUtil.isEmpty(namespace)) {
			if(namespace != null) {
				namespace = null;
			}
		}
		this.namespace = namespace;
	}

	/**
	 * 添加 sql content
	 * @param sqlContentMetadata
	 */
	public void addContentMetadata(SqlContentMetadata sqlContentMetadata) {
		if(contentMap == null) {
			contentMap = new HashMap<String, List<SqlContentMetadata>>(DialectType.values().length);
		}
		
		String dialectTypeCode = sqlContentMetadata.getCode();
		List<SqlContentMetadata> contents = contentMap.get(dialectTypeCode);
		if(contents == null) {
			contents = new ArrayList<SqlContentMetadata>(5);
			contentMap.put(dialectTypeCode, contents);
		}
		contents.add(sqlContentMetadata);
	}
	
	/**
	 * <pre>
	 * 	如果namespace为空, 返回name
	 * 	如果namespace不为空, 返回namespace.name
	 * </pre>
	 */
	@Override
	public String getCode() {
		if(namespace == null) {
			return name;
		}
		return namespace+"."+name;
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.SQL;
	}
	
	public String getNamespace() {
		return namespace;
	}
	public String getName() {
		return name;
	}
	public List<SqlContentMetadata> getContents(String dialectTypeCode) {
		List<SqlContentMetadata> contents = contentMap.get(dialectTypeCode);
		if(contents == null || contents.size() == 0) {
			contents = contentMap.get(DialectType.ALL.getCode());// 如果没有找到, 用ALL再找
		}
		return contents;
	}
}
