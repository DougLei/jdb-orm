package com.douglei.orm.configuration.environment.mapping.store.impl.redis;

import java.util.Collection;

import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.store.MappingStore;
import com.douglei.orm.configuration.environment.mapping.store.NotExistsMappingException;
import com.douglei.orm.configuration.environment.mapping.store.RepeatedMappingException;
import com.douglei.tools.utils.Collections;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 
 * @author DougLei
 */
public class RedisMappingStore implements MappingStore {
	private final RedisMappingStoreHandler handler = new RedisMappingStoreHandler();
	private JedisPool redisPool;

	public RedisMappingStore(JedisPool redisPool) {
		this.redisPool = redisPool;
	}
	public RedisMappingStore(JedisPool redisPool, boolean multiDataSource) {
		this.redisPool = redisPool;
		handler.setMultiDataSource(multiDataSource);
	}

	@Override
	public void initializeStore(int size) {
		try(Jedis connection = redisPool.getResource()){
			handler.initializeStore(connection);
		}
	}
	
	@Override
	public void addMapping(Mapping mapping) throws RepeatedMappingException{
		try(Jedis connection = redisPool.getResource()){
			handler.addMapping(mapping, connection);
		}
	}
	
	@Override
	public void addMapping(Collection<Mapping> mappings) throws RepeatedMappingException {
		if(Collections.unEmpty(mappings)) {
			try(Jedis connection = redisPool.getResource()){
				handler.addMapping(mappings, connection);
			}
		}
	}

	@Override
	public void addOrCoverMapping(Mapping mapping) {
		try(Jedis connection = redisPool.getResource()){
			handler.addOrCoverMapping(mapping, connection);
		}
	}
	
	@Override
	public void addOrCoverMapping(Collection<Mapping> mappings) {
		if(Collections.unEmpty(mappings)) {
			try(Jedis connection = redisPool.getResource()){
				handler.addOrCoverMapping(mappings, connection);
			}
		}
	}
	
	@Override
	public Mapping removeMapping(String mappingCode) throws NotExistsMappingException {
		try(Jedis connection = redisPool.getResource()){
			return handler.removeMapping(mappingCode, connection);
		}
	}
	
	@Override
	public void removeMapping(Collection<String> mappingCodes) throws NotExistsMappingException {
		if(Collections.unEmpty(mappingCodes)) {
			try(Jedis connection = redisPool.getResource()){
				handler.removeMapping(mappingCodes, connection);
			}
		}
	}
	
	@Override
	public Mapping getMapping(String mappingCode) throws NotExistsMappingException {
		try(Jedis connection = redisPool.getResource()){
			return handler.getMapping(mappingCode, connection);
		}
	}
	
	@Override
	public boolean mappingExists(String mappingCode) {
		try(Jedis connection = redisPool.getResource()){
			return handler.mappingExists(mappingCode, connection);
		}
	}
	
	@Override
	public void destroy() throws DestroyException {
		try(Jedis connection = redisPool.getResource()){
			handler.destroy(connection);
		}
		if(!handler.isMultiDataSource()) {
			redisPool.destroy();
			redisPool = null;	
		}
	}
}
