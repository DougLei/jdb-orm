package com.douglei.orm.configuration.environment.mapping.store.impl.redis;

import java.nio.charset.StandardCharsets;
import java.util.Collection;

import com.douglei.orm.context.DBRunEnvironmentContext;

/**
 * 
 * @author DougLei
 */
public abstract class RedisHandler{
	protected static final String prefix = "MP:";
	protected boolean multiDataSource;// 是否是多个数据源, 如果包含多个数据源, 则code需要前缀区分是哪个数据源
	
	protected String getPrefix() {
		if(multiDataSource) {
			return prefix + DBRunEnvironmentContext.getEnvironmentProperty().getId() + ":";
		}
		return prefix;
	}
	protected String getCode(String code) {
		return getPrefix() + code;
	}
	
	/**
	 * 获取映射code的byte二维数组
	 * @param mappingCodes
	 * @return
	 */
	protected byte[][] getCodeByteArray(Collection<String> mappingCodes){
		byte index = 0;
		byte[][] codeByteArray = new byte[mappingCodes.size()][];
		for (String code : mappingCodes) {
			codeByteArray[index++] = getCode(code).getBytes(StandardCharsets.UTF_8);
		}
		return codeByteArray;
	}
	
	
	public void setMultiDataSource(boolean multiDataSource) {
		this.multiDataSource = multiDataSource;
	}
	public boolean isMultiDataSource() {
		return multiDataSource;
	}
}
