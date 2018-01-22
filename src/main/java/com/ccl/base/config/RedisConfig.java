package com.ccl.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.ccl.base.redis.RedisObjectSerializer;

@Configuration
//maxInactiveIntervalInSeconds session超时时间,单位秒
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 600)
public class RedisConfig {
	
	@Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

	@Bean
	public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, T> template = new RedisTemplate<String, T>();
		template.setConnectionFactory(factory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new RedisObjectSerializer());
		return template;
	}

}
