package com.douglei.orm.mapping.impl.sql.metadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.douglei.orm.mapping.impl.sql.metadata.content.ContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.SqlContentMetadata;
import com.douglei.orm.mapping.metadata.AbstractMetadata;
import com.douglei.orm.mapping.validator.Validator;

/**
 * 
 * @author DougLei
 */
public class SqlMetadata extends AbstractMetadata{
	private List<ContentMetadata> contents;
	private Map<String, SqlContentMetadata> sqlContentMap;
	private Map<String, List<Validator>> validatorsMap;
	
	public SqlMetadata(String namespace, String oldNamespace) {
		this.name = namespace;
		this.oldName = oldNamespace;
	}
	public void setContents(List<ContentMetadata> contents) {
		this.contents = contents;
	}
	public void addSqlContent(SqlContentMetadata sqlContent) {
		if(this.sqlContentMap == null)
			this.sqlContentMap = new HashMap<String, SqlContentMetadata>();
		this.sqlContentMap.put(sqlContent.getName(), sqlContent);
	}
	public void putValidators(String name, List<Validator> validators) {
		if(this.validatorsMap == null)
			this.validatorsMap = new HashMap<String, List<Validator>>();
		this.validatorsMap.put(name, validators);
	}
	
	/**
	 * 获取ContentMetadata集合
	 * @return
	 */
	public List<ContentMetadata> getContents() {
		return contents;
	}
	
	/**
	 * 获取SqlContentMetadata的Map集合
	 * @return
	 */
	public Map<String, SqlContentMetadata> getSqlContentMap() {
		return sqlContentMap;
	}
	
	/**
	 * 获取Validator list的Map集合
	 * @return
	 */
	public Map<String, List<Validator>> getValidatorsMap() {
		return validatorsMap;
	}
}