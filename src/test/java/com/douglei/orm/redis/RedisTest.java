package com.douglei.orm.redis;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.douglei.orm.session.SysUser;
import com.douglei.tools.utils.serialize.JdkSerializeProcessor;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

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
	
	@Test
	public void testPip() {
		Jedis jedis = jedisPool.getResource();
		Pipeline p = jedis.pipelined();
		p.set("sex", "男");
		p.sync();
		jedis.close();
	}
	
	@Test
	public void testPip1() {
		Jedis jedis = jedisPool.getResource();
		System.out.println(jedis.get("sex"));
		jedis.close();
	}
	
	@Test
	public void test2() {
		Jedis jedis = jedisPool.getResource();
		System.out.println(jedis.get("MP:default:com.ibs.demo.entity.SysUser"));
		jedis.close();
	}
	
	@Test
	public void test3() {
		Jedis jedis = jedisPool.getResource();
		jedis.set("test1", "test1");
		jedis.set("test2", "test2");
		jedis.set("test3", "test3");
		jedis.close();
	}
	
	@Test
	public void test4() {
		Jedis jedis = jedisPool.getResource();
		Set<String> keys = jedis.keys("test*");
		for (String string : keys) {
			System.out.println(jedis.get(string));
		}
		System.out.println("------------------");
		jedis.close();
	}
	
	@Test
	public void test5() {
//		Jedis jedis = jedisPool.getResource();
//		Pipeline p  = jedis.pipelined();
//		p.del("test1", "test2", "test3");
//		p.sync();
//		jedis.close();
		
		List<String> mappingCodes = new ArrayList<String>();
		mappingCodes.add("test1");
		mappingCodes.add("test2");
		mappingCodes.add("test3");
		
		Jedis connection = jedisPool.getResource();
		Pipeline pipeline = connection.pipelined();
		byte index = 0;
		byte[][] codes = new byte[mappingCodes.size()][];
		for (String code : mappingCodes) {
			codes[index++] = code.getBytes(StandardCharsets.UTF_8);
		}
		pipeline.del(codes);
		pipeline.sync();
		
	}
	
	@Test
	public void test6() {
		Jedis connection = jedisPool.getResource();
		System.out.println(connection.exists("ORM:MP:queryUser".getBytes()));;
		
	}
}
