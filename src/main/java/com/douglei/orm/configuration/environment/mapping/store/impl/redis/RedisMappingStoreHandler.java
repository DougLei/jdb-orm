package com.douglei.orm.configuration.environment.mapping.store.impl.redis;

import java.util.Collection;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.store.NotExistsMappingException;
import com.douglei.orm.configuration.environment.mapping.store.RepeatedMappingException;
import com.douglei.orm.context.DBRunEnvironmentContext;
import com.douglei.tools.utils.Collections;
import com.douglei.tools.utils.serialize.JdkSerializeProcessor;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * 
 * @author DougLei
 */
class RedisMappingStoreHandler {
	private static final Logger logger = LoggerFactory.getLogger(RedisMappingStoreHandler.class);
	private static final String prefix = "MP:";
	private boolean multiDataSource;// 是否是多个数据源, 如果包含多个数据源, 则code需要前缀区分是哪个数据源
	
	private String getPrefix() {
		if(multiDataSource) {
			return prefix + DBRunEnvironmentContext.getEnvironmentProperty().getId() + ":";
		}
		return prefix;
	}
	private String getCode(String code) {
		return getPrefix() + code;
	}
	
	public void initializeStore(Jedis connection) {
		Set<String> keys = connection.keys(getPrefix() + "*");
		if(Collections.unEmpty(keys)) {
			removeMapping(keys, connection);
		}
	}
	
	public void addMapping(Mapping mapping, Jedis connection) throws RepeatedMappingException{
		String code = getCode(mapping.getCode());
		if(mappingExists(code, connection)) {
			throw new RepeatedMappingException("已经存在相同code为["+mapping.getCode()+"]的映射对象: " + JdkSerializeProcessor.deserializeFromByteArray(Mapping.class, connection.get(code.getBytes())));
		}
		connection.set(code.getBytes(), JdkSerializeProcessor.serialize2ByteArray(mapping));
	}
	
	public void addMapping(Collection<Mapping> mappings, Jedis connection) throws RepeatedMappingException {
		Pipeline pipeline = connection.pipelined();
		mappings.forEach(mapping -> pipeline.set(getCode(mapping.getCode()).getBytes(), JdkSerializeProcessor.serialize2ByteArray(mapping)));
		pipeline.sync();
	}

	public void addOrCoverMapping(Mapping mapping, Jedis connection) {
		String code = getCode(mapping.getCode());
		if(logger.isDebugEnabled() && mappingExists(code, connection)) {
			logger.debug("覆盖已经存在code为[{}]的映射对象: {}", mapping.getCode(), JdkSerializeProcessor.deserializeFromByteArray(Mapping.class, connection.get(code.getBytes())));
		}
		connection.set(code.getBytes(), JdkSerializeProcessor.serialize2ByteArray(mapping));
	}
	
	public void addOrCoverMapping(Collection<Mapping> mappings, Jedis connection) {
		for (Mapping mapping : mappings) {
			addOrCoverMapping(mapping, connection);
		}
	}
	
	private Mapping removeMapping(String mappingCode, Jedis connection, boolean returnRemoved) throws NotExistsMappingException {
		if(mappingExists(mappingCode, connection)) {
			mappingCode = getCode(mappingCode);
			Mapping mp = null;
			if(returnRemoved) {
				mp = JdkSerializeProcessor.deserializeFromByteArray(Mapping.class, connection.get(mappingCode.getBytes()));
			}
			connection.del(mappingCode);
			return mp;
		}
		throw new NotExistsMappingException("不存在code为["+mappingCode+"]的映射对象, 无法删除");
	}

	public Mapping removeMapping(String mappingCode, Jedis connection) throws NotExistsMappingException {
		return removeMapping(mappingCode, connection, true);
	}
	
	public void removeMapping(Collection<String> mappingCodes, Jedis connection) throws NotExistsMappingException {
		Pipeline pipeline = connection.pipelined();
		mappingCodes.forEach(mappingCode -> pipeline.del(getCode(mappingCode)));
		pipeline.sync();
	}
	
	public Mapping getMapping(String mappingCode, Jedis connection) throws NotExistsMappingException {
		byte[] mpbyte = connection.get(getCode(mappingCode).getBytes());
		if(mpbyte == null || mpbyte.length == 0) {
			throw new NotExistsMappingException("不存在code为["+mappingCode+"]的映射对象");
		}
		return JdkSerializeProcessor.deserializeFromByteArray(Mapping.class, mpbyte);
	}
	
	public boolean mappingExists(String mappingCode, Jedis connection) {
		return connection.exists(getCode(mappingCode));
	}
	
	public void destroy(Jedis connection) throws DestroyException {
		initializeStore(connection);
	}
	
	public void setMultiDataSource(boolean multiDataSource) {
		this.multiDataSource = multiDataSource;
	}
}
