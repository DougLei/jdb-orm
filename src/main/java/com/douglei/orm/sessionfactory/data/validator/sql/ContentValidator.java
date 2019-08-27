package com.douglei.orm.sessionfactory.data.validator.sql;

import com.douglei.orm.core.metadata.sql.ContentMetadata;
import com.douglei.orm.core.metadata.validator.ValidationResult;

/**
 * 
 * @author DougLei
 */
class ContentValidator {
	
	private ContentMetadata contentMetadata;
	
	public ContentValidator(ContentMetadata contentMetadata) {
		this.contentMetadata = contentMetadata;
	}

	/**
	 * 验证sql参数
	 * @param sqlParameter
	 * @return
	 */
	public ValidationResult doValidate(Object sqlParameter) {
		// TODO Auto-generated method stub
		return null;
	}
}
