package com.douglei.orm.core.dialect.db.sql.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.core.dialect.DialectType;

/**
 * 
 * @author DougLei
 */
public class Sql implements Serializable {
	private static final long serialVersionUID = -1384705272068010225L;
	
	private String namespace;
	private String name;
	private List<SqlContent> contents;
	
	public Sql(String namespace, String name) {
		this.namespace = namespace;
		this.name = name;
	}
	
	/**
	 * 添加 sql content
	 * @param sqlContent
	 */
	public void addSqlContent(SqlContent sqlContent) {
		if(contents == null) {
			contents = new ArrayList<SqlContent>(10);
		}
		contents.add(sqlContent);
	}

	public String getNamespace() {
		return namespace;
	}
	public String getName() {
		return name;
	}
	public List<SqlContent> getContents(DialectType dialect) {
		List<SqlContent> list = new ArrayList<SqlContent>(contents.size());
		for (SqlContent content : contents) {
			if(content.getDialectType() == dialect || content.getDialectType() == DialectType.ALL) {
				list.add(content);
			}
		}
		return list;
	}
}
