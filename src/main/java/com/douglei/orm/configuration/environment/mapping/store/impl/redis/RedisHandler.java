package com.douglei.orm.configuration.environment.mapping.store.impl.redis;

import java.nio.charset.StandardCharsets;
import java.util.Collection;

import com.douglei.orm.context.EnvironmentContext;

/**
 * 
 * @author DougLei
 */
public abstract class RedisHandler{
	protected static final String prefix = "ORM:MP:";
	protected boolean storeMultiDataSource;// 是否存储多个数据源的映射, 如果是 则code需要前缀区分是哪个数据源, 即ORM:MP:dataSourceId:xxx.code
	
	// 获取byte数组的key
	protected byte[] getByteArrayKey(String code) {
		return code.getBytes(StandardCharsets.UTF_8);
	}
	
	// 获取映射code的byte二维数组
	protected byte[][] getCodeByteArray(Collection<String> codes){
		byte index = 0;
		byte[][] codeByteArray = new byte[codes.size()][];
		for (String code : codes) {
			codeByteArray[index++] = getByteArrayKey(code);
		}
		return codeByteArray;
	}
	
	protected String getPrefix() {
		if(storeMultiDataSource) {
			return prefix + EnvironmentContext.getEnvironmentProperty().getId() + ":";
		}
		return prefix;
	}
	protected String getCode(String code) {
		return getPrefix() + code;
	}
	
	public void setStoreMultiDataSource(boolean storeMultiDataSource) {
		this.storeMultiDataSource = storeMultiDataSource;
	}
	public boolean isStoreMultiDataSource() {
		return storeMultiDataSource;
	}
}
