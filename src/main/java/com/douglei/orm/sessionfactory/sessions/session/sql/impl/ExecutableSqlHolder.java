package com.douglei.orm.sessionfactory.sessions.session.sql.impl;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.SqlMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.SqlParameterNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.AutoIncrementIDMetadata;
import com.douglei.orm.sessionfactory.sessions.session.IExecutableSql;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class ExecutableSqlHolder extends SqlContentExtractor implements IExecutableSql{
	private int executableSqlCount; // 可执行的sql的数量
	private int currentIndex; // 当前执行的sql的下标, 从0开始
	private List<ExecutableSql> executableSqls;
	
	public ExecutableSqlHolder(PurposeEntity purposeEntity, SqlMetadata sqlMetadata, String name, Object sqlParameter) {
		List<ContentMetadata> contentMetadatas = getContentMetadatas(purposeEntity.getPurpose(), name, sqlMetadata.getContents());
		
		this.executableSqlCount = contentMetadatas.size();
		this.executableSqls = new ArrayList<ExecutableSql>(executableSqlCount);
		for (ContentMetadata contentMetadata : contentMetadatas) 
			this.executableSqls.add(new ExecutableSql(purposeEntity, contentMetadata, sqlParameter));
	}
	
	@Override
	public int executableSqlCount() {
		return executableSqlCount;
	}

	@Override
	public boolean next() {
		currentIndex++;
		return currentIndex < executableSqlCount;
	}
	
	/**
	 * 获取当前sql的名称
	 * @return
	 */
	public String getCurrentName() {
		return executableSqls.get(currentIndex).getName(); 
	}
	
	/**
	 * 获取当前sql的类型
	 * @return
	 */
	public ContentType getCurrentType() {
		return executableSqls.get(currentIndex).getType(); 
	}
	
	@Override
	public String getCurrentSql() {
		return executableSqls.get(currentIndex).getContent();
	}

	/**
	 * 获取当前sql参数集合
	 * @return
	 */
	public List<SqlParameterNode> getCurrentParameters() {
		return executableSqls.get(currentIndex).getParameters();
	}
	
	@Override
	public List<Object> getCurrentParameterValues() {
		return executableSqls.get(currentIndex).getParameterValues();
	}
	
	/**
	 * 获取当前sql自增主键值(配置)
	 * @return
	 */
	public AutoIncrementIDMetadata getCurrentAutoIncrementID() {
		return executableSqls.get(currentIndex).getAutoIncrementID();
	}
}
