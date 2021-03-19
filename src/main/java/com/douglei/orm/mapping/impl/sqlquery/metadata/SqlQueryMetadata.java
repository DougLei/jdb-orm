package com.douglei.orm.mapping.impl.sqlquery.metadata;

import java.util.Map;

import com.douglei.orm.mapping.impl.sql.metadata.content.ContentMetadata;
import com.douglei.orm.mapping.metadata.AbstractMetadata;

/**
 * 
 * @author DougLei
 */
public class SqlQueryMetadata extends AbstractMetadata {
	private ContentMetadata content;
	private Map<String, ParameterMetadata> parameterMap;
	
	public SqlQueryMetadata(String name, String oldName) {
		this.name = name;
		this.oldName = oldName;
	}
	public void setContent(ContentMetadata content) {
		this.content = content;
	}
	public void setParameterMap(Map<String, ParameterMetadata> parameterMap) {
		this.parameterMap = parameterMap;
	}

	public ContentMetadata getContent() {
		return content;
	}
	public Map<String, ParameterMetadata> getParameterMap() {
		return parameterMap;
	}
}
