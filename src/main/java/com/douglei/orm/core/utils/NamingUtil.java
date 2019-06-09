package com.douglei.orm.core.utils;

import com.douglei.tools.utils.StringUtil;

/**
 * 命名工具类
 * @author DougLei
 */
public class NamingUtil {
	
	/**
	 * <pre>
	 * 	列名转换为属性名
	 * 		    列名:	多个单词，用_分割
	 * 		属性名:	首字母小写、多个单词，后面每个单词首字母大写
	 * </pre>
	 * @param columnName
	 * @return
	 */
	public static String columnName2PropertyName(String columnName){
		StringBuilder sb = new StringBuilder(columnName.length());
		String[] columnInfo = StringUtil.trimUnderline_(columnName);
		if(columnInfo[0] != null) {
			sb.append(columnInfo[0]);
		}
		
		columnName = columnInfo[2].toLowerCase();
		String[] words = columnName.split("_");
		sb.append(words[0]);
		
		String firstWord = null;
		int len = words.length;
		if(len > 1){
			for(int i = 1; i<len; i++){
				if(words[i].equals("")){// 证明是_
					sb.append("_");
				}else {
					firstWord = words[i].substring(0,1);
					sb.append(words[i].replaceFirst(firstWord, firstWord.toUpperCase()));
				}
			}
		}
		
		if(columnInfo[1] != null) {
			sb.append(columnInfo[1]);
		}
		return sb.toString();
	}
}