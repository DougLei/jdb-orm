package com.douglei.orm.sessionfactory.validator.sql;

import java.util.List;
import java.util.Map;

import com.douglei.orm.mapping.impl.sql.SqlNodeContainer;
import com.douglei.orm.mapping.impl.sql.executor.content.node.SqlNodeExecutor;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.SqlContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.orm.mapping.validator.Validator;

/**
 * 
 * @author DougLei
 */
class ContentValidator {
	private List<SqlNode> sqlNodes;
	private Map<String, SqlContentMetadata> sqlContentMetadataMap;
	private Map<String, List<Validator>> validatorsMap;
	
	public ContentValidator(ContentMetadata contentMetadata, Map<String, SqlContentMetadata> sqlContentMetadataMap, Map<String, List<Validator>> validatorsMap) {
		this.sqlNodes = contentMetadata.getSqlNodes();
		this.sqlContentMetadataMap = sqlContentMetadataMap;
		this.validatorsMap = validatorsMap;
	}

	/**
	 * 验证sql参数
	 * @param sqlParameter
	 * @return
	 */
	public ValidateFailResult validate(Object sqlParameter) {
		for (SqlNode sqlNode : sqlNodes) {
			SqlNodeExecutor<SqlNode> executor = SqlNodeContainer.getExecutor(sqlNode);
			if(executor.matching(sqlNode, sqlParameter)) {
				ValidateFailResult failResult = executor.validate(sqlNode, sqlContentMetadataMap, validatorsMap, sqlParameter);
				if(failResult != null) 
					return failResult;
			}
		}
		return null;
	}
}
