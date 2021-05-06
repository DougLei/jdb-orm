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
		if(sqlMetadata.getValidatorsMap() == null)
			return;
		
		List<ContentMetadata> contents = getContentMetadatas(purpose, name, sqlMetadata.getContents());
		this.contentValidators = new ArrayList<ContentValidator>(contents.size());
		contents.forEach(content -> contentValidators.add(new ContentValidator(content, sqlMetadata.getSqlContentMap(), sqlMetadata.getValidatorsMap())));
	}
	
	/**
	 * 进行验证
	 * @param sqlParameter
	 * @return
	 */
	public ValidateFailResult validate(Object sqlParameter) {
		if(contentValidators == null)
			return null;
		
		for (ContentValidator validator : contentValidators) {
			ValidateFailResult failResult = validator.validate(sqlParameter);
			if(failResult != null) 
				return failResult;
		}
		return null;
	}
}