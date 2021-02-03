package com.douglei.orm.mapping.metadata.validator;

/**
 * 验证结果对象
 * @author DougLei
 */
public class ValidationResult {
	private int index; // 如果验证的是集合, 该字段用来记录验证数据在集合中的下标
	private String parameterName; // 记录验证失败的对象名称
	
	private String message; // 消息
	private String code; // 国际化编码, 根据编码获取对应语言的消息
	private Object[] params; // 消息中的参数数组
	
	/**
	 * 
	 * @param name 验证失败的参数名
	 * @param message 具体的消息
	 * @param code 国际化编码
	 * @param params 国际化编码需要的参数
	 */
	public ValidationResult(String parameterName, String message, String code, Object... params) {
		this.parameterName = parameterName;
		this.message = message;
		this.code = code;
		this.params = params;
	}
	
	/**
	 * 设置被验证参数所在集合的下标
	 * <p>
	 * 验证集合时使用
	 * @param index
	 */
	public final void setIndex(int index) {
		this.index = index;
	}
	
	/**
	 * 获取被验证的参数名
	 * @return
	 */
	public String getParameterName() {
		return parameterName;
	}
	/**
	 * 获取被验证参数所在集合的下标
	 *<p> 
	 * 验证集合时使用
	 * @return
	 */
	public final int getIndex() {x
		return index;
	}
	/**
	 * 获取验证结果的消息
	 * @return
	 */
	public String getMessage() {
		if(params.length > 0)
			return String.format(message, params);
		return message;
	}
	/**
	 * 获取验证结果国际化编码
	 * @return
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * 获取验证结果
	 * @return
	 */
	public Object[] getParams() {
		return params;
	}
}
