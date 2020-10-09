package com.douglei.orm.mapping.impl.sql.metadata.parameter;

import java.io.Serializable;
import java.util.regex.Pattern;

import com.douglei.tools.utils.RegularExpressionUtil;

/**
 * sql参数相关的配置
 * @author DougLei
 */
public class SqlParameterConfigHolder implements Serializable{
	
	/**
	 * 前后缀一样
	 */
	public static final byte SAME = 1;
	/**
	 * 前后缀不一样
	 */
	public static final byte DIFFERENT = 2;
	/**
	 * 前缀中包含后缀
	 */
	public static final byte SUFFIX_IN_PREFIX = 3;
	/**
	 * 后缀中包含前缀
	 */
	public static final byte PREFIX_IN_SUFFIX = 4;
	
	/**
	 * 前后缀的关系
	 * @see SqlParameterConfigHolder#SAME
	 * @see SqlParameterConfigHolder#DIFFERENT
	 * @see SqlParameterConfigHolder#SUFFIX_IN_PREFIX
	 * @see SqlParameterConfigHolder#PREFIX_IN_SUFFIX
	 */
	private byte psRelation;
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
			this.psRelation = SAME;
		}else {
			this.suffix = RegularExpressionUtil.transferRegularExpressionKey(suffix);
			this.suffixPattern = Pattern.compile(this.suffix, Pattern.MULTILINE);
			this.psRelation = DIFFERENT;
			if(prefix.indexOf(suffix) != -1) {
				this.psRelation = SUFFIX_IN_PREFIX;
			}else if(suffix.indexOf(prefix) != -1) {
				this.psRelation = PREFIX_IN_SUFFIX;
			}
		}
		
		this.split = RegularExpressionUtil.transferRegularExpressionKey(split);
		this.splitIncludeRegExKey = this.split != split;
		this.defaultValueHandler = defaultValueHandler;
	}
	
	public int getPrefixLength() {
		return prefixLength;
	}
	public byte getPsRelation() {
		return psRelation;
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
