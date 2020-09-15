package com.douglei.orm.configuration.environment.mapping.container.impl.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.container.MappingContainer;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 
 * @author DougLei
 */
public class RedisMappingContainer implements MappingContainer {
	private static final Logger logger = LoggerFactory.getLogger(RedisMappingContainer.class);
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
	
	@Override
	public void destroy() throws DestroyException {
		if(logger.isDebugEnabled()) logger.debug("{} 开始 destroy", getClass().getName());
		try(Jedis connection = redisPool.getResource()){
			handler.destroy(connection);
		}
		if(!handler.isStoreMultiDataSource()) {
			redisPool.destroy();
			redisPool = null;	
		}
		if(logger.isDebugEnabled()) logger.debug("{} 结束 destroy", getClass().getName());
	}
}