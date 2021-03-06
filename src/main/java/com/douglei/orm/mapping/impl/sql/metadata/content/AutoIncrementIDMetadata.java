package com.douglei.orm.mapping.impl.sql.metadata.content;

import com.douglei.orm.mapping.metadata.Metadata;
import com.douglei.tools.OgnlUtil;

/**
 * 
 * @author DougLei
 */
public class AutoIncrementIDMetadata implements Metadata{
	private String prefix; // 如果key为ognl表达式, 则这里记录前缀, 例如 user.id, 代表传入对象中user属性中的id
	private String key;
	private String sequence;
	
	public AutoIncrementIDMetadata(String key) {
		int dot = key.lastIndexOf(".");
		if(dot == -1) {
			this.key = key;
		}else {
			this.prefix = key.substring(0, dot);
			this.key = key.substring(dot+1);
		}
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	
	public String getKey() {
		return key;
	}
	public String getSequence() {
		return sequence;
	}

	/**
	 * 获取目标实例
	 * @param sqlParameter
	 * @return
	 */
	public Object getTargetObject(Object sqlParameter) {
		if(prefix == null) 
			return sqlParameter;
		return OgnlUtil.getObjectValue(prefix, sqlParameter);
	}
}
