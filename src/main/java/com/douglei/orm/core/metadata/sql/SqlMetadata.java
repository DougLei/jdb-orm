package com.douglei.orm.core.metadata.sql;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.context.EnvironmentContext;
import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.MetadataType;

/**
 * sql元数据
 * @author DougLei
 */
public class SqlMetadata implements Metadata{
	private String namespace;
	private List<SqlContentMetadata> contents;// TODO 解决下重复name的问题
	
	public SqlMetadata(String namespace) {
		this.namespace = namespace;
	}

	/**
	 * 添加 sql content
	 * @param sqlContentMetadata
	 */
	public void addSqlContentMetadata(SqlContentMetadata sqlContentMetadata) {
		if(contents == null) {
			contents = new ArrayList<SqlContentMetadata>(10);
		}
		contents.add(sqlContentMetadata);
	}
	
	@Override
	public String getCode() {
		return namespace;
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.SQL;
	}
	
	public String getNamespace() {
		return namespace;
	}
	public List<SqlContentMetadata> getContents() {
		DialectType currentDialectType = EnvironmentContext.getEnvironmentProperty().getDialect().getType();
		List<SqlContentMetadata> list = new ArrayList<SqlContentMetadata>(contents.size());
		for (SqlContentMetadata content : contents) {
			if(content.isMatchingDialectType(currentDialectType)) {
				list.add(content);
			}
		}
		return list;
	}
}
