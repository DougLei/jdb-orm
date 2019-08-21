package com.douglei.orm.configuration.impl.xml.element.environment.mapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.impl.xml.util.Dom4jElementUtil;
import com.douglei.orm.core.metadata.validator.ValidatorHandler;
import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.reflect.IntrospectorUtil;

/**
 * 映射的抽象父类
 * @author DougLei
 */
public abstract class XmlMapping implements Mapping{
	private static final long serialVersionUID = 2817014948073187035L;
	
	protected String configFileName;

	public XmlMapping(String configFileName) {
		this.configFileName = configFileName;
	}
	
	/**
	 * 获取配置的ValidatorHandler集合
	 * @param validatorsElement
	 * @return
	 */
	protected Map<String, ValidatorHandler> getValidatorHandlerMap(Element validatorsElement) {
		if(validatorsElement != null) {
			List<Element> validatorElements = Dom4jElementUtil.elements("validator", validatorsElement);
			if(validatorElements != null) {
				Map<String, ValidatorHandler> validatorMap = new HashMap<String, ValidatorHandler>(validatorElements.size());
				
				ValidatorHandler handler = null;
				for (Element ve : validatorElements) {
					handler = getValidatorHandler(ve);
					if(handler != null) {
						validatorMap.put(processValidatorNameValue(handler.getName()), handler);
					}
				}
				if(validatorMap.size() > 0) {
					return validatorMap;
				}
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
		if(validatorElement.attributeCount() > 0) {
			List<Attribute> attributes = validatorElement.attributes();
			Map<String, String> propertyMap = new HashMap<String, String>(attributes.size());
			attributes.forEach(attribute -> propertyMap.put(attribute.getName(), attribute.getValue()));
			if(StringUtil.notEmpty(propertyMap.get("name"))) {
				return (ValidatorHandler) IntrospectorUtil.setProperyValues(new ValidatorHandler(), propertyMap);
			}
		}
		return null;
	}
	
	/**
	 * 获取指定name的ValidatorHandler
	 * @param name
	 * @param validatorHandlerMap
	 * @return
	 */
	protected ValidatorHandler getValidatorHandler(String name, Map<String, ValidatorHandler> validatorHandlerMap) {
		if(validatorHandlerMap.isEmpty()) {
			return new ValidatorHandler();
		}
		ValidatorHandler handler = validatorHandlerMap.get(name);
		if(handler == null) {
			handler = new ValidatorHandler();
		}
		return handler;
	}
}
