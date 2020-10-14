package com.douglei.orm.mapping.metadata.parser;

import org.dom4j.Element;

import com.douglei.orm.EnvironmentContext;
import com.douglei.orm.mapping.impl.table.metadata.CreateMode;
import com.douglei.orm.mapping.metadata.Metadata;

/**
 * 元数据解析器
 * @author DougLei
 */
public interface MetadataParser<P, R extends Metadata> {
	
	/**
	 * 对metadata解析，如果解析成功，则返回对应的Metadata实例
	 * @param p
	 * @return
	 * @throws MetadataParseException
	 */
	R parse(P p) throws MetadataParseException;
	
	
	/**
	 * 获取createMode值
	 * @param createMode
	 * @param strict
	 * @return
	 */
	default CreateMode getCreateMode(Element element) {
		if(Boolean.parseBoolean(element.attributeValue("strict"))) {
			CreateMode createMode = CreateMode.toValue(element.attributeValue("createMode"));
			if(createMode == null)
				return CreateMode.defaultCreateMode();
			return createMode;
		}
		
		CreateMode createMode = EnvironmentContext.getProperty().getCreateMode();
		if(createMode == null) {
			createMode = CreateMode.toValue(element.attributeValue("createMode"));
			
			if(createMode == null)
				createMode = CreateMode.defaultCreateMode();
		}
		return createMode;
	}
}
