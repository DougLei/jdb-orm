package com.douglei.orm.core.metadata.sql;

import java.util.regex.Pattern;

import com.douglei.orm.configuration.JdbConfigurationBean;

/**
 * sql参数声明的配置类
 * @author DougLei
 */
public class SqlParameterDeclareConfiguration {
	private static final char[] pattern_keys = {'$', '(', ')', '*', '+', '.', '[', '?', '\\', '^', '{', '|'};
	
	public static final String prefix;
	public static final String suffix;
	public static final byte prefixLength;
	
	public static final String prefixPatternString;
	public static final String suffixPatternString;
	
	public static final Pattern prefixPattern;
	public static final Pattern suffixPattern;
	
	static {
		prefix = JdbConfigurationBean.instance().getSqlParameterPrefix();
		suffix = JdbConfigurationBean.instance().getSqlParameterSuffix();
		prefixLength = (byte) prefix.length();
		
		prefixPatternString = toPatternString(prefix);
		prefixPattern = Pattern.compile(prefixPatternString, Pattern.MULTILINE);
		if(prefix.equals(suffix)) {
			suffixPatternString = prefixPatternString;
			suffixPattern = prefixPattern;
		}else {
			suffixPatternString = toPatternString(suffix);
			suffixPattern = Pattern.compile(suffixPatternString, Pattern.MULTILINE);
		}
	}
	
	// 获取正则表达式, 如果有正则表达式的关键字, 则追加\进行转义
	private static String toPatternString(String pattern) {
		StringBuilder sp = new StringBuilder(pattern.length()*2);
		char s;
		for(byte i=0;i<pattern.length();i++) {
			s = pattern.charAt(i);
			for(char c : pattern_keys) {
				if(s == c) {
					sp.append('\\');
					break;
				}
			}
			sp.append(s);
		}
		if(sp.length() == pattern.length()) {
			return pattern;
		}
		return sp.toString();
	}
}
