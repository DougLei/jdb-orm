package com.douglei.orm.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * order by子句解析器
 * @author DougLei
 */
class OrderByClauseResolver{
	private static final Logger logger = LoggerFactory.getLogger(OrderByClauseResolver.class);
	
	// 记录解析出的每个单词的长度
	private int wordLength;
	// 是否停止解析单词, 在分析出该单词对解析order by语句没有任何帮助的情况下, 使用该参数终止对单词的继续解析
	private boolean wordStopResolving;
	
	// 当前的下标
	private int index;
	
	// 每次获取的字符
	private char c;

	// 记录每次解析出单词的字符数组
	private char[] cs; 
	// 记录每次解析出单词后, 转换的关键字实例
	private KeyWord kw; 
	
	
	/**
	 * 从sql对象中提取order by子句
	 * @param statement
	 */
	public void extractOrderByClause(SqlStatement statement) {
		String sql = statement.getSql();
		index = sql.length()-1;
		
		for(;index>-1;index--) {
			c = sql.charAt(index);
			
			if(statement.isBlank(c)) {
				if(wordLength > 0) { // 证明有单词, 要判断它是否关键字
					if(KeyWord.lengthSatisfied(wordLength)) {
						cs = new char[wordLength];
						for(int i=0;i<wordLength;i++)
							cs[i] = sql.charAt(index+i+1);
						kw = KeyWord.toValue(cs);
						
						if(kw != null) {
							logger.debug("找到关键字: {}", kw);
							kw.extractOrderBy(sql, index, statement);
							break;
						}
					}
					wordLength = 0;
				}
				
				if(wordStopResolving)
					wordStopResolving = false;
			}else if(wordStopResolving){
				continue;
			}else {
				wordLength++;
				if(wordLength > 6){ // 当前单词的长度, 大于关键字中, 最长的单词(having)长度, 没有解析下去的意义
					wordLength = 0;
					wordStopResolving = true;
					continue;
				}
			}
		}
	}
}

/**
 * 关键词
 * 一旦出现这些关键词, 证明是没有order by信息的, 可以直接终止解析
 * @author DougLei
 */
enum KeyWord {
	BY{ // 可以是order by或group by
		
		@Override
		public void extractOrderBy(String sql, int index, SqlStatement statement) {
			char c;
			for(;index>-1;index--) {
				c = sql.charAt(index);
				if(!statement.isBlank(c)) {
					if(c == 'R' || c == 'r') { // order的最后一个字符
						index-=4;
						statement.setSql(sql.substring(0, index));
						statement.setOrderByClause(sql.substring(index));
					}
					break;
				}
			}
		} 
	}, 
	HAVING,
	WHERE,
	ON,
	FROM;

	private char[] lowerCases; // 小写的字符数组
	private char[] upperCases; // 大写的字符数组
	private KeyWord() {
		this.lowerCases = name().toLowerCase().toCharArray();
		this.upperCases = name().toCharArray();
	}
	
	/**
	 * 判断长度是否满足上面这些关键字的长度
	 * @param length
	 * @return
	 */
	public static boolean lengthSatisfied(int length) {
		return length == 6 || length == 2 || length == 5 || length == 4;
	}
	
	/**
	 * 根据字符数组, 得到对应的关键词
	 * @param wc
	 * @return
	 */
	public static KeyWord toValue(char[] wc) {
		KeyWord[] kws = KeyWord.values();
		for (KeyWord kw : kws) {
			if(kw.matching(wc)) {
				return kw;
			}
		}
		return null;
	}
	// 根据指定的字符数组, 与当前的关键字进行匹配
	private boolean matching(char[] cs) {
		if(cs.length == lowerCases.length) {
			for(int i=0;i<cs.length;i++) {
				if(cs[i] != lowerCases[i] && cs[i] != upperCases[i]) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 提取order by
	 * @param sql
	 * @param index
	 * @param statement
	 */
	public void extractOrderBy(String sql, int index, SqlStatement statement) {
	}
}