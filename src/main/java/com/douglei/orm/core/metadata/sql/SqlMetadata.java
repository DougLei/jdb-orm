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
	private static final long serialVersionUID = 1855517217773149671L;
	
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
	
	public List<ContentMetadata> getContents(String name) {
		return name==null?getAllContents():getContentByName(name);
	}
	
	// 获取所有满足方言的content集合
	private List<ContentMetadata> getAllContents(){
		DialectType currentDialectType = EnvironmentContext.getEnvironmentProperty().getDialect().getType();
		List<ContentMetadata> list = new ArrayList<ContentMetadata>(contents.size());
		for (ContentMetadata content : contents) {
			if(content.isMatchingDialectType(currentDialectType)) {
				list.add(content);
			}
		}
		return list;
	}
	
	// 获取指定name的content集合
	private List<ContentMetadata> getContentByName(String name) {
		DialectType currentDialectType = EnvironmentContext.getEnvironmentProperty().getDialect().getType();
		List<ContentMetadata> list = new ArrayList<ContentMetadata>(1);
		for (ContentMetadata content : contents) {
			if(content.getName().equals(name) && content.isMatchingDialectType(currentDialectType)) {
				list.add(content);
				break;
			}
		}
		return list;
	}

	private class RepeatedContentNameException extends RuntimeException{
		private static final long serialVersionUID = 4281842020558874332L;
		public RepeatedContentNameException(String contentName) {
			super("重复配置了name=["+contentName+"]的<content>元素");
		}
	}
}