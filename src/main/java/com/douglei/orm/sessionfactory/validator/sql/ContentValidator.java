package com.douglei.orm.sessionfactory.validator.sql;

import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.content.ContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;

/**
 * 
 * @author DougLei
 */
class ContentValidator {
	private List<SqlNode> rootSqlNodes;
	
	public ContentValidator(ContentMetadata contentMetadata) {
		rootSqlNodes = contentMetadata.getRootSqlNodes();
	}

	/**
	 * 验证sql参数
	 * @param sqlParameter
	 * @return
	 */
	public ValidationResult validate(Object sqlParameter) {
		ValidationResult result = null;
		for (SqlNode sqlNode : rootSqlNodes) {
			if(sqlNode.matching(sqlParameter)) {
				if((result = sqlNode.validateParameter(sqlParameter)) != null) 
					return result;
			}
		}
		return null;
	}
}
