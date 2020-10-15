package com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.SqlMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.impl.sql.metadata.content.IncrementIdValueConfig;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterMetadata;
import com.douglei.orm.sessionfactory.sessions.session.execute.ExecuteHandler;

/**
 * 
 * @author DougLei
 */
public class SqlExecuteHandler implements ExecuteHandler{
	private int executeSqlCount; // 要执行的sql的数量
	private int executeSqlIndex; // 执行的sql的下标, 从0开始
	private List<ExecuteSql> executeSqls;
	
	public SqlExecuteHandler(PurposeEntity purposeEntity, SqlMetadata sqlMetadata, String name, Object sqlParameter) {
		List<ContentMetadata> contents = getContents(purposeEntity.getPurpose(), name, sqlMetadata.getContents());
		if(contents.isEmpty())
			throw new NullPointerException("不存在任何可以执行的sql语句");
		validate(purposeEntity.getPurpose(), contents);
		
		executeSqlCount = contents.size();
		executeSqls = new ArrayList<ExecuteSql>(executeSqlCount);
		
		for (ContentMetadata content : contents) 
			executeSqls.add(new ExecuteSql(purposeEntity, content, sqlParameter));
	}
	
	// 获取指定name的sql content; 
	// 若name为null, 则根据purpose决定获取content的方式: 
	// 	1. purpose为UPDATE/UNKNOW时, 返回所有sql content.
	// 	2. purpose为QUERY/PROCEDURE时, 返回第一个sql content; 
	private List<ContentMetadata> getContents(Purpose purpose, String name, List<ContentMetadata> contents) {
		if(name == null) {
			if(purpose == Purpose.UPDATE || purpose == Purpose.UNKNOW)
				return contents;
			
			List<ContentMetadata> list = new ArrayList<ContentMetadata>(1);
			list.add(contents.get(0));
			return list;
		}
		
		for (ContentMetadata content : contents) {
			if(content.getName().equals(name)) {
				List<ContentMetadata> list = new ArrayList<ContentMetadata>(1);
				list.add(content);
				return list;
			}
		}
		return Collections.emptyList();
	}
	
	// 根据purpose(用途)去验证获取的contents是否有效, 如果无效则会抛出异常
	private void validate(Purpose purpose, List<ContentMetadata> contents) {
		switch(purpose) {
			case QUERY:
				if(contents.get(0).getType() != ContentType.SELECT)
					throw new IllegalArgumentException("name为["+contents.get(0).getName()+"]的sql, 不是["+ContentType.SELECT+"]类型, 无法执行查询");
				break;
			case PROCEDURE:
				if(contents.get(0).getType() != ContentType.PROCEDURE)
					throw new IllegalArgumentException("name为["+contents.get(0).getName()+"]的sql, 不是["+ContentType.PROCEDURE+"]类型, 无法执行");
				break;
			case UPDATE:
				for(ContentMetadata content : contents) {
					switch (content.getType()) {
						case SELECT:
						case PROCEDURE:
							throw new IllegalArgumentException("name为["+content.getName()+"]的sql, 不是["+ContentType.INSERT+","+ContentType.DELETE+","+ContentType.UPDATE+","+ContentType.DECLARE+"]类型, 无法执行");
						case INSERT:
						case DELETE:
						case UPDATE:
						case DECLARE:
							break;
					}
				}
				break;
			case UNKNOW:
				break;
		}
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
