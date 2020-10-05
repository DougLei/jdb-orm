package com.douglei.orm.mapping.container.impl.redis;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.mapping.Mapping;
import com.douglei.tools.utils.serialize.JdkSerializeProcessor;

import redis.clients.jedis.Jedis;

/**
 * 
 * @author DougLei
 */
class RedisMappingContainerHandler extends RedisHandler {
	private static final Logger logger = LoggerFactory.getLogger(RedisMappingContainerHandler.class);

	public void clear(Jedis connection) {
		Set<String> codes = connection.keys(getPrefix() + "*");
		if(codes != null && !codes.isEmpty()) {
			connection.del(getCodeByteArray(codes));
		}
	}
	
	public Mapping addMapping(Mapping mapping, Jedis connection) {
		String code = getCode(mapping.getCode());
		Mapping exMapping = getMapping(code, connection);
		if(logger.isDebugEnabled() && exMapping != null) {
			logger.debug("覆盖已经存在code为[{}]的映射对象: {}", mapping.getCode(), getMapping(mapping.getCode(), connection));
		}
		connection.set(code.getBytes(), JdkSerializeProcessor.serialize2ByteArray(mapping));
		return exMapping;
	}
	
	public Mapping deleteMapping(String code, Jedis connection) {
		code = getCode(code);
		if(exists(code, connection)) {
			Mapping mp = JdkSerializeProcessor.deserializeFromByteArray(Mapping.class, connection.get(code.getBytes()));
			connection.del(code);
			return mp;
		}
		return null;
	}
	
	public Mapping getMapping(String code, Jedis connection) {
		byte[] mpbyte = connection.get(getCode(code).getBytes());
		if(mpbyte == null || mpbyte.length == 0) 
			return null;
		return JdkSerializeProcessor.deserializeFromByteArray(Mapping.class, mpbyte);
	}
	
	public boolean exists(String code, Jedis connection) {
		return connection.exists(code);
	}
}