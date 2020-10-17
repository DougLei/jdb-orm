package com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.content.ContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.sessionfactory.sessions.session.sql.Purpose;

/**
 * sql content元数据的提取器
 * @author DougLei
 */
public abstract class SqlContentExtractor {
	
	/**
	 * 根据purpose和name, 获取对应的sql内容元数据集合
	 * @param purposeEntity
	 * @param name
	 * @param contents
	 * @return
	 */
	protected List<ContentMetadata> getContents(Purpose purpose, String name, List<ContentMetadata> contents){
		List<ContentMetadata> list = getContents_(purpose, name, contents);
		if(list.isEmpty())
			throw new NullPointerException("不存在任何可以执行的sql语句");
		validate(purpose, contents);
		return list;
	}
	
	// 获取指定name的sql content; 
	// 若name为null, 则根据purpose决定获取content的方式: 
	// 	1. purpose为UPDATE/UNKNOW时, 返回所有sql content.
	// 	2. purpose为QUERY/PROCEDURE时, 返回第一个sql content; 
	private List<ContentMetadata> getContents_(Purpose purpose, String name, List<ContentMetadata> contents) {
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
}
