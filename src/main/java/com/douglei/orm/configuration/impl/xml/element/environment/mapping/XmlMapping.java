package com.douglei.orm.configuration.impl.xml.element.environment.mapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.core.metadata.validator.ValidatorHandler;
import com.douglei.tools.utils.StringUtil;

/**
 * 映射的抽象父类
 * @author DougLei
 */
public abstract class XmlMapping implements Mapping{
	
	protected String configFileName;

	public XmlMapping(String configFileName) {
		this.configFileName = configFileName;
	}
	
	/**
	 * 获取配置的ValidatorHandler集合
	 * @param validatorsElement
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, ValidatorHandler> getValidatorHandlerMap(Element validatorsElement) {
		if(validatorsElement != null) {
			List<Element> validatorElements = validatorsElement.selectNodes("validator[@name]");
			if(com.douglei.tools.utils.Collections.unEmpty(validatorElements)) {
				Map<String, ValidatorHandler> validatorMap = new HashMap<String, ValidatorHandler>(validatorElements.size());
				
				ValidatorHandler handler = null;
				for (Element ve : validatorElements) {
					handler = getValidatorHandler(ve);
					validatorMap.put(processValidatorNameValue(handler.getName()), handler);
				}
				return validatorMap;
			}
		}
		return Collections.emptyMap();
	}
	
	/**
	 * 处理<validator name="">中的name属性的值
	 * @param nameValue
	 * @return
	 */
	protected String processValidatorNameValue(String nameValue) {
		return nameValue;
	}
	
	/**
	 * 获取validatorHandler实例
	 * @param validatorElement
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ValidatorHandler getValidatorHandler(Element validatorElement) {
		String name = validatorElement.attributeValue("name");
		if(StringUtil.notEmpty(name)) {
			ValidatorHandler handler = new ValidatorHandler(name, true);
			List<Attribute> attributes = validatorElement.attributes();
			if(attributes.size() > 1) {
				attributes.forEach(attribute -> {
					if(!"name".equals(attribute.getName())) {
						handler.addValidator(attribute.getName(), attribute.getValue());
					}
				});
			}
			return handler;
		}
		throw new NullPointerException("<validator>元素中的name属性值不能为空");
	}
	
	/**
	 * 获取指定name的ValidatorHandler
	 * @param name
	 * @param validatorHandlerMap
	 * @return
	 */
	protected ValidatorHandler getValidatorHandler(String name, Map<String, ValidatorHandler> validatorHandlerMap) {
		if(validatorHandlerMap.isEmpty()) {
			return new ValidatorHandler(name);
		}
		ValidatorHandler handler = validatorHandlerMap.get(name);
		if(handler == null) {
			handler = new ValidatorHandler(name);
		}
		return handler;
	}
}
