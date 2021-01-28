package com.douglei.orm.mapping.impl.sql.metadata;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.content.ContentMetadata;
import com.douglei.orm.mapping.metadata.AbstractMetadata;

/**
 * sql元数据
 * @author DougLei
 */
public class SqlMetadata extends AbstractMetadata{
	private static final long serialVersionUID = -5098712069064894057L;
	private List<ContentMetadata> contents;
	
	public SqlMetadata(String namespace, String oldNamespace) {
		super.name = namespace;
		super.oldName = oldNamespace;
	}

	/**
	 * 添加 sql content
	 * @param sqlContentMetadata
	 */
	public void addContentMetadata(ContentMetadata contentMetadata) {
		if(contents == null) {
			contents = new ArrayList<ContentMetadata>();
		}else if(contents.contains(contentMetadata)){
			throw new RepeatedContentNameException(name, contentMetadata.getName());
		}
		contents.add(contentMetadata);
	}
	
	/**
	 * 获取数据库对象集合, 如果没有则返回null
	 * @return
	 */
	public List<ContentMetadata> getContents() {
		return contents;
	}
}