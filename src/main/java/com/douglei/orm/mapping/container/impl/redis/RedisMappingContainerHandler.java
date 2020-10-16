package com.douglei.orm.mapping.container.impl.redis;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.MappingProperty;
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
		if(codes != null && !codes.isEmpty()) 
			connection.del(getCodeByteArray(codes));
	}
	
	public MappingProperty addMappingProperty(MappingProperty mappingProperty, Jedis connection) {
		MappingProperty exMappingProperty = getMappingProperty(mappingProperty.getCode(), connection);
		if(logger.isDebugEnabled() && exMappingProperty != null) 
			logger.debug("覆盖code为[{}]的映射属性: {}", mappingProperty.getCode(), exMappingProperty);
		
		connection.set(getCode4Property(mappingProperty.getCode()).getBytes(), JdkSerializeProcessor.serialize2ByteArray(mappingProperty));
		return exMappingProperty;
	}
	
	public MappingProperty deleteMappingProperty(String code, Jedis connection) {
		if(!exists(code, connection)) 
			return null;
		
		code = getCode4Property(code);
		MappingProperty mappingProperty = JdkSerializeProcessor.deserializeFromByteArray(MappingProperty.class, connection.get(code.getBytes()));
		connection.del(code);
		return mappingProperty;
	}
	
	public MappingProperty getMappingProperty(String code, Jedis connection) {
		byte[] mpfbyte = connection.get(getCode4Property(code).getBytes());
		if(mpfbyte == null || mpfbyte.length == 0) 
			return null;
		return JdkSerializeProcessor.deserializeFromByteArray(MappingProperty.class, mpfbyte);
	}
	
	public Mapping addMapping(Mapping mapping, Jedis connection) {
		Mapping exMapping = getMapping(mapping.getCode(), connection);
		if(logger.isDebugEnabled() && exMapping != null) 
			logger.debug("覆盖code为[{}]的映射: {}", mapping.getCode(), exMapping);
		
		connection.set(getCode(mapping.getCode()).getBytes(), JdkSerializeProcessor.serialize2ByteArray(mapping));
		return exMapping;
	}
	
	public Mapping deleteMapping(String code, Jedis connection) {
		if(!exists(code, connection)) 
			return null;
		
		code = getCode(code);
		Mapping mapping = JdkSerializeProcessor.deserializeFromByteArray(Mapping.class, connection.get(code.getBytes()));
		connection.del(code);
		return mapping;
	}
	
	public Mapping getMapping(String code, Jedis connection) {
		byte[] mpbyte = connection.get(getCode(code).getBytes());
		if(mpbyte == null || mpbyte.length == 0) 
			return null;
		return JdkSerializeProcessor.deserializeFromByteArray(Mapping.class, mpbyte);
	}
	
	public boolean exists(String code, Jedis connection) {
		return connection.exists(getCode(code));
	}
}
