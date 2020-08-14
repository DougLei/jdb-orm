package com.douglei.orm.core.dialect.datatype.handler.classtype;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.DataType;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDoubleDataTypeHandler extends ClassDataTypeHandler{
	private static final long serialVersionUID = 5390641481543718833L;

	@Override
	public String getCode() {
		return DataType.DOUBLE.getName();
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return supportClasses;
	}
	private static final Class<?>[] supportClasses = {double.class, Double.class};

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
			return new ValidationResult(validateFieldName) {
				
				@Override
				public String getOriginMessage() {
					return "数据值类型错误, 应为浮点型(double)";
				}
				
				@Override
				public String getCode() {
					return codePrefix + "value.datatype.error.double";
				}
			};
		}
		
		String string = o.toString();
		if(length != DBDataType.NO_LIMIT && string.length() - 1 > length) {
			return new ValidationResult(validateFieldName) {
				
				@Override
				public String getOriginMessage() {
					return "数据值长度超长, 设置长度为%d, 实际长度为%d";
				}
				
				@Override
				public String getCode() {
					return codePrefix + "value.digital.length.overlength";
				}

				@Override
				public Object[] getParams() {
					return new Object[] { length, (string.length() - 1) };
				}
			};
		}
		
		if(precision != DBDataType.NO_LIMIT) {
			short dotIndex = (short)string.indexOf(".");
			if(dotIndex != -1) {
				dotIndex++;
				short pl = 0;
				while(dotIndex < string.length()) {
					dotIndex++;
					pl++;
				}
				if(pl > precision) {
					short apl = pl;
					return new ValidationResult(validateFieldName) {
						
						@Override
						public String getOriginMessage() {
							return "数据值精度超长, 设置精度为%d, 实际精度为%d";
						}
						
						@Override
						public String getCode() {
							return codePrefix + "value.digital.precision.overlength";
						}
						
						@Override
						public Object[] getParams() {
							return new Object[] { precision, apl };
						}
					};
				}
			}
		}
		return null;
	}
}
