package com.douglei.orm.redis;

import java.util.Set;

import org.junit.Test;

import com.douglei.orm.session.SysUser;
import com.douglei.tools.utils.serialize.JdkSerializeProcessor;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * @author DougLei
 */
public class RedisTest {
	
	private static JedisPool jedisPool;
	static {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(1024);
        config.setMaxIdle(200);
        config.setMaxWaitMillis(10000);
		jedisPool = new JedisPool(config, "localhost", 6379, 10000, "douglei");
	}
	
	@Test
	public void testString() {
		Jedis jedis = jedisPool.getResource();
		jedis.set("name", "哈哈");
		System.out.println(jedis.get("name"));
		
		jedis.append("name", "-Douglei");
		System.out.println(jedis.get("name"));
		
		jedis.del("name");
		
		System.out.println(jedis.get("name"));
	}
	
	@Test
	public void testObject() {
		Jedis jedis = jedisPool.getResource();
		SysUser user = new SysUser("1", "哈哈", 12, "男");
		System.out.println(user);
		jedis.set("user".getBytes(), JdkSerializeProcessor.serialize2ByteArray(user));
		SysUser user2 = JdkSerializeProcessor.deserializeFromByteArray(SysUser.class, jedis.get("user".getBytes()));
		System.out.println(user2);
	}
	
	@Test
	public void testExists() {
		Jedis jedis = jedisPool.getResource();
		System.out.println(jedis.exists("user"));
		jedis.set("name", "哈哈");
		System.out.println(jedis.get("user"));
		System.out.println(jedis.exists("name"));
	}
	
	@Test
	public void testLike() {
		Jedis jedis = jedisPool.getResource();
		
//		jedis.set("name1", "金石磊");
//		jedis.set("name2", "小新爷");
//		jedis.set("name3", "Douglei");
//		jedis.set("namd", "dfadsf辅导费");
		
		Set<String> keys = jedis.keys("name*");
		keys.forEach(name -> System.out.println(name));
		jedis.close();
		
	}
}
