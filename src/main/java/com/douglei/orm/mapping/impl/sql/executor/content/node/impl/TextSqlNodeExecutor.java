package com.douglei.orm.mapping.impl.sql.executor.content.node.impl;

import java.util.List;
import java.util.Map;

import com.douglei.orm.mapping.impl.sql.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.executor.content.node.ExecutableSqlNode;
import com.douglei.orm.mapping.impl.sql.executor.content.node.SqlNodeExecutor;
import com.douglei.orm.mapping.impl.sql.metadata.content.SqlContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.ParameterNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.TextSqlNode;
import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.orm.mapping.validator.ValidatedData;
import com.douglei.orm.mapping.validator.Validator;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class TextSqlNodeExecutor implements SqlNodeExecutor<TextSqlNode> {

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.TEXT;
	}
	
	@Override
	public ExecutableSqlNode getExecutableSqlNode(PurposeEntity purposeEntity, TextSqlNode currentSqlNode, Map<String, SqlContentMetadata> sqlContentMetadataMap, Object sqlParameter, String previousAlias) {
		return new ExecutableSqlNode(purposeEntity, currentSqlNode.getSql(), currentSqlNode.getParameters(), sqlParameter, previousAlias);
	}

	@Override
	public ValidateFailResult validate(TextSqlNode currentSqlNode, Map<String, SqlContentMetadata> sqlContentMetadataMap, Map<String, List<Validator>> validatorsMap, Object sqlParameter, String previousAlias) {
		if(currentSqlNode.getParameters() != null) {
			ValidatedData data = new ValidatedData();
			for (ParameterNode parameter : currentSqlNode.getParameters()) {
				List<Validator> validators = validatorsMap.get(parameter.getName());
				if(validators == null)
					continue;
				
				data.setValue(ParameterNodeExecutor.SINGLETON.getValue(parameter, sqlParameter, previousAlias), parameter);
				for(Validator validator: validators) {
					ValidateFailResult failResult = validator.validate(data);
					if(failResult != null)
						return failResult;
				}
			}
		}
		return null;
	}
}
