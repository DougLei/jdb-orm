package com.douglei.orm.sessionfactory.data.validator.sql;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.core.metadata.sql.ContentMetadata;
import com.douglei.orm.core.metadata.sql.SqlMetadata;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.data.validator.DataValidator;

/**
 * sql参数验证器
 * @author DougLei
 */
public class SqlValidator implements DataValidator{
	
	List<ContentValidator> contentValidators;
	
	public SqlValidator(SqlMetadata sqlMetadata, String name) {
		List<ContentMetadata> contents = sqlMetadata.getContents(name);
		if(contents.size() == 0) {
			throw new NullPointerException("无法验证sql参数, 不存在任何可以执行的sql语句");
		}
		contentValidators = new ArrayList<ContentValidator>(contents.size());
		contents.forEach(content -> contentValidators.add(new ContentValidator(content)));
	}
	
	@Override
	public ValidationResult doValidate(Object sqlParameter) {
		ValidationResult result = null;
		for (ContentValidator cvalidator : contentValidators) {
			result = cvalidator.doValidate(sqlParameter);
			if(result != null) {
				break;
			}
		}
		return result;
	}
}
