package com.douglei.orm.core.dialect.datatype.handler.classtype;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.DataType;
import com.douglei.tools.utils.datatype.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDoubleDataTypeHandler extends ClassDataTypeHandler{
	
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
		if(value != null && ValidationUtil.isDouble(value.toString())) {
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
	public String doValidate(Object value, short length, short precision) {
		Double d = null;
		if(value.getClass() == double.class || value instanceof Double) {
			d = (Double) value;
		}else if(ValidationUtil.isDouble(value.toString())) {
			d = Double.parseDouble(value.toString());
		}else {
			return "数据值类型错误, 应为浮点型(double)";
		}
		
		String doubleString = d.toString();
		if(doubleString.length() - 1 > length) {
			return "数据值长度超长, 设置长度为" + length +", 实际长度为" + (doubleString.length() - 1);
		}
		
		short dotIndex = (short)doubleString.indexOf(".");
		if(dotIndex != -1) {
			dotIndex++;
			short pl = 0;
			while(dotIndex < doubleString.length()) {
				dotIndex++;
				pl++;
			}
			if(pl > precision) {
				return "数据值精度超长, 设置精度为" + precision +", 实际精度为" + pl;
			}
		}
		return null;
	}
}
