package com.ccl.base.utils.test;
/**
 ***********************************************************************
 * All rights Reserved, Designed By changhong
 * @Title: RedisTest.java   
 * @Package com.changhong.oto.common.utils.test   
 * @Description: TODO   
 * @author: liuchengyong     
 * @date: 2016-12-14 下午2:48:56   
 * @version V1.0 
 * @Copyright: 2016 www.changhong.com Inc. All rights reserved. 
 ***********************************************************************
 *//*
package com.changhong.oto.common.utils.test;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

import org.apache.shiro.cache.Cache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.DefaultMessage;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.changhong.oto.common.utils.cache.RedisCacheManagerWrapper;

*//**
 * @ClassName RedisTest
 * @Description TODO
 * @author liuchengyong
 * @Date 2016-12-14 下午2:48:56
 * @version 1.0.0
 *//*
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:/spring/applicationContext.xml","classpath:/spring-cache/redis/spring-redis-cache.xml"})  
public class RedisTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	RedisCacheManager redisCacheManger;
	
	@SuppressWarnings("rawtypes")
	@Qualifier(value="redisTemplate")
	@Autowired
	RedisOperations redisOperations;
	
	@Autowired
	RedisCacheManagerWrapper redisCacheManagerWrapper;
	
	@Test  
    public void test01() {  
		S s = new S();
		s.name = "2222";
		Cache<String,Object> cache = redisCacheManagerWrapper.getCache("test");
		cache.put("s", s);
		System.out.println(((S)cache.get("s")).name);
		
		cache.put("ll","xxxx");
		System.out.println(cache.get("ll"));
    }
	
	@SuppressWarnings("unchecked")
	@Test  
	public void testRedisTemplate01Add(){
		
		redisOperations.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.setEx(redisOperations.getKeySerializer().serialize("test01"), 1000, redisOperations.getValueSerializer().serialize("test01"));
				return new Long(0);
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	@Test  
	public void testRedisTemplate01Get(){
		
		redisOperations.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] b = 	connection.get(redisOperations.getKeySerializer().serialize("test01"));
				System.out.println(redisOperations.getValueSerializer().deserialize(b));
				return new Long(0);
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testRedisTemplate01Del(){
			
			redisOperations.execute(new RedisCallback<Long>() {
				@Override
				public Long doInRedis(RedisConnection connection)
						throws DataAccessException {
					connection.del(redisOperations.getKeySerializer().serialize("test01"));
					return new Long(0);
				}
			});
		}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testRedisTemplate01Expire(){
			testRedisTemplate01Add();
			redisOperations.execute(new RedisCallback<Long>() {
				@Override
				public Long doInRedis(RedisConnection connection)
						throws DataAccessException {
					connection.expire(redisOperations.getKeySerializer().serialize("test01"), 1000);
					return new Long(0);
				}
			});
		}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testRedisTemplate01ExpireAt(){
			testRedisTemplate01Add();
			redisOperations.execute(new RedisCallback<Long>() {
				@Override
				public Long doInRedis(RedisConnection connection)
						throws DataAccessException {
					connection.expireAt(redisOperations.getKeySerializer().serialize("test01"),new Date().getTime()/1000+60);
					return new Long(0);
				}
			});
		}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testRedisTemplate01ExpirePersist(){
			testRedisTemplate01Add();
			redisOperations.execute(new RedisCallback<Long>() {
				@Override
				public Long doInRedis(RedisConnection connection)
						throws DataAccessException {
					connection.persist(redisOperations.getKeySerializer().serialize("test01"));
					return new Long(0);
				}
			});
		}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testRedisTemplate01ExpirePublish(){
		
		redisOperations.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.publish(redisOperations.getKeySerializer().serialize("test"),redisOperations.getValueSerializer().serialize("我发布了一个消息"));
				return new Long(0);
			}
		});
		
		}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testRedisTemplate01ExpireSubscribe(){
		
		redisOperations.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.subscribe(new MessageListener() {
					@Override
					public void onMessage(Message message, byte[] pattern) {
						// TODO Auto-generated method stub
						System.out.println(redisOperations.getValueSerializer().deserialize(message.getBody()));
					}
				},redisOperations.getKeySerializer().serialize("test"));
				return new Long(0);
			}
		});
		}
	
	static class S implements Serializable{
		private String name;
	}
}
*/