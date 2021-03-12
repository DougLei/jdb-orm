package com.douglei.orm.sessionfactory.sessions.session.sqlquery.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.mapping.impl.sqlquery.metadata.SqlQueryMetadata;

/**
 * 
 * @author DougLei
 */
public class SqlQueryAssembler {
	private static final Logger logger = LoggerFactory.getLogger(SqlQueryAssembler.class);
	private SqlQueryMetadata metadata;
	private List<AbstractParameter> parameters;
	
	/**
	 * 
	 * @param metadata
	 * @param parameters
	 * @param container
	 */
	SqlQueryAssembler(SqlQueryMetadata metadata, List<AbstractParameter> parameters) {
		this.metadata = metadata;
		this.parameters = parameters;
	}

	/**
	 * 装配
	 * @return
	 */
	public SqlQueryEntity assembling() {
		SqlQueryEntity entity = new SqlQueryEntity(metadata.getSql());
		
		if(parameters != null) {
			for(int i=0;i<parameters.size();i++) {
				parameters.get(i).assembleSQL(entity, metadata);
				if(i < parameters.size()-1)
					entity.appendConditionSQLNext(parameters.get(i).next);
			}
		}
		
		// 验证是否没有传入必要的参数
		metadata.getParameterMap().values().forEach(parameter -> {
			if(parameter.isRequired() && !entity.existsInConditionSQL(parameter.getName()))
				throw new SqlQueryAssembleException("装配code为["+metadata.getCode()+"]的query-sql时, 未传入必要的参数["+parameter.getName()+"]");
		});
		
		logger.debug("QuerySqlEntity={}", entity);
		return entity;
	}
}