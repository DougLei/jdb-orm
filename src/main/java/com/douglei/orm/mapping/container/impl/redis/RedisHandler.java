package com.douglei.orm.mapping.container.impl.redis;

import java.nio.charset.StandardCharsets;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.EnvironmentContext;

/**
 * 
 * @author DougLei
 */
public abstract class RedisHandler{
	private static final Logger logger = LoggerFactory.getLogger(RedisHandler.class);
	protected static final String prefix = "ORM:MP:";
	protected boolean storeMultiDataSource;// 是否存储多个数据源的映射, 如果是 则code需要前缀区分是哪个数据源, 即ORM:MP:dataSourceId:xxx.code
	
	// 获取byte数组的key
	protected byte[] getByteArrayKey(String code) {
		return code.getBytes(StandardCharsets.UTF_8);
	}
	
	// 获取映射code的byte二维数组
	protected byte[][] getCodeByteArray(Collection<String> codes){
		short index = 0;
		byte[][] codeByteArray = new byte[codes.size()][];
		for (String code : codes) {
			logger.debug("index is {}, code is {}", index, code);
			codeByteArray[index++] = getByteArrayKey(code);
		}
		return codeByteArray;
	}
	
	protected String getPrefix() {
		if(storeMultiDataSource) 
			return prefix + EnvironmentContext.getProperty().getId() + ":";
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
