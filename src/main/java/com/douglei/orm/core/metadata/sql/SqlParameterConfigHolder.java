package com.douglei.orm.core.metadata.sql;

import java.util.regex.Pattern;

import com.douglei.tools.utils.RegularExpressionUtil;

/**
 * sql参数相关的配置
 * @author DougLei
 */
public class SqlParameterConfigHolder {
	private int prefixLength;
	
	private String prefix;
	private Pattern prefixPattern;
	private String suffix;
	private Pattern suffixPattern;
	
	private String split;
	private boolean splitIncludeRegExKey; // 分隔符是否包含正则表达式的关键字
	
	private DefaultValueHandler defaultValueHandler;
	
	public SqlParameterConfigHolder(String prefix, String suffix, String split, DefaultValueHandler defaultValueHandler) {
		this.prefixLength = prefix.length();
		
		this.prefix = RegularExpressionUtil.transferRegularExpressionKey(prefix);
		this.prefixPattern = Pattern.compile(this.prefix, Pattern.MULTILINE);
		if(prefix.equals(suffix)) {
			this.suffix = this.prefix;
			this.suffixPattern = this.prefixPattern;
		}else {
			this.suffix = RegularExpressionUtil.transferRegularExpressionKey(suffix);
			this.suffixPattern = Pattern.compile(this.suffix, Pattern.MULTILINE);
		}
		
		this.split = RegularExpressionUtil.transferRegularExpressionKey(split);
		this.splitIncludeRegExKey = this.split != split;
		this.defaultValueHandler = defaultValueHandler;
	}

	public int getPrefixLength() {
		return prefixLength;
	}
	public String getPrefix() {
		return prefix;
	}
	public Pattern getPrefixPattern() {
		return prefixPattern;
	}
	public String getSuffix() {
		return suffix;
	}
	public Pattern getSuffixPattern() {
		return suffixPattern;
	}
	public String getSplit() {
		return split;
	}
	public DefaultValueHandler getDefaultValueHandler() {
		return defaultValueHandler;
	}
	public boolean splitIncludeRegExKey() {
		return splitIncludeRegExKey;
	}
}
