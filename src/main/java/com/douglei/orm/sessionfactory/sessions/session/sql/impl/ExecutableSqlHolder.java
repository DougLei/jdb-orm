package com.douglei.orm.sessionfactory.sessions.session.sql.impl;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.SqlMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.impl.sql.metadata.content.IncrementIdValueConfig;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterMetadata;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;

/**
 * 可执行sql的持有器
 * @author DougLei
 */
public class ExecutableSqlHolder extends SqlContentExtractor implements com.douglei.orm.sessionfactory.sessions.session.ExecutableSqlHolder{
	private int executableSqlCount; // 可执行的sql的数量
	private int currentIndex; // 当前执行的sql的下标, 从0开始
	private List<ExecutableSql> executableSqls;
	
	public ExecutableSqlHolder(PurposeEntity purposeEntity, SqlMetadata sqlMetadata, String name, Object sqlParameter) {
		List<ContentMetadata> contents = getContents(purposeEntity.getPurpose(), name, sqlMetadata.getContents());
		
		executableSqls = new ArrayList<ExecutableSql>(executableSqlCount = contents.size());
		for (ContentMetadata content : contents) 
			executableSqls.add(new ExecutableSql(purposeEntity, content, sqlParameter));
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
	public List<SqlParameterMetadata> getCurrentParameters() {
		return executableSqls.get(currentIndex).getParameters();
	}
	
	@Override
	public List<Object> getCurrentParameterValues() {
		return executableSqls.get(currentIndex).getParameterValues();
	}
	
	/**
	 * 获取当前sql自增主键值的配置
	 * @return
	 */
	public IncrementIdValueConfig getCurrentIncrementIdValueConfig() {
		return executableSqls.get(currentIndex).getIncrementIdValueConfig();
	}
}
