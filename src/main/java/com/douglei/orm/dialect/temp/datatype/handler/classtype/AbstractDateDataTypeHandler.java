package com.douglei.orm.dialect.temp.datatype.handler.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import com.douglei.orm.dialect.datatype.DataType2;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.tools.utils.datatype.dateformat.DateFormatUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDateDataTypeHandler extends ClassDataTypeHandler{
	private static final long serialVersionUID = -2598112756930712297L;

	@Override
	public String getCode() {
		return DataType2.DATE.getName();
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return new Class<?>[] {Date.class};
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		if(DateFormatUtil.verifyIsDate(value)) {
			preparedStatement.setTimestamp(parameterIndex, DateFormatUtil.parseSqlTimestamp(value));
		} else {
			preparedStatement.setNull(parameterIndex, getSqlType());
		}
	}
	
	@Override
	public ValidationResult doValidate(String validateFieldName, Object value, short length, short precision) {
		if(value instanceof Date || DateFormatUtil.verifyIsDate(value)) {
			return null;
		}
		return new ValidationResult(validateFieldName, "数据值类型错误, 应为日期类型", "jdb.data.validator.value.datatype.error.date");
	}
}