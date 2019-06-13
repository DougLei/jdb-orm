package com.douglei.orm.core.dialect.db.sql.entity;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.dialect.db.Entity2MappingContentConverter;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class Sql implements Entity2MappingContentConverter {
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

	@Override
	public String toXmlMappingContent() {
		StringBuilder xml = new StringBuilder(3000);
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<mapping-configuration>");
		xml.append("<sql name=\"").append(name).append("\"");
		if(StringUtil.notEmpty(namespace)) {
			xml.append(" namespace=\"").append(namespace).append("\"");
		}
		xml.append(">");
		toXmlSqlContent(xml);
		xml.append("</sql>");
		xml.append("</mapping-configuration>");
		return xml.toString();
	}
	private void toXmlSqlContent(StringBuilder xml) {
		if(this.contents != null) {
			for (SqlContent content : this.contents) {
				xml.append("<content type=\"").append(content.getType().name()).append("\"");
				if(content.getDialectType() != null) {
					xml.append(" dialect=\"").append(content.getDialectType().name()).append("\"");
				}
				xml.append(">");
				xml.append(content.getContent());
				xml.append("</content>");
			}
		}
	}
}