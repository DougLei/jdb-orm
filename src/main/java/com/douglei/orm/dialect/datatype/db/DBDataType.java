package com.douglei.orm.dialect.datatype.db;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.Classification;
import com.douglei.orm.dialect.datatype.DataType;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;

/**
 * 数据库的数据类型
 * @author DougLei
 */
public abstract class DBDataType extends DataType implements Serializable{
	private static final int NO_LIMIT = -1; // 对长度(或精度)没有限制
	
	protected String name;// 类型的名称, 大写
	protected int sqlType;// @see java.sql.Types
	
	private int maxLength;// 最大长度
	private int maxPrecision;// 最大精度
	
	protected DBDataType(int sqlType) {
		this(sqlType, NO_LIMIT, NO_LIMIT);
	}
	protected DBDataType(int sqlType, int maxLength) {
		this(sqlType, maxLength, NO_LIMIT);
	}
	protected DBDataType(int sqlType, int maxLength, int maxPrecision) {
		this.name = getClass().getSimpleName().toUpperCase();
		this.sqlType = sqlType;
		this.maxLength = maxLength;
		this.maxPrecision = maxPrecision;
	}
	
	@Override
	public final Classification getClassification() {
		return Classification.DB;
	}
	
	@Override
	public final String getName() {
		return name;
	}
	
	/**
	 * 获取sql类型的值
	 * @return
	 */
	public final int getSqlType() {
		return sqlType;
	}
	
	/**
	 * 修正输入的长度值
	 * @param inputLength
	 * @return
	 */
	public final int correctInputLength(int inputLength) {
		if(this.maxLength == NO_LIMIT) 
			return NO_LIMIT;
		if(inputLength < 1 || inputLength > this.maxLength) 
			return this.maxLength;
		return inputLength;
	}
	
	/**
	 * 修正输入的精度值
	 * @param inputLength
	 * @param inputPrecision
	 * @return
	 */
	public final int correctInputPrecision(int inputLength, int inputPrecision) {
		if(this.maxPrecision == NO_LIMIT) 
			return NO_LIMIT;
		if(inputPrecision < 0 || inputPrecision > this.maxPrecision) 
			inputPrecision = this.maxPrecision;
		if(inputPrecision > inputLength) // 精度不能大于长度
			inputPrecision = inputLength;
		return inputPrecision;
	}
	
	/**
	 * 是否是字符类型
	 * @return
	 */
	public boolean isCharacterType() {
		return false;
	}
	
	/**
	 * 获取数据库数据类型对应的sql语句
	 * @param length
	 * @param precision
	 * @return
	 */
	public String getSqlStatement(int length, int precision) {
		return name;
	}
	
	/**
	 * 支持处理的Class数组
	 * @return 
	 */
	public Class<?>[] supportClasses() {
		return null;
	}
	
	/**
	 * 给 {@link PreparedStatement} 设置对应的参数值
	 * @param preparedStatement
	 * @param parameterIndex
	 * @param value
	 * @throws SQLException
	 */
	public final void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(value == null) {
			preparedStatement.setNull(parameterIndex, sqlType);
		}else {
			setValue_(preparedStatement, parameterIndex, value);
		}
	}
	
	/**
	 * 给 {@link PreparedStatement} 设置对应的参数值
	 * @param preparedStatement
	 * @param parameterIndex
	 * @param value 不为null
	 * @throws SQLException
	 */
	protected void setValue_(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		throw new IllegalArgumentException("["+getClass().getName()+"] 类型无法执行 setValue(PreparedStatement, int, Object)方法; 传入的value为 [" + value + "]");
	}
	
	/**
	 * 从 {@link ResultSet} 中获取对应的列值
	 * @param columnIndex
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public Object getValue(int columnIndex, ResultSet resultSet) throws SQLException{
		throw new IllegalArgumentException("["+getClass().getName()+"] 类型无法执行 getValue(int, ResultSet)方法");
	}
	
	/**
	 * 从 {@link CallableStatement} 中获取输出参数的值
	 * @param parameterIndex
	 * @param callableStatement
	 * @return
	 * @throws SQLException
	 */
	public Object getValue(int parameterIndex, CallableStatement callableStatement) throws SQLException{
		throw new IllegalArgumentException("["+getClass().getName()+"] 类型无法执行 getValue(int, CallableStatement)方法");
	}
	
	/**
	 * 验证指定属性名的属性值
	 * @param name
	 * @param value
	 * @param length
	 * @param precision
	 * @return 返回null表示验证通过
	 */
	public ValidationResult validate(String name, Object value, int length, int precision){
		throw new IllegalArgumentException("["+getClass().getName()+"] 类型无法执行 validate(String, Object, int, int)方法");
	}
	
	@Override
	public final String toString() {
		return name;
	}
}
