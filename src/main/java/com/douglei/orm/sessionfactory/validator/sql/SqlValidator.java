package com.douglei.orm.sessionfactory.validator.sql;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.SqlMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentMetadata;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;

/**
 * sql参数验证器
 * @author DougLei
 */
public class SqlValidator {
	private List<ContentValidator> contentValidators;
	
	public SqlValidator(SqlMetadata sqlMetadata, String name) {
		List<ContentMetadata> contents = null;
		if(name == null) {
			contents = sqlMetadata.getContents();
		}else {
			contents = new ArrayList<ContentMetadata>(1);
			for(ContentMetadata content : sqlMetadata.getContents()) {
				if(content.getName().equals(name)) {
					contents.add(content);
					break;
				}
			}
		}
		if(contents.isEmpty()) 
			throw new NullPointerException("无法验证sql参数, 不存在任何可以执行的sql语句");
		
		contentValidators = new ArrayList<ContentValidator>(contents.size());
		contents.forEach(content -> contentValidators.add(new ContentValidator(content)));
	}
	
	public ValidationResult validate(Object sqlParameter) {
		ValidationResult result = null;
		for (ContentValidator cvalidator : contentValidators) {
			result = cvalidator.validate(sqlParameter);
			if(result != null) 
				break;
		}
		return result;
	}
}
