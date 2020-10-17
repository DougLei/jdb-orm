package com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.SqlMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.impl.sql.metadata.content.IncrementIdValueConfig;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterMetadata;
import com.douglei.orm.sessionfactory.sessions.session.execute.ExecuteHandler;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class SqlExecuteHandler extends SqlContentExtractor implements ExecuteHandler{
	private int executeSqlCount; // 要执行的sql的数量
	private int executeSqlIndex; // 执行的sql的下标, 从0开始
	private List<ExecuteSql> executeSqls;
	
	public SqlExecuteHandler(PurposeEntity purposeEntity, SqlMetadata sqlMetadata, String name, Object sqlParameter) {
		List<ContentMetadata> contents = getContents(purposeEntity.getPurpose(), name, sqlMetadata.getContents());
		
		executeSqlCount = contents.size();
		executeSqls = new ArrayList<ExecuteSql>(executeSqlCount);
		
		for (ContentMetadata content : contents) 
			executeSqls.add(new ExecuteSql(purposeEntity, content, sqlParameter));
	}
	
	@Override
	public int executeSqlCount() {
		return executeSqlCount;
	}

	@Override
	public boolean next() {
		executeSqlIndex++;
		return executeSqlIndex < executeSqlCount;
	}
	
	/**
	 * 获取当前sql的名称
	 * @return
	 */
	public String getCurrentName() {
		return executeSqls.get(executeSqlIndex).getName(); 
	}
	
	/**
	 * 获取当前sql的类型
	 * @return
	 */
	public ContentType getCurrentType() {
		return executeSqls.get(executeSqlIndex).getType(); 
	}
	
	@Override
	public String getCurrentSql() {
		return executeSqls.get(executeSqlIndex).getContent();
	}

	@Override
	public List<Object> getCurrentParameters() {
		return executeSqls.get(executeSqlIndex).getParameters();
	}
	
	/**
	 * 获取当前sql参数集合
	 * @return
	 */
	public List<SqlParameterMetadata> getCurrentSqlParameters() {
		return executeSqls.get(executeSqlIndex).getSqlParameters();
	}
	
	/**
	 * 获取当前sql自增主键值的配置
	 * @return
	 */
	public IncrementIdValueConfig getCurrentIncrementIdValueConfig() {
		return executeSqls.get(executeSqlIndex).getIncrementIdValueConfig();
	}
}
