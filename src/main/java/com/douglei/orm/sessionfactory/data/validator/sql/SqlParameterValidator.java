package com.douglei.orm.sessionfactory.data.validator.sql;

import java.util.List;

import com.douglei.orm.core.metadata.sql.ContentMetadata;
import com.douglei.orm.core.metadata.sql.SqlMetadata;
import com.douglei.orm.core.metadata.validator.ValidationResult;

/**
 * sql参数验证器
 * @author DougLei
 */
public class SqlParameterValidator {
	
	List<ContentMetadata> contents;
	
	public SqlParameterValidator(SqlMetadata sqlMetadata, String name) {
		contents = sqlMetadata.getContents(name);
	}
	
	public ValidationResult setOriginObjectAndDoValidate(Object originObject) {
		// TODO 这里获取sql参数集合, 对数据进行验证
		
		
		return null;
	}
}
