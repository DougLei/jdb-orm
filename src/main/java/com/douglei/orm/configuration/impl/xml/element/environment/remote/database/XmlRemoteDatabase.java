package com.douglei.orm.configuration.impl.xml.element.environment.remote.database;

import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.douglei.orm.configuration.SelfCheckingException;
import com.douglei.orm.configuration.environment.remote.database.RemoteDatabase;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class XmlRemoteDatabase extends RemoteDatabase{

	public XmlRemoteDatabase(Map<String, String> propertyMap, Element createSqlElement, Element dropSqlElement) {
		setPropertyMap(propertyMap);
		setSql(createSqlElement, dropSqlElement);
	}
	
	private void setPropertyMap(Map<String, String> propertyMap) {
		super.url = propertyMap.get("url");
		super.username = propertyMap.get("username");
		super.password = propertyMap.get("password");
		super.destroy = "true".equalsIgnoreCase(propertyMap.get("destroy"))?true:false;
	}
	
	private void setSql(Element createSqlElement, Element dropSqlElement) {
		String sql = null;
		addSql((byte) 1, createSqlElement.elements("sql"), sql);
		addSql((byte) 2, dropSqlElement.elements("sql"), sql);
	}
	
	private void addSql(byte sqlType, List<?> sqlElements, String sql) {
		if(sqlElements != null && sqlElements.size() > 0) {
			for (Object sqlElement : sqlElements) {
				sql = ((Element)sqlElement).getTextTrim();
				if(StringUtil.notEmpty(sql)) {
					if(sqlType == 1) {
						addCreateSql(sql);
					}else if(sqlType == 2) {
						addDropSql(sql);
					}
				}
			}
		}
	}
	
	@Override
	public void selfChecking() throws SelfCheckingException {
		if(StringUtil.isEmpty(super.url)) {
			throw new SelfCheckingException("<remoteDatabase>元素下, <property name=\"url\" />的值不能为空");
		}
		if(StringUtil.isEmpty(super.username)) {
			throw new SelfCheckingException("<remoteDatabase>元素下, <property name=\"username\" />的值不能为空");
		}
		if(StringUtil.isEmpty(super.password)) {
			throw new SelfCheckingException("<remoteDatabase>元素下, <property name=\"password\" />的值不能为空");
		}
		if(createSql == null) {
			throw new SelfCheckingException("<remoteDatabase> -> <createSql>元素下, <sql>元素的(text)内容, 至少有一个不能为空");
		}
		if(dropSql == null) {
			throw new SelfCheckingException("<remoteDatabase> -> <dropSql>元素下, <sql>元素的(text)内容, 至少有一个不能为空");
		}
	}
}