package com.douglei.orm.mapping.metadata.validator;

import java.util.HashMap;
import java.util.Map;

import com.douglei.orm.mapping.metadata.validator.impl.NotBlankValidator;
import com.douglei.orm.mapping.metadata.validator.impl.RegexValidator;
import com.douglei.tools.utils.reflect.ClassLoadUtil;
import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * 验证器容器
 * @author DougLei
 */
public class ValidatorContainer {
	private static Map<String, Class<? extends Validator>> container = new HashMap<String, Class<? extends Validator>>(8);
	static {
		// _NullableValidator 和 _DataTypeValidator 两个验证器属于默认必须的, 所以这里不进行注册
		container.put("notBlank", NotBlankValidator.class);
		container.put("regex", RegexValidator.class);
	}
	
	/**
	 * 获取验证器实例
	 * @param name
	 * @param configValue
	 * @return
	 */
	public static Validator getValidatorInstance(String name, String configValue) {
		Class<? extends Validator> validatorClass = container.get(name);
		if(validatorClass == null) 
			container.put(name, (validatorClass = ClassLoadUtil.loadClass(name)));
		return ((Validator) ConstructorUtil.newInstance(validatorClass)).init(configValue);
	}
}
