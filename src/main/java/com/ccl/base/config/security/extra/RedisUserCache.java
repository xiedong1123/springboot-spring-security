package com.ccl.base.config.security.extra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;

public class RedisUserCache implements UserCache {

	private static final String PRIFIX = "security:user:";

	@Autowired
	private RedisTemplate<String, UserDetails> redisTemplate;

	@Override
	public UserDetails getUserFromCache(String username) {
		ValueOperations<String, UserDetails> opsForValue = redisTemplate.opsForValue();
		return opsForValue.get(getKey(username));
	}

	@Override
	public void putUserInCache(UserDetails user) {
		ValueOperations<String, UserDetails> opsForValue = redisTemplate.opsForValue();
		opsForValue.set(getKey(user.getUsername()), user);
	}

	@Override
	public void removeUserFromCache(String username) {
		redisTemplate.delete(getKey(username));
	}

	private String getKey(String rawKey) {
		return PRIFIX + rawKey;
	}

}
