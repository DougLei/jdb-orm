package com.douglei.core.dialect.db.sql.entity;

import com.douglei.core.dialect.DialectType;
import com.douglei.core.metadata.sql.SqlContentType;

/**
 * 
 * @author DougLei
 */
public class SqlContent {
	private DialectType dialectType;
	private SqlContentType contentType;
	private String content;
	
	public SqlContent(SqlContentType contentType, String content) {
		this(null, contentType, content);
	}
	public SqlContent(DialectType dialectType, SqlContentType contentType, String content) {
		this.dialectType = dialectType;
		this.contentType = contentType;
		this.content = content;
	}
	
	public SqlContentType getContentType() {
		return contentType;
	}
	public DialectType getDialectType() {
		return dialectType;
	}
	public String getContent() {
		return content;
	}
}
