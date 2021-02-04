package com.douglei.orm.configuration.environment.mapping;

import java.util.regex.Pattern;

import com.douglei.tools.RegularExpressionUtil;

/**
 * 内置的sql映射配置
 * @author DougLei
 */
public class SqlMappingConfiguration {
	private SqlMappingParameterPSRelation relation; // 前缀后的关系
	
	private int parameterPrefixLength; // 前缀的长度; 因为在初始化时会对前缀中的正则关键字加反斜杠, 这会增加前缀的长度, 所以这里需要提前记录初始的长度
	private String parameterPrefix;
	private Pattern parameterPrefixPattern; // 前缀的正则表达式
	
	private String parameterSuffix;
	private Pattern parameterSuffixPattern; // 后缀的正则表达式
	
	private String parameterSplit;
	
	private SqlMappingParameterDefaultValueHandler parameterDefaultValueHandler;
	
	/**
	 * 
	 * @param parameterPrefix 声明参数时的前缀, 默认为#{
	 * @param parameterSuffix 声明参数时的后缀, 默认为}
	 * @param parameterSplit 参数中各个配置的分隔符, 默认为,
	 * @param parameterDefaultValueHandler 参数默认值处理器
	 */
	public SqlMappingConfiguration(String parameterPrefix, String parameterSuffix, String parameterSplit, SqlMappingParameterDefaultValueHandler parameterDefaultValueHandler) {
		this.parameterPrefixLength = parameterPrefix.length();
		
		this.parameterPrefix = RegularExpressionUtil.addBackslash4Key(parameterPrefix);
		this.parameterPrefixPattern = Pattern.compile(this.parameterPrefix, Pattern.MULTILINE);
		
		// 设置前后缀的关系, 并创建相关的实例
		if(parameterPrefix.equals(parameterSuffix)) { 
			this.relation = SqlMappingParameterPSRelation.SAME; 
			this.parameterSuffix = this.parameterPrefix;
			this.parameterSuffixPattern = this.parameterPrefixPattern;
		}else {
			this.relation = SqlMappingParameterPSRelation.DIFFERENT;
			this.parameterSuffix = RegularExpressionUtil.addBackslash4Key(parameterSuffix);
			this.parameterSuffixPattern = Pattern.compile(this.parameterSuffix, Pattern.MULTILINE);
			
			if(parameterPrefix.indexOf(parameterSuffix) != -1) {
				this.relation = SqlMappingParameterPSRelation.SUFFIX_IN_PREFIX;
			}else if(parameterSuffix.indexOf(parameterPrefix) != -1) {
				this.relation = SqlMappingParameterPSRelation.PREFIX_IN_SUFFIX;
			}
		}
		
		this.parameterSplit = RegularExpressionUtil.addBackslash4Key(parameterSplit);
		this.parameterDefaultValueHandler = parameterDefaultValueHandler;
	}
	
	
	
	/**
	 * 获取sql映射中声明参数时的前后缀关系
	 * @return
	 */
	public SqlMappingParameterPSRelation getRelationship() {
		return relation;
	}
	
	/**
	 * 获取声明参数时的前缀的长度
	 * @return
	 */
	public int getParameterPrefixLength() {
		return parameterPrefixLength;
	}
	/**
	 * 获取声明参数时的前缀
	 * @return
	 */
	public String getParameterPrefix() {
		return parameterPrefix;
	}
	/**
	 * 获取声明参数时的前缀正则表达式实例
	 * @return
	 */
	public Pattern getParameterPrefixPattern() {
		return parameterPrefixPattern;
	}

	/**
	 * 获取声明参数时的后缀
	 * @return
	 */
	public String getParameterSuffix() {
		return parameterSuffix;
	}
	/**
	 * 获取声明参数时的后缀正则表达式实例
	 * @return
	 */
	public Pattern getParameterSuffixPattern() {
		return parameterSuffixPattern;
	}

	/**
	 * 获取参数中各个配置的分隔符
	 * @return
	 */
	public String getParameterSplit() {
		return parameterSplit;
	}
	/**
	 * 获取参数默认值处理器
	 * @return
	 */
	public SqlMappingParameterDefaultValueHandler getParameterDefaultValueHandler() {
		return parameterDefaultValueHandler;
	}

	@Override
	public String toString() {
		return "[relation=" + relation + ", parameterPrefixLength=" + parameterPrefixLength
				+ ", parameterPrefix=" + parameterPrefix + ", parameterPrefixPattern=" + parameterPrefixPattern
				+ ", parameterSuffix=" + parameterSuffix + ", parameterSuffixPattern=" + parameterSuffixPattern
				+ ", parameterSplit=" + parameterSplit + ", parameterDefaultValueHandler="
				+ parameterDefaultValueHandler + "]";
	}
}
