package com.douglei.orm.core.metadata.sql;

import java.util.regex.Pattern;

import com.douglei.orm.configuration.JdbConfigurationBean;
import com.douglei.tools.utils.RegularExpressionUtil;

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
	public static final boolean sqlParameterSplitIncludeRegExKey;// sql参数的分隔符是否包含正则表达式的关键字
	
	static {
		prefix = JdbConfigurationBean.instance().getSqlParameterPrefix();
		suffix = JdbConfigurationBean.instance().getSqlParameterSuffix();
		prefixLength = (byte) prefix.length();
		
		prefixPatternString = RegularExpressionUtil.toRegularExpressionString(prefix);
		prefixPattern = Pattern.compile(prefixPatternString, Pattern.MULTILINE);
		if(prefix.equals(suffix)) {
			suffixPatternString = prefixPatternString;
			suffixPattern = prefixPattern;
		}else {
			suffixPatternString = RegularExpressionUtil.toRegularExpressionString(suffix);
			suffixPattern = Pattern.compile(suffixPatternString, Pattern.MULTILINE);
		}
		
		sqlParameterSplit = RegularExpressionUtil.toRegularExpressionString(JdbConfigurationBean.instance().getSqlParameterSplit());
		sqlParameterSplitIncludeRegExKey = RegularExpressionUtil.includeRegularExpressionKey(JdbConfigurationBean.instance().getSqlParameterSplit());
	}
}
