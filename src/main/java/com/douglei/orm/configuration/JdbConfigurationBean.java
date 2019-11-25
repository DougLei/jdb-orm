package com.douglei.orm.configuration;

import com.douglei.tools.instances.file.resources.reader.PropertiesConfigurationBeanReader;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class JdbConfigurationBean {
	private JdbConfigurationBean() {}
	private static JdbConfigurationBean bean;
	
	static void initial() {
		if(bean == null) {
			bean = new JdbConfigurationBean();
			new PropertiesConfigurationBeanReader("jdb.config.properties", bean);
		}
	}
	public static final JdbConfigurationBean instance() {
		return bean;
	}
	
	private String sqlParameterPrefix;// 声明sql参数的前缀
	private String sqlParameterSuffix;// 声明sql参数的后缀
	private String sqlParameterSplit;// 声明sql参数时, 配置的参数间隔符

	
	public String getSqlParameterPrefix() {
		if(sqlParameterPrefix == null) {
			return "#{";
		}
		return sqlParameterPrefix;
	}
	public String getSqlParameterSuffix() {
		if(sqlParameterSuffix == null) {
			return "}";
		}
		return sqlParameterSuffix;
	}
	public String getSqlParameterSplit() {
		if(sqlParameterSplit == null) {
			return ",";
		}
		return sqlParameterSplit;
	}
	public void setSqlParameterPrefix(String sqlParameterPrefix) {
		if(StringUtil.notEmpty(sqlParameterPrefix)) {
			this.sqlParameterPrefix = sqlParameterPrefix.trim();
		}
	}
	public void setSqlParameterSuffix(String sqlParameterSuffix) {
		if(StringUtil.notEmpty(sqlParameterSuffix)) {
			this.sqlParameterSuffix = sqlParameterSuffix.trim();
		}
	}
	public void setSqlParameterSplit(String sqlParameterSplit) {
		if(StringUtil.notEmpty(sqlParameterSplit)) {
			this.sqlParameterSplit = sqlParameterSplit.trim();
		}
	}
}
