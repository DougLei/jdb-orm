package com.douglei.orm.dialect.datatype.handler.classtype;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.datatype.DataType;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDoubleDataTypeHandler extends ClassDataTypeHandler{
	private static final long serialVersionUID = -6271435058160379467L;

	@Override
	public String getCode() {
		return DataType.DOUBLE.getName();
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return new Class<?>[] {double.class, Double.class, BigDecimal.class};
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		if(value != null && VerifyTypeMatchUtil.isDouble(value.toString())) {
			if(value.getClass() == double.class || value instanceof Double) {
				preparedStatement.setDouble(parameterIndex, (double)value);
			}else if(value instanceof BigDecimal){
				preparedStatement.setBigDecimal(parameterIndex, (BigDecimal)value);
			}else {
				preparedStatement.setDouble(parameterIndex, Double.parseDouble(value.toString()));
			}
		}else {
			preparedStatement.setNull(parameterIndex, getSqlType());
		}
	}
	
	@Override
	public ValidationResult doValidate(String validateFieldName, Object value, short length, short precision) {
		Object o = null;
		if(value.getClass() == double.class || value instanceof Double || value instanceof BigDecimal) {
			o = value;
		}else if(VerifyTypeMatchUtil.isDouble(value.toString())) {
			o = Double.parseDouble(value.toString());
		}else {
			return new ValidationResult(validateFieldName, "数据值类型错误, 应为浮点型(double)", "jdb.data.validator.value.datatype.error.double");
		}
		
		String string = o.toString();
		if(length != DBDataType.NO_LIMIT && string.length() - 1 > length) 
			return new ValidationResult(validateFieldName, "数据值长度超长, 设置长度为%d, 实际长度为%d", "jdb.data.validator.value.digital.length.overlength", length, (string.length() - 1));
		
		if(precision != DBDataType.NO_LIMIT) {
			short dotIndex = (short)string.indexOf(".");
			if(dotIndex != -1) {
				dotIndex++;
				short pl = 0;
				while(dotIndex < string.length()) {
					dotIndex++;
					pl++;
				}
				if(pl > precision) 
					return new ValidationResult(validateFieldName, "数据值精度超长, 设置精度为%d, 实际精度为%d", "jdb.data.validator.value.digital.precision.overlength", precision, pl);
			}
		}
		return null;
	}
}
