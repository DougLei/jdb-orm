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
	private Map<String, SqlContentMetadata> sqlContents;
	private Map<String, List<Validator>> validatorMap;
	
	public SqlMetadata(String namespace, String oldNamespace) {
		this.name = namespace;
		this.oldName = oldNamespace;
	}
	public void setContents(List<ContentMetadata> contents) {
		this.contents = contents;
	}
	public void addSqlContents(String name, SqlContentMetadata sqlContent) {
		if(this.sqlContents == null)
			this.sqlContents = new HashMap<String, SqlContentMetadata>();
		this.sqlContents.put(name, sqlContent);
	}
	public void addValidators(String name, List<Validator> validators) {
		if(this.validatorMap == null)
			this.validatorMap = new HashMap<String, List<Validator>>();
		this.validatorMap.put(name, validators);
	}
	
	/**
	 * 获取ContentMetadata集合
	 * @return
	 */
	public List<ContentMetadata> getContents() {
		return contents;
	}
	
	/**
	 * 获取SqlContentMetadata集合
	 * @return
	 */
	public Map<String, SqlContentMetadata> getSqlContents() {
		return sqlContents;
	}
	
	/**
	 * 获取ValidatorMap集合
	 * @return
	 */
	public Map<String, List<Validator>> getValidatorMap() {
		return validatorMap;
	}
}