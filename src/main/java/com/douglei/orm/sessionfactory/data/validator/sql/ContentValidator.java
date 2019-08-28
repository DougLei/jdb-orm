package com.douglei.orm.sessionfactory.data.validator.sql;

import java.util.List;

import com.douglei.orm.core.metadata.sql.ContentMetadata;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;
import com.douglei.orm.core.metadata.validator.ValidationResult;

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
	public ValidationResult doValidate(Object sqlParameter) {
		ValidationResult result = null;
		for (SqlNode sqlNode : rootSqlNodes) {
			if(sqlNode.matching(sqlParameter)) {
				if((result = sqlNode.validateParameter(sqlParameter)) != null) {
					return result;
				}
			}
		}
		return null;
	}
}
