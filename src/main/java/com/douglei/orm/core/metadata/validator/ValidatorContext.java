package com.douglei.orm.core.metadata.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.douglei.tools.instances.file.resources.reader.PropertiesReader;
import com.douglei.tools.instances.scanner.ClassScanner;
import com.douglei.tools.utils.reflect.ClassLoadUtil;
import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
class ValidatorContext {
	private static final String validatorConfigFile = "jdb.validator.factories"; 
	private static final Map<String, Class<? extends Validator>> validators = new HashMap<String, Class<? extends Validator>>(8);
	static {
		scanAndRegisterValidators();
		loadAndRegisterValidators();
	}
	
	/**
	 * 扫描并注册Validator
	 */
	private static void scanAndRegisterValidators() {
		ClassScanner cs = new ClassScanner();
		List<String> validatorClasses = cs.scan(Validator.class.getPackage().getName() +".impl");
		if(validatorClasses.size() > 0) {
			validatorClasses.forEach(clz -> registerValidatorByScan(ClassLoadUtil.loadClass(clz)));
		}
		cs.destroy();
	}

	/**
	 * 加载配置文件并注册Validator
	 */
	private static void loadAndRegisterValidators() {
		PropertiesReader reader = new PropertiesReader(validatorConfigFile);
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
	 * @param validatorConfigValue
	 * @return
	 */
	public static Validator getValidatorInstance(String validatorName, String validatorConfigValue) {
		Class<? extends Validator> vc = validators.get(validatorName);
		if(vc == null) {
			try {
				vc = ClassLoadUtil.loadClassWithStatic(validatorName);
				registerValidator(validatorName, vc);
			} catch (Exception e) {
				throw new UnsupportValidatorException(validatorName);
			}
		}
		return ((Validator) ConstructorUtil.newInstance(vc)).initialValidator(validatorConfigValue);
	}
}
