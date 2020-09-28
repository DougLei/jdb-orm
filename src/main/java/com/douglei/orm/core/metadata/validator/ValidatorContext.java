package com.douglei.orm.core.metadata.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.douglei.tools.instances.file.reader.PropertiesReader;
import com.douglei.tools.instances.scanner.impl.ClassScanner;
import com.douglei.tools.utils.reflect.ClassLoadUtil;
import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
class ValidatorContext {
	private static Map<String, Class<? extends Validator>> validators = new HashMap<String, Class<? extends Validator>>(8);
	static {
		scanAndRegisterValidators();
		loadAndRegisterValidators();
	}
	
	/**
	 * 扫描并注册Validator
	 */
	private static void scanAndRegisterValidators() {
		ClassScanner scanner = new ClassScanner();
		List<String> validatorClasses = scanner.scan(Validator.class.getPackage().getName() +".impl");
		if(validatorClasses.size() > 0) 
			validatorClasses.forEach(clz -> registerValidatorByScan(ClassLoadUtil.loadClass(clz)));
		scanner.destroy();
	}

	/**
	 * 加载配置文件并注册Validator
	 */
	private static void loadAndRegisterValidators() {
		PropertiesReader reader = new PropertiesReader("jdb.validator.properties");
		if(reader.ready()) {
			reader.entrySet().forEach(validatorConfig -> {
				registerValidator(validatorConfig.getKey().toString(), ClassLoadUtil.loadClassWithStatic(validatorConfig.getValue().toString()));
			});
		}
	}
	
	private static void registerValidatorByScan(Class<? extends Validator> vc) {
		String name =vc.getSimpleName();
		if(name.endsWith("Validator")) {
			registerValidator(((char)(name.charAt(0)+ 32)) + name.substring(1, name.indexOf("Va")), vc);
		}
	}
	private static void registerValidator(String validatorName, Class<? extends Validator> vc) {
		validators.put(validatorName, vc);
	}
	
	/**
	 * 获取验证器实例
	 * @param validatorName
	 * @param configValue
	 * @return
	 */
	public static Validator getValidatorInstance(String validatorName, String configValue) {
		Class<? extends Validator> vc = validators.get(validatorName);
		if(vc == null) 
			throw new NullPointerException("不存在名为"+validatorName+"的验证器");
		return ((Validator) ConstructorUtil.newInstance(vc)).initialValidator(configValue);
	}
}
