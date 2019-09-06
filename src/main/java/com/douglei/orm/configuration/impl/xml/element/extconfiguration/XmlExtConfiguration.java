package com.douglei.orm.configuration.impl.xml.element.extconfiguration;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.ext.configuration.ExtConfiguration;
import com.douglei.orm.configuration.ext.configuration.datatypehandler.ExtDataTypeHandler;
import com.douglei.orm.configuration.impl.xml.element.extconfiguration.datatypehandler.XmlExtDataTypeHandler;
import com.douglei.orm.configuration.impl.xml.util.Dom4jElementUtil;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class XmlExtConfiguration implements ExtConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(XmlExtConfiguration.class);
	
	private List<ExtDataTypeHandler> extDataTypeHandlerList;
	
	public XmlExtConfiguration(Element extConfigurationElement) {
		logger.debug("开始处理<extConfiguration>元素");
		if(extConfigurationElement != null) {
			setDataTypeHandlers(Dom4jElementUtil.element("datatypeHandlers", extConfigurationElement));
			
			// TODO 如果以后再有其他的ext, 继续添加
		}
		logger.debug("处理<extConfiguration>元素结束");
	}
	
	private void setDataTypeHandlers(Element datatypeHandlersElement) {
		logger.debug("开始处理<datatypeHandlers>元素");
		if(datatypeHandlersElement != null) {
			List<Element> elements = Dom4jElementUtil.elements("datatypeHandler", datatypeHandlersElement);
			if(elements != null) {
				extDataTypeHandlerList = new ArrayList<ExtDataTypeHandler>(elements.size());
				
				String clz = null;
				for (Element element : elements) {
					clz = element.attributeValue("class");
					if(StringUtil.isEmpty(clz)) {
						continue;
					}
					extDataTypeHandlerList.add(new XmlExtDataTypeHandler(clz));
				}
			}
		}
		logger.debug("处理<datatypeHandlers>元素结束");
	}

	
	@Override
	public List<ExtDataTypeHandler> getExtDataTypeHandlerList() {
		return extDataTypeHandlerList;
	}

	@Override
	public void destroy() throws DestroyException {
		if(logger.isDebugEnabled()) logger.debug("{} 开始 destroy", getClass().getName());
		if(extDataTypeHandlerList != null && extDataTypeHandlerList.size() > 0) {
			extDataTypeHandlerList.clear();
		}
		if(logger.isDebugEnabled()) logger.debug("{} 结束 destroy", getClass().getName());
	}
}
