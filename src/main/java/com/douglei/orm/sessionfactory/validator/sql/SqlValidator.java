package com.douglei.orm.sessionfactory.validator.sql;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.SqlMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentMetadata;
import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.SqlContentExtractor;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.Purpose;

/**
 * sql参数验证器
 * @author DougLei
 */
public class SqlValidator extends SqlContentExtractor{
	private List<ContentValidator> contentValidators;
	
	public SqlValidator(Purpose purpose, SqlMetadata sqlMetadata, String name) {
		List<ContentMetadata> contents = getContents(purpose, name, sqlMetadata.getContents());
		contentValidators = new ArrayList<ContentValidator>(contents.size());
		contents.forEach(content -> contentValidators.add(new ContentValidator(content)));
	}
	
	/**
	 * 进行验证
	 * @param sqlParameter
	 * @return
	 */
	public ValidateFailResult validate(Object sqlParameter) {
		ValidateFailResult result = null;
		for (ContentValidator cvalidator : contentValidators) {
			result = cvalidator.validate(sqlParameter);
			if(result != null) 
				break;
		}
		return result;
	}
}