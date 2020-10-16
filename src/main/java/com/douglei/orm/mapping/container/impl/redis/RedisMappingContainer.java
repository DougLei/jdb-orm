package com.douglei.orm.mapping.container.impl.redis;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.MappingProperty;
import com.douglei.orm.mapping.container.MappingContainer;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 
 * @author DougLei
 */
public class RedisMappingContainer implements MappingContainer {
	private final RedisMappingContainerHandler handler = new RedisMappingContainerHandler();
	private JedisPool redisPool;

	public RedisMappingContainer(JedisPool redisPool) {
		this.redisPool = redisPool;
	}
	public RedisMappingContainer(JedisPool redisPool, boolean storeMultiDataSource) {
		this.redisPool = redisPool;
		handler.setStoreMultiDataSource(storeMultiDataSource);
	}

	@Override
	public void clear() {
		try(Jedis connection = redisPool.getResource()){
			handler.clear(connection);
		}
		if(!handler.isStoreMultiDataSource()) {
			redisPool.destroy();
			redisPool = null;	
		}
	}
	
	@Override
	public MappingProperty addMappingProperty(MappingProperty mappingProperty) {
		try(Jedis connection = redisPool.getResource()){
			return handler.addMappingProperty(mappingProperty, connection);
		}
	}
	
	
	@Override
	public MappingProperty deleteMappingProperty(String code) {
		try(Jedis connection = redisPool.getResource()){
			return handler.deleteMappingProperty(code, connection);
		}
	}
	
	
	@Override
	public MappingProperty getMappingProperty(String code) {
		try(Jedis connection = redisPool.getResource()){
			return handler.getMappingProperty(code, connection);
		}
	}
	
	@Override
	public Mapping addMapping(Mapping mapping) {
		try(Jedis connection = redisPool.getResource()){
			return handler.addMapping(mapping, connection);
		}
	}
	
	@Override
	public Mapping deleteMapping(String code) {
		try(Jedis connection = redisPool.getResource()){
			return handler.deleteMapping(code, connection);
		}
	}
	
	@Override
	public Mapping getMapping(String code) {
		try(Jedis connection = redisPool.getResource()){
			return handler.getMapping(code, connection);
		}
	}
	
	@Override
	public boolean exists(String code) {
		try(Jedis connection = redisPool.getResource()){
			return handler.exists(code, connection);
		}
	}
}
