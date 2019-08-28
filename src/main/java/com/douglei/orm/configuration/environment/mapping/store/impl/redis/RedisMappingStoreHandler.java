package com.douglei.orm.configuration.environment.mapping.store.impl.redis;

import java.util.Collection;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.store.NotExistsMappingException;
import com.douglei.orm.configuration.environment.mapping.store.RepeatedMappingException;
import com.douglei.orm.context.EnvironmentContext;
import com.douglei.tools.utils.Collections;
import com.douglei.tools.utils.serialize.JdkSerializeProcessor;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * 
 * @author DougLei
 */
class RedisMappingStoreHandler extends RedisHandler {
	private static final Logger logger = LoggerFactory.getLogger(RedisMappingStoreHandler.class);

	public void initializeStore(Jedis connection) {
		if(EnvironmentContext.getEnvironmentProperty().clearMappingOnStart()) {
			Set<String> keys = connection.keys(getPrefix() + "*");
			if(Collections.unEmpty(keys)) {
				removeMapping(keys, connection);
			}
		}
	}
	
	public void addMapping(Mapping mapping, Jedis connection) throws RepeatedMappingException{
		String code = getCode(mapping.getCode());
		if(mappingExists(code, connection)) {
			if(EnvironmentContext.getEnvironmentProperty().clearMappingOnStart()) {
				throw new RepeatedMappingException("已经存在相同code为["+mapping.getCode()+"]的映射对象: " + getMapping(mapping.getCode(), connection));
			}
			if(logger.isDebugEnabled()) {
				logger.debug("启动时, 已经存在相同code为[{}]的映射对象: ", mapping.getCode(), getMapping(mapping.getCode(), connection));
			}
			return;
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
			logger.debug("覆盖已经存在code为[{}]的映射对象: {}", mapping.getCode(), getMapping(mapping.getCode(), connection));
		}
		connection.set(code.getBytes(), JdkSerializeProcessor.serialize2ByteArray(mapping));
	}
	
	public void addOrCoverMapping(Collection<Mapping> mappings, Jedis connection) {
		Pipeline pipeline = connection.pipelined();
		mappings.forEach(mapping -> {
			if(logger.isDebugEnabled() && mappingExists(getCode(mapping.getCode()), connection)) {
				logger.debug("覆盖已经存在code为[{}]的映射对象: {}", mapping.getCode(), getMapping(mapping.getCode(), connection));
			}
			pipeline.set(getCode(mapping.getCode()).getBytes(), JdkSerializeProcessor.serialize2ByteArray(mapping));
		});
		pipeline.sync();
	}
	
	public Mapping removeMapping(String mappingCode, Jedis connection) throws NotExistsMappingException {
		String code = getCode(mappingCode);
		if(mappingExists(code, connection)) {
			Mapping mp = JdkSerializeProcessor.deserializeFromByteArray(Mapping.class, connection.get(code.getBytes()));
			connection.del(code);
			return mp;
		}
		throw new NotExistsMappingException("不存在code为["+mappingCode+"]的映射对象, 无法删除");
	}
	
	public void removeMapping(Collection<String> mappingCodes, Jedis connection) throws NotExistsMappingException {
		connection.del(getCodeByteArray(mappingCodes));
	}
	
	public Mapping getMapping(String mappingCode, Jedis connection) throws NotExistsMappingException {
		byte[] mpbyte = connection.get(getCode(mappingCode).getBytes());
		if(mpbyte == null || mpbyte.length == 0) {
			throw new NotExistsMappingException("不存在code为["+mappingCode+"]的映射对象");
		}
		return JdkSerializeProcessor.deserializeFromByteArray(Mapping.class, mpbyte);
	}
	
	public boolean mappingExists(String mappingCode, Jedis connection) {
		return connection.exists(mappingCode);
	}
	
	public void destroy(Jedis connection) throws DestroyException {
		initializeStore(connection);
	}
}
