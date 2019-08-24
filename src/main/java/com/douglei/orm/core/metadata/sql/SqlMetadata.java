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
	private List<ContentMetadata> contents;
	
	public SqlMetadata(String namespace) {
		this.namespace = namespace;
	}

	/**
	 * 添加 sql content
	 * @param sqlContentMetadata
	 */
	public void addContentMetadata(ContentMetadata contentMetadata) {
		if(contents == null) {
			contents = new ArrayList<ContentMetadata>(10);
		}else {
			String contentName = contentMetadata.getName();
			contents.forEach(content -> {
				if(content.getName().equals(contentName)) {
					throw new RepeatedContentNameException(contentName);
				}
			});
		}
		contents.add(contentMetadata);
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
	public List<ContentMetadata> getContents() {
		DialectType currentDialectType = EnvironmentContext.getEnvironmentProperty().getDialect().getType();
		List<ContentMetadata> list = new ArrayList<ContentMetadata>(contents.size());
		for (ContentMetadata content : contents) {
			if(content.isMatchingDialectType(currentDialectType)) {
				list.add(content);
			}
		}
		return list;
	}
	
	private class RepeatedContentNameException extends RuntimeException{
		public RepeatedContentNameException(String contentName) {
			super("重复配置了name=["+contentName+"]的<content>元素");
		}
	}
}