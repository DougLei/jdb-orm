package com.douglei.orm.sessionfactory.validator.sql;

import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.content.ContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.validator.ValidationResult;

/**
 * 
 * @author DougLei
 */
class ContentValidator {
	private List<SqlNode> sqlNodes;
	
	public ContentValidator(ContentMetadata contentMetadata) {
		sqlNodes = contentMetadata.getSqlNodes();
	}

	/**
	 * 验证sql参数
	 * @param sqlParameter
	 * @return
	 */
	public ValidationResult validate(Object sqlParameter) {
		ValidationResult result = null;
		for (SqlNode sqlNode : sqlNodes) {
			if(sqlNode.matching(sqlParameter)) {
				if((result = sqlNode.validateParameter(sqlParameter)) != null) 
					return result;
			}
		}
		return null;
	}
}
