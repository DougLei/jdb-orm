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
	private Map<String, List<String>> parameterMap;
	
	public SqlMetadata(String namespace, String name) {
		setNamespace(namespace);
		this.name = name;
	}
	private void setNamespace(String namespace) {
		if(StringUtil.isEmpty(namespace)) {
			namespace = null;
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
			contents = new ArrayList<SqlContentMetadata>(4);
			contentMap.put(dialectTypeCode, contents);
		}
		contents.add(sqlContentMetadata);
		
		addParameterMetadata(dialectTypeCode, sqlContentMetadata);
	}

	// 添加sql参数元数据
	private void addParameterMetadata(String dialectTypeCode, SqlContentMetadata sqlContentMetadata) {
		List<SqlParameterMetadata> spms = sqlContentMetadata.getSqlParameterOrders();
		if(spms != null && spms.size() > 0) {
			if(parameterMap == null) {
				parameterMap = new HashMap<String, List<String>>(DialectType.values().length);
			}
			
			List<String> parameterNames = parameterMap.get(dialectTypeCode);
			if(parameterNames == null) {
				parameterNames = new ArrayList<String>();
				parameterMap.put(dialectTypeCode, parameterNames);
			}
			
			String sqlParameterName = null;
			for (SqlParameterMetadata spm : spms) {
				sqlParameterName = spm.getName();
				if(!parameterNames.contains(sqlParameterName)) {
					parameterNames.add(sqlParameterName);
				}
			}
		}
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
}
