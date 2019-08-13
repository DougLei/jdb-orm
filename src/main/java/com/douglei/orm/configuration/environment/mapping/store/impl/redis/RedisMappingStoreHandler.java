package com.douglei.orm.configuration.environment.mapping.store.impl.redis;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.store.NotExistsMappingException;
import com.douglei.orm.configuration.environment.mapping.store.RepeatedMappingException;
import com.douglei.tools.utils.serialize.JdkSerializeProcessor;

import redis.clients.jedis.Jedis;

/**
 * 
 * @author DougLei
 */
public class RedisMappingStoreHandler {
	private static final Logger logger = LoggerFactory.getLogger(RedisMappingStoreHandler.class);
	
	public void initializeStore(Jedis connection) {
		// TODO
	}
	
	public void addMapping(Mapping mapping, Jedis connection) throws RepeatedMappingException{
		String code = mapping.getCode();
		if(mappingExists(code, connection)) {
			throw new RepeatedMappingException("已经存在相同code为["+code+"]的映射对象: " + JdkSerializeProcessor.deserializeFromByteArray(Mapping.class, connection.get(code.getBytes())));
		}
		connection.set(code.getBytes(), JdkSerializeProcessor.serialize2ByteArray(mapping));
	}
	
	public void addMapping(Collection<Mapping> mappings, Jedis connection) throws RepeatedMappingException {
		for (Mapping mapping : mappings) {
			addMapping(mapping, connection);
		}
	}

	public void addOrCoverMapping(Mapping mapping, Jedis connection) {
		String code = mapping.getCode();
		if(logger.isDebugEnabled() && mappingExists(code, connection)) {
			logger.debug("覆盖已经存在code为[{}]的映射对象: {}", code, JdkSerializeProcessor.deserializeFromByteArray(Mapping.class, connection.get(code.getBytes())));
		}
		connection.set(code.getBytes(), JdkSerializeProcessor.serialize2ByteArray(mapping));
	}
	
	public void addOrCoverMapping(Collection<Mapping> mappings, Jedis connection) {
		for (Mapping mapping : mappings) {
			addOrCoverMapping(mapping, connection);
		}
	}

	public Mapping removeMapping(String mappingCode, Jedis connection) throws NotExistsMappingException {
		if(mappingExists(mappingCode, connection)) {
			Mapping mp = JdkSerializeProcessor.deserializeFromByteArray(Mapping.class, connection.get(mappingCode.getBytes()));
			connection.del(mappingCode);
			return mp;
		}
		throw new NotExistsMappingException("不存在code为["+mappingCode+"]的映射对象, 无法删除");
	}
	
	public void removeMapping(Collection<String> mappingCodes, Jedis connection) throws NotExistsMappingException {
		for (String mappingCode : mappingCodes) {
			removeMapping(mappingCode, connection);
		}
	}
	
	public Mapping getMapping(String mappingCode, Jedis connection) throws NotExistsMappingException {
		Mapping mp = JdkSerializeProcessor.deserializeFromByteArray(Mapping.class, connection.get(mappingCode.getBytes()));
		if(mp == null) {
			throw new NotExistsMappingException("不存在code为["+mappingCode+"]的映射对象");
		}
		return mp;
	}
	
	public boolean mappingExists(String mappingCode, Jedis connection) {
		return connection.exists(mappingCode);
	}
	
	public void destroy(Jedis connection) throws DestroyException {
	}
}
