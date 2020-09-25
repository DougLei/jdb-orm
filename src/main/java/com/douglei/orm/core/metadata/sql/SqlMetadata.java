package com.douglei.orm.core.metadata.sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.sql.content.ContentMetadata;

/**
 * sql元数据
 * @author DougLei
 */
public class SqlMetadata implements Metadata{
	private static final long serialVersionUID = 2951312641689328484L;
	
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
			contents = new ArrayList<ContentMetadata>();
		}else {
			String contentName = contentMetadata.getName();
			contents.forEach(content -> {
				if(content.getName().equals(contentName)) 
					throw new RepeatedContentNameException(contentName);
			});
		}
		contents.add(contentMetadata);
	}
	
	@Override
	public String getCode() {
		return namespace;
	}
	
	/**
	 * 获取指定name的sql content, 如果name=null, 则返回所有sql content
	 * @param name
	 * @return 非null的list
	 */
	public List<ContentMetadata> getContents(String name) {
		return name==null?contents:getContentByName(name);
	}
	
	// 获取指定name的content集合
	private List<ContentMetadata> getContentByName(String name) {
		for (ContentMetadata content : contents) {
			if(content.getName().equals(name)) {
				List<ContentMetadata> list = new ArrayList<ContentMetadata>(1);
				list.add(content);
				return list;
			}
		}
		return Collections.emptyList();
	}

	/**
	 * 获取数据库对象集合, 如果没有则返回null
	 * @return
	 */
	public List<ContentMetadata> getContents() {
		return contents;
	}
}