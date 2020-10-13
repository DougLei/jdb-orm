package com.douglei.orm.mapping.container.impl.redis;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.MappingFeature;
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
	public MappingFeature addMappingFeature(MappingFeature mappingFeature) {
		try(Jedis connection = redisPool.getResource()){
			return handler.addMappingFeature(mappingFeature, connection);
		}
	}
	
	
	@Override
	public MappingFeature deleteMappingFeature(String code) {
		try(Jedis connection = redisPool.getResource()){
			return handler.deleteMappingFeature(code, connection);
		}
	}
	
	
	@Override
	public MappingFeature getMappingFeature(String code) {
		try(Jedis connection = redisPool.getResource()){
			return handler.getMappingFeature(code, connection);
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
