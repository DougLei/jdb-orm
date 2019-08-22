package com.douglei.orm.core.dialect.datatype.handler.classtype;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.DataType;
import com.douglei.orm.core.metadata.validator.ValidatorResult;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDoubleDataTypeHandler extends ClassDataTypeHandler{
	private static final long serialVersionUID = -7794996918400919536L;

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
	public ValidatorResult doValidate(Object value, short length, short precision) {
		Object o = null;
		if(value.getClass() == double.class || value instanceof Double || value instanceof BigDecimal) {
			o = value;
		}else if(VerifyTypeMatchUtil.isDouble(value.toString())) {
			o = Double.parseDouble(value.toString());
		}else {
			return new ValidatorResult() {
				
				@Override
				public String getMessage() {
					return "数据值类型错误, 应为浮点型(double)";
				}
				
				@Override
				protected String getI18nCode() {
					return i18nCodePrefix + "value.datatype.error.double";
				}
			};
		}
		
		String string = o.toString();
		if(string.length() - 1 > length) {
			return new ValidatorResult() {
				
				@Override
				public String getMessage() {
					return "数据值长度超长, 设置长度为" + length +", 实际长度为" + (string.length() - 1);
				}
				
				@Override
				protected String getI18nCode() {
					return i18nCodePrefix + "value.digital.length.overdlength";
				}
			};
		}
		
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
				return new ValidatorResult() {
					
					@Override
					public String getMessage() {
						return "数据值精度超长, 设置精度为" + precision +", 实际精度为" + apl;
					}
					
					@Override
					protected String getI18nCode() {
						return i18nCodePrefix + "value.digital.precision.overdlength";
					}
				};
			}
		}
		return null;
	}
}
