package com.douglei.orm.core.metadata.sql;

import java.util.regex.Pattern;

import com.douglei.orm.configuration.JdbConfigurationBean;
import com.douglei.tools.utils.StringUtil;

/**
 * sql参数声明的配置类
 * @author DougLei
 */
public class SqlParameterDeclareConfiguration {
	public static final String prefix;
	public static final String suffix;
	public static final byte prefixLength;
	
	public static final String prefixPatternString;
	public static final String suffixPatternString;
	
	public static final Pattern prefixPattern;
	public static final Pattern suffixPattern;
	
	public static final String sqlParameterSplit;
	
	static {
		prefix = JdbConfigurationBean.instance().getSqlParameterPrefix();
		suffix = JdbConfigurationBean.instance().getSqlParameterSuffix();
		prefixLength = (byte) prefix.length();
		
		prefixPatternString = StringUtil.toPatternString(prefix);
		prefixPattern = Pattern.compile(prefixPatternString, Pattern.MULTILINE);
		if(prefix.equals(suffix)) {
			suffixPatternString = prefixPatternString;
			suffixPattern = prefixPattern;
		}else {
			suffixPatternString = StringUtil.toPatternString(suffix);
			suffixPattern = Pattern.compile(suffixPatternString, Pattern.MULTILINE);
		}
		
		sqlParameterSplit = StringUtil.toPatternString(JdbConfigurationBean.instance().getSqlParameterSplit());
	}
}
