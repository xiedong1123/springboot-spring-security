package com.ccl.base.utils.test;
/**
 ***********************************************************************
 * All rights Reserved, Designed By changhong
 * @Title: RedisTestString.java   
 * @Package com.changhong.oto.common.utils.test   
 * @Description: TODO   
 * @author: liuchengyong     
 * @date: 2016-12-15 下午1:48:17   
 * @version V1.0 
 * @Copyright: 2016 www.changhong.com Inc. All rights reserved. 
 ***********************************************************************
 *//*
package com.changhong.oto.common.utils.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

*//**
 * @ClassName RedisTestString
 * @Description TODO
 * @author liuchengyong
 * @Date 2016-12-15 下午1:48:17
 * @version 1.0.0
 *//*
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:/spring/applicationContext.xml","classpath:/spring-cache/redis/spring-redis-cache.xml"})
public class RedisTestString extends AbstractTransactionalJUnit4SpringContextTests{
	@SuppressWarnings("rawtypes")
	@Qualifier(value="redisTemplate")
	@Autowired
	RedisOperations redisOperations;
	
	@SuppressWarnings("unchecked")
	@Test  
	public void testRedisTemplate01(){
		
		redisOperations.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.flushDb();
				connection.lPush(redisOperations.getKeySerializer().serialize("x"),redisOperations.getValueSerializer().serialize("1"),redisOperations.getValueSerializer().serialize("2"));
				return new Long(0);
			}
		});
	}
}
*/