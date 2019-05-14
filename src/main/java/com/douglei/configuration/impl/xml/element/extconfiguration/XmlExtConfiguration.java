package com.douglei.configuration.impl.xml.element.extconfiguration;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.DestroyException;
import com.douglei.configuration.SelfCheckingException;
import com.douglei.configuration.extconfiguration.ExtConfiguration;
import com.douglei.configuration.extconfiguration.datatypehandler.ExtDataTypeHandler;
import com.douglei.configuration.impl.xml.element.extconfiguration.datatypehandler.XmlExtDataTypeHandler;
import com.douglei.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class XmlExtConfiguration implements ExtConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(XmlExtConfiguration.class);
	
	private List<ExtDataTypeHandler> extDataTypeHandlerList;
	
	public XmlExtConfiguration(List<?> extConfigurationElements) {
		logger.debug("开始处理<ext-configuration>元素");
		if(extConfigurationElements != null && extConfigurationElements.size() > 0) {
			if(extConfigurationElements.size() > 1) {
				throw new RepeatExtConfigurationElementException("<ext-configuration>元素最多只能配置一个");
			}
			
			Element extConfigurationElement = (Element) extConfigurationElements.get(0);
			setDataTypeHandlers(extConfigurationElement.elements("datatype-handlers"));
			
			// 如果有其他的ext, 再继续添加
		}
		logger.debug("处理<ext-configuration>元素结束");
	}
	
	private void setDataTypeHandlers(List<?> elements) {
		logger.debug("开始处理<datatype-handlers>元素");
		if(elements != null && elements.size() > 0) {
			if(elements.size() > 1) {
				throw new DataTypeHandlersElementException("<datatype-handlers>元素最多只能配置一个");
			}
			elements = ((Element) elements.get(0)).elements("datatype-handler");
			if(elements != null && elements.size() > 0) {
				extDataTypeHandlerList = new ArrayList<ExtDataTypeHandler>(elements.size());
				
				Element element = null;
				String clz = null;
				for (Object object : elements) {
					element = (Element) object;
					clz = element.attributeValue("class");
					if(StringUtil.isEmpty(clz)) {
						continue;
					}
					extDataTypeHandlerList.add(new XmlExtDataTypeHandler(element.attributeValue("dialect"), clz));
				}
			}
		}
		logger.debug("处理<datatype-handlers>元素结束");
	}

	
	@Override
	public List<ExtDataTypeHandler> getExtDataTypeHandlerList() {
		return extDataTypeHandlerList;
	}

	@Override
	public void doDestroy() throws DestroyException {
		if(extDataTypeHandlerList != null && extDataTypeHandlerList.size() > 0) {
			extDataTypeHandlerList.clear();
		}
	}

	@Override
	public void selfChecking() throws SelfCheckingException {
	}
}