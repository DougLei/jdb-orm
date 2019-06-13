package com.douglei.orm.core.validate;

/**
 * 值的数据类型验证器
 * 验证值的数据类型, 数据长度, 数据精度
 * @author DougLei
 */
public interface ValueDataTypeValidator {
	
	/**
	 * 验证输入value的数据类型, 数据长度, 数据精度
	 * @param value
	 * @param length
	 * @param precision
	 * @return 返回null表示验证通过, 如果验证不通过, 返回相应的错误内容
	 */
	String doValidate(Object value, short length, short precision);
}
