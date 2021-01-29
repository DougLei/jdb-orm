package com.douglei.orm.util;

import com.douglei.tools.utils.StringUtil;

/**
 * 命名转换工具类
 * @author DougLei
 */
public class NameConvertUtil {
	
	/**
	 * 列名(多单词下划线分割) ==> 属性名(驼峰命名); 只处理单词间的下划线, 前后缀的下划线直接使用
	 * <p>
	 * 例: _USER_NAME_ ==> _userName_
	 * @param columnName
	 * @return
	 */
	public String column2Property(String columnName) {
		StringBuilder sb = new StringBuilder(columnName.length());
		
		int index = 0;
		while(columnName.charAt(index) == '_') 
			sb.append(columnName.charAt(index++));
		
		
		
		
		String[] columnInfo = StringUtil.trim_(columnName, '_');
		if(columnInfo[0] != null) 
			sb.append(columnInfo[0]);
		
		columnName = columnInfo[1].toLowerCase();
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
		
		if(columnInfo[2] != null) 
			sb.append(columnInfo[2]);
		return sb.toString();
	}
	
	/**
	 * 属性名(驼峰命名) ==> 列名(多单词下划线分割); 只处理单词间的下划线, 前后缀的下划线直接使用
	 * <p>
	 * 例: _userName_ ==> _USER_NAME_
	 * @param propertyName
	 * @return
	 */
	public String property2Column(String propertyName) {
		StringBuilder cn = new StringBuilder(propertyName.length() + 5);
		String[] pns = StringUtil.trim_(propertyName, '_');
		if(pns[0] != null) 
			cn.append(pns[0]);
		
		propertyName = pns[1];
		cn.append(propertyName.charAt(0));
		char tmp;
		for(byte i=1;i<propertyName.length();i++) {
			tmp = propertyName.charAt(i);
			if(tmp > 64 && tmp < 91) {
				cn.append("_");
				tmp += 32;
			}
			cn.append(tmp);
		}
		
		if(pns[2] != null) 
			cn.append(pns[2]);
		return cn.toString();
	}
}
