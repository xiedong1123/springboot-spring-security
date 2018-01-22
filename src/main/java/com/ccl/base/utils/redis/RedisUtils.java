package com.ccl.base.utils.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands.DistanceUnit;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoRadiusCommandArgs;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * 
 * @ClassName：RedisUtils
 * @Description：redis工具类
 * @Author：xiedong
 * @Date：2017年9月12日上午10:46:28
 * @version：1.0.0
 */
@Component
public class RedisUtils {
    /**
     * 日志记录
     */
    private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);
    
    
    public static RedisTemplate<String, String> redisTemplate;
    
    @Autowired
    public RedisUtils(RedisTemplate<String, String> template) {
    	redisTemplate = template;
	}
    

	/**
     * 前缀
     */
    public static final String KEY_PREFIX_VALUE = "redis:value:";
    public static final String KEY_PREFIX_SET = "redis:set:";
    public static final String KEY_PREFIX_LIST = "redis:list:";
    public static final String KEY_PREFIX_HASH = "redis:hash:";
    public static final String KEY_PREFIX_GEO = "redis:geo:";

  /**
   * 
   * @MethodName：cacheValue
   * @param k
   * @param v
   * @param time(单位秒)  <=0 不过期
   * @return
   * @ReturnType：boolean
   * @Description：缓存value操作
   * @Creator：chenchuanliang
   * @CreateTime：2017年5月18日上午11:24:56
   * @Modifier：
   * @ModifyTime：
   */
    public static boolean cacheValue(String k, String v, long time) {
        String key = KEY_PREFIX_VALUE + k;
        try {
            ValueOperations<String, String> valueOps =  redisTemplate.opsForValue();
            valueOps.set(key, v);
			if (time > 0) {
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
            return true;
        } catch (Throwable t) {
            logger.error("缓存[" + key + "]失败, value[" + v + "]", t);
        }
        return false;
    }

  /**
   * 
   * @MethodName：cacheValue
   * @param k
   * @param v
   * @return
   * @ReturnType：boolean
   * @Description：缓存value操作
   * @Creator：chenchuanliang
   * @CreateTime：2017年5月18日上午11:24:43
   * @Modifier：
   * @ModifyTime：
   */
    public static boolean cacheValue(String k, String v) {
        return cacheValue(k, v, -1);
    }

    /**
     * 
     * @MethodName：getValue
     * @param k
     * @return
     * @ReturnType：String
     * @Description：获取缓存
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午11:21:37
     * @Modifier：
     * @ModifyTime：
     */
    public static String getValue(String k) {
    	String key = KEY_PREFIX_VALUE + k;
        try {
            ValueOperations<String, String> valueOps =  redisTemplate.opsForValue();
			return valueOps.get(key);
        } catch (Throwable t) {
            logger.error("获取缓存失败key[" + key + ", error[" + t + "]");
        }
        return null;
    }
  
    /**
    * 
    * @MethodName：cacheSet
    * @param k
    * @param v
    * @param time
    * @return
    * @ReturnType：boolean
    * @Description：缓存set操作
    * @Creator：chenchuanliang
    * @CreateTime：2017年5月18日上午11:20:00
    * @Modifier：
    * @ModifyTime：
    */
    public static boolean cacheSet(String k, String v, long time) {
        String key = KEY_PREFIX_SET + k;
        try {
            SetOperations<String, String> valueOps =  redisTemplate.opsForSet();
            valueOps.add(key, v);
			if (time > 0) {
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
        } catch (Throwable t) {
            logger.error("缓存[" + key + "]失败, value[" + v + "]", t);
        }
        return true;
    }

   /**
    * 
    * @MethodName：cacheSet
    * @param k
    * @param v
    * @return
    * @ReturnType：boolean
    * @Description：缓存set
    * @Creator：chenchuanliang
    * @CreateTime：2017年5月18日上午11:19:00
    * @Modifier：
    * @ModifyTime：
    */
    public static boolean cacheSet(String k, String v) {
        return cacheSet(k, v, -1);
    }

   /**
    * 
    * @MethodName：cacheSet
    * @param k
    * @param v
    * @param time
    * @return
    * @ReturnType：boolean
    * @Description：缓存set
    * @Creator：chenchuanliang
    * @CreateTime：2017年5月18日上午11:18:48
    * @Modifier：
    * @ModifyTime：
    */
    public static boolean cacheSet(String k, Set<String> v, long time) {
        String key = KEY_PREFIX_SET + k;
        try {
            SetOperations<String, String> setOps =  redisTemplate.opsForSet();
            setOps.add(key, v.toArray(new String[v.size()]));
            if (time > 0) {
            	redisTemplate.expire(key, time, TimeUnit.SECONDS);
            	}
            return true;
        } catch (Throwable t) {
            logger.error("缓存[" + key + "]失败, value[" + v + "]", t);
        }
        return false;
    }

    /**
     * 
     * @MethodName：cacheSet
     * @param k
     * @param v
     * @return
     * @ReturnType：boolean
     * @Description：缓存set
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午11:18:34
     * @Modifier：
     * @ModifyTime：
     */
    public static boolean cacheSet(String k, Set<String> v) {
        return cacheSet(k, v, -1);
    }

  /**
   * 
   * @MethodName：getSet
   * @param k
   * @return
   * @ReturnType：Set<String>
   * @Description：获取缓存set数据
   * @Creator：chenchuanliang
   * @CreateTime：2017年5月18日上午11:17:45
   * @Modifier：
   * @ModifyTime：
   */
    public static Set<String> getSet(String k) {
    	String key = KEY_PREFIX_SET + k;
        try {
            SetOperations<String, String> setOps = redisTemplate.opsForSet();
			return setOps.members(key);
        } catch (Throwable t) {
            logger.error("获取set缓存失败key[" + key + ", error[" + t + "]");
        }
        return null;
    }

   /**
    * 
    * @MethodName：cacheList
    * @param k
    * @param v
    * @param time
    * @return
    * @ReturnType：boolean
    * @Description：list缓存
    * @Creator：chenchuanliang
    * @CreateTime：2017年5月18日上午11:17:23
    * @Modifier：
    * @ModifyTime：
    */
    public static boolean cacheList(String k, String v, long time) {
        String key = KEY_PREFIX_LIST + k;
        try {
            ListOperations<String, String> listOps =  redisTemplate.opsForList();
            listOps.rightPush(key, v);
            if (time > 0) {
            	redisTemplate.expire(key, time, TimeUnit.SECONDS);
            	}
            return true;
        } catch (Throwable t) {
            logger.error("缓存[" + key + "]失败, value[" + v + "]", t);
        }
        return false;
    }

   /**
    * 
    * @MethodName：cacheList
    * @param k
    * @param v
    * @return
    * @ReturnType：boolean
    * @Description：缓存list
    * @Creator：chenchuanliang
    * @CreateTime：2017年5月18日上午11:17:10
    * @Modifier：
    * @ModifyTime：
    */
    public static boolean cacheList(String k, String v) {
        return cacheList(k, v, -1);
    }

  /**
   * 
   * @MethodName：cacheList
   * @param k
   * @param v
   * @param time
   * @return
   * @ReturnType：boolean
   * @Description：缓存list
   * @Creator：chenchuanliang
   * @CreateTime：2017年5月18日上午11:15:47
   * @Modifier：
   * @ModifyTime：
   */
    public static boolean cacheList(String k, List<String> v, long time) {
        String key = KEY_PREFIX_LIST + k;
        try {
            ListOperations<String, String> listOps =  redisTemplate.opsForList();
            listOps.rightPushAll(key, v);
            if (time > 0) {
            	redisTemplate.expire(key, time, TimeUnit.SECONDS);
            	}
            return true;
        } catch (Throwable t) {
            logger.error("缓存[" + key + "]失败, value[" + v + "]", t);
        }
        return false;
    }

    /**
     * 
     * @MethodName：cacheList
     * @param k
     * @param v
     * @return
     * @ReturnType：boolean
     * @Description：缓存list
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午11:15:05
     * @Modifier：
     * @ModifyTime：
     */
    public static boolean cacheList(String k, List<String> v) {
       return cacheList(k, v, -1);
    }

   /**
    * 
    * @MethodName：getList
    * @param k
    * @param start
    * @param end
    * @return
    * @ReturnType：List<String>
    * @Description：获取list缓存
    * @Creator：chenchuanliang
    * @CreateTime：2017年5月18日上午11:14:45
    * @Modifier：
    * @ModifyTime：
    */
    public static List<String> getList(String k, long start, long end) {
    	String key = KEY_PREFIX_LIST + k;
        try {
            ListOperations<String, String> listOps =  redisTemplate.opsForList();
			return listOps.range(key, start, end);
        } catch (Throwable t) {
            logger.error("获取list缓存失败key[" + key + "]" + ", error[" + t + "]");
        }
        return null;
    }

    /**
     * 
     * @MethodName：getListSize
     * @param k
     * @return
     * @ReturnType：long
     * @Description：获取总条数
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午11:13:39
     * @Modifier：
     * @ModifyTime：
     */
    public static long getListSize(String k) {
    	String key = KEY_PREFIX_LIST + k;
        try {
            ListOperations<String, String> listOps =  redisTemplate.opsForList();
			return listOps.size(key);
        } catch (Throwable t) {
            logger.error("获取list长度失败key[" + key + "], error[" + t + "]");
        }
        return 0;
    }

   /**
    * 
    * @MethodName：removeOneOfList
    * @param k
    * @return
    * @ReturnType：boolean
    * @Description：移除list缓存
    * @Creator：chenchuanliang
    * @CreateTime：2017年5月18日上午11:11:16
    * @Modifier：
    * @ModifyTime：
    */
    public static boolean removeOneOfList(String k) {
    	String key = KEY_PREFIX_LIST + k;
        try {
            ListOperations<String, String> listOps =  redisTemplate.opsForList();
			listOps.rightPop(key);
            return true;
        } catch (Throwable t) {
            logger.error("移除list缓存失败key[" + key + ", error[" + t + "]");
        }
        return false;
    }
    
    /**
     * 
     * @MethodName：cacheGeo
     * @param key
     * @param x
     * @param y
     * @param member
     * @param time(单位秒)  <=0 不过期
     * @return
     * @ReturnType：boolean
     * @Description：缓存地理位置信息
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午11:30:23
     * @Modifier：
     * @ModifyTime：
     */
    public static boolean cacheGeo(String k, double x, double y, String member, long time) {
    	String key = KEY_PREFIX_GEO + k;
    	try {
    		GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
    		geoOps.geoAdd(key, new Point(x, y) , member);
    		if (time > 0) {
    			redisTemplate.expire(key, time, TimeUnit.SECONDS);
    			}
		} catch (Throwable t) {
			logger.error("缓存[" + key +"]" + "失败, point["+ x + "," + 
					y + "], member[" + member + "]" +", error[" + t + "]");
		}
		return true;
    }
    
    /**
     * 
     * @MethodName：cacheGeo
     * @param key
     * @param locations
     * @param time(单位秒)  <=0 不过期
     * @return
     * @ReturnType：boolean
     * @Description：
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午11:31:33
     * @Modifier：
     * @ModifyTime：
     */
    public static boolean cacheGeo(String k, Iterable<GeoLocation<String>> locations, long time) {
    	try {
    		for (GeoLocation<String> location : locations) {
    			cacheGeo(k, location.getPoint().getX(), location.getPoint().getY(), location.getName(), time);
    		}
		} catch (Throwable t) {
			logger.error("缓存[" + KEY_PREFIX_GEO + k + "]" + "失败" +", error[" + t + "]");
		}
		return true;
	}
    
    /**
     * 
     * @MethodName：removeGeo
     * @param key
     * @param members
     * @return
     * @ReturnType：boolean
     * @Description：移除地理位置信息
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午10:53:01
     * @Modifier：
     * @ModifyTime：
     */
    public static boolean removeGeo(String k, String...members) {
    	String key = KEY_PREFIX_GEO + k;
    	try {
    		GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
			geoOps.geoRemove(key, members);
		} catch (Throwable t) {
			logger.error("移除[" + key +"]" + "失败" +", error[" + t + "]");
		}
    	return true;
    }
    
    /**
     * 
     * @MethodName：distanceGeo
     * @param key
     * @param member1
     * @param member2
     * @return Distance
     * @ReturnType：Distance
     * @Description：根据两个成员计算两个成员之间距离
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午10:58:33
     * @Modifier：
     * @ModifyTime：
     */
    public static Distance distanceGeo(String k, String member1, String member2) {
    	String key = KEY_PREFIX_GEO + k;
    	try {
    		GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
			return geoOps.geoDist(key, member1, member2);
		} catch (Throwable t) {
			logger.error("计算距离[" + key +"]" + "失败, member[" + member1 + "," + member2 +"], error[" + t + "]");
		}
		return null;
    }
    
    /**
     * 
     * @MethodName：getGeo
     * @param key
     * @param members
     * @return
     * @ReturnType：List<Point>
     * @Description：根据key和member获取这些member的坐标信息
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午11:02:01
     * @Modifier：
     * @ModifyTime：
     */
    public static List<Point> getGeo(String k, String...members) {
    	String key = KEY_PREFIX_GEO + k;
    	try {
    		GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
    		return geoOps.geoPos(key, members);
		} catch (Throwable t) {
			logger.error("获取坐标[" + key +"]" + "失败]" + ", error[" + t + "]");
		}
		return null;
    }
    
    /**
     * 
     * @MethodName：radiusGeo
     * @param key
     * @param x
     * @param y
     * @param distance km
     * @return
     * @ReturnType：GeoResults<GeoLocation<String>>
     * @Description：通过给定的坐标和距离(km)获取范围类其它的坐标信息
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午11:09:10
     * @Modifier：
     * @ModifyTime：
     */
    public static GeoResults<GeoLocation<String>> radiusGeo(String key, double x, double y, double distance, Direction direction, long limit) {
    	try {
    		String k = KEY_PREFIX_GEO + key;
    		GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
    		
    		//设置geo查询参数
    		GeoRadiusCommandArgs geoRadiusArgs = GeoRadiusCommandArgs.newGeoRadiusArgs();
    		geoRadiusArgs = geoRadiusArgs.includeCoordinates().includeDistance();//查询返回结果包括距离和坐标
    		if (Direction.ASC.equals(direction)) {//按查询出的坐标距离中心坐标的距离进行排序
    			geoRadiusArgs.sortAscending();
    		} else if (Direction.DESC.equals(direction)) {
    			geoRadiusArgs.sortDescending();
    		}
    		geoRadiusArgs.limit(limit);//限制查询数量
    		GeoResults<GeoLocation<String>> radiusGeo = geoOps.geoRadius(k, new Circle(new Point(x, y), new Distance(distance, DistanceUnit.METERS)), geoRadiusArgs);
    		
    		return radiusGeo;
		} catch (Throwable t) {
			logger.error("通过坐标[" + x + "," + y +"]获取范围[" + distance + "km的其它坐标失败]" + ", error[" + t + "]");
		}
		return null;
    }
    
    /**
     * 
     * @MethodName：containsListKey
     * @param k
     * @return
     * @ReturnType：boolean
     * @Description：判断缓存是否存在
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午11:23:37
     * @Modifier：
     * @ModifyTime：
     */
    public static boolean containsValueKey(String k) {
        return containsKey(KEY_PREFIX_VALUE + k);
    }

    /**
     * 
     * @MethodName：containsListKey
     * @param k
     * @return
     * @ReturnType：boolean
     * @Description：判断缓存是否存在
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午11:23:37
     * @Modifier：
     * @ModifyTime：
     */
    public static boolean containsSetKey(String k) {
        return containsKey(KEY_PREFIX_SET + k);
    }

   /**
    * 
    * @MethodName：containsListKey
    * @param k
    * @return
    * @ReturnType：boolean
    * @Description：判断缓存是否存在
    * @Creator：chenchuanliang
    * @CreateTime：2017年5月18日上午11:23:37
    * @Modifier：
    * @ModifyTime：
    */
    public static boolean containsListKey(String k) {
        return containsKey(KEY_PREFIX_LIST + k);
    }
    
    /**
     * 
     * @MethodName：containsGeoKey
     * @param k
     * @return
     * @ReturnType：boolean
     * @Description：判断缓存是否存在
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午11:34:14
     * @Modifier：
     * @ModifyTime：
     */
    public static boolean containsGeoKey(String k) {
        return containsKey(KEY_PREFIX_GEO + k);
    }

    private static boolean containsKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Throwable t) {
            logger.error("判断缓存存在失败key[" + key + ", error[" + t + "]");
        }
        return false;
    }
    
    /**
     * 
     * @MethodName：remove
     * @param key
     * @return
     * @ReturnType：boolean
     * @Description：移除key中所有缓存
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午11:20:19
     * @Modifier：
     * @ModifyTime：
     */
    public static boolean removeValue(String k) {
        return remove(KEY_PREFIX_VALUE + k);
    }
    
    /**
     * 
     * @MethodName：remove
     * @param key
     * @return
     * @ReturnType：boolean
     * @Description：移除key中所有缓存
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午11:20:19
     * @Modifier：
     * @ModifyTime：
     */
    public static boolean removeSet(String k) {
        return remove(KEY_PREFIX_SET + k);
    }
    
    /**
     * 
     * @MethodName：remove
     * @param key
     * @return
     * @ReturnType：boolean
     * @Description：移除key中所有缓存
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午11:20:19
     * @Modifier：
     * @ModifyTime：
     */
    public static boolean removeList(String k) {
        return remove(KEY_PREFIX_LIST + k);
    }
    
    /**
     * 
     * @MethodName：removeGeo
     * @param k
     * @return
     * @ReturnType：boolean
     * @Description：移除key中所有缓存
     * @Creator：chenchuanliang
     * @CreateTime：2017年5月18日上午11:36:23
     * @Modifier：
     * @ModifyTime：
     */
    public static boolean removeGeo(String k) {
        return remove(KEY_PREFIX_GEO + k);
    }
    
    private static boolean remove(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Throwable t) {
            logger.error("移除缓存失败key[" + key + ", error[" + t + "]");
        }
        return false;
    }
    
    /**
     * 缓存一个hash键值对到hash表
     * @MethodName：cacheHash
     * @param key
     * @param hashKey
     * @param value
     * @param time
     * @return
     * @ReturnType：boolean
     * @Description：
     * @Creator：chenchuanliang
     * @CreateTime：2017年6月27日上午10:43:25
     * @Modifier：
     * @ModifyTime：
     */
    public static boolean cacheHash(String key, String hashKey, String value, long time){
    	try {
    		key = KEY_PREFIX_HASH + key;
    		HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
    		opsForHash.put(key, hashKey, value);
    		if (time > 0) {
    			redisTemplate.expire(key, time, TimeUnit.SECONDS);
    		}
    		return true;
		} catch (Exception e) {
			logger.error("缓存失败key[" + key + ", error[" + e + "]");
		}
    	return false;
    }
    
    /**
     * 缓存一个map到hash表
     * @MethodName：cacheHash
     * @param key
     * @param map
     * @param time
     * @return
     * @ReturnType：boolean
     * @Description：
     * @Creator：chenchuanliang
     * @CreateTime：2017年6月27日上午10:45:27
     * @Modifier：
     * @ModifyTime：
     */
    public static boolean cacheHash(String key, Map<String, String> map, long time){
    	try {
    		key = KEY_PREFIX_HASH + key;
    		HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
    		opsForHash.putAll(key, map);
    		if (time > 0) {
    			redisTemplate.expire(key, time, TimeUnit.SECONDS);
    		}
    		return true;
    	} catch (Exception e) {
    		logger.error("缓存失败key[" + key + ", error[" + e + "]");
    	}
    	return false;
    }
    
    /**
     * 通过key获取一个map
     * @MethodName：getHash
     * @param key
     * @return
     * @ReturnType：Map<String,String>
     * @Description：
     * @Creator：chenchuanliang
     * @CreateTime：2017年6月27日上午10:48:21
     * @Modifier：
     * @ModifyTime：
     */
    public static Map<String, String> getHash(String key){
    	try {
    		key = KEY_PREFIX_HASH + key;
    		HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
    		return opsForHash.entries(key);
    	} catch (Exception e) {
    		logger.error("获取缓存失败key[" + key + ", error[" + e + "]");
    	}
    	return null;
    }
    
    /**
     * 获取key对应map中所有的keys
     * @MethodName：getHashKey
     * @param key
     * @return
     * @ReturnType：Set<String>
     * @Description：
     * @Creator：chenchuanliang
     * @CreateTime：2017年6月27日上午10:49:16
     * @Modifier：
     * @ModifyTime：
     */
    public static Set<String> getHashKey(String key){
    	try {
    		key = KEY_PREFIX_HASH + key;
    		HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
    		return opsForHash.keys(key);
    	} catch (Exception e) {
    		logger.error("获取缓存失败key[" + key + ", error[" + e + "]");
    	}
    	return null;
    }
    
    /**
     * 获取key对应map中所有的values
     * @MethodName：getHashValues
     * @param key
     * @return
     * @ReturnType：List<String>
     * @Description：
     * @Creator：chenchuanliang
     * @CreateTime：2017年6月27日上午10:49:55
     * @Modifier：
     * @ModifyTime：
     */
    public static List<String> getHashValues(String key){
    	try {
    		key = KEY_PREFIX_HASH + key;
    		HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
    		return opsForHash.values(key);
    	} catch (Exception e) {
    		logger.error("获取缓存失败key[" + key + ", error[" + e + "]");
    	}
    	return null;
    }
    
    /**
     * 删除key对应hashMap中key的值.或删除整个key对应map
     * @MethodName：delete
     * @param key
     * @param hashKeys
     * @return
     * @ReturnType：List<String>
     * @Description：
     * @Creator：chenchuanliang
     * @CreateTime：2017年6月27日上午10:51:22
     * @Modifier：
     * @ModifyTime：
     */
    public static boolean deleteHash(String key, Object... hashKeys){
    	try {
    		key = KEY_PREFIX_HASH + key;
    		if (hashKeys == null || hashKeys.length == 0) {
    			redisTemplate.delete(key);
    		} else {
    			HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
    			opsForHash.delete(key, hashKeys);
    		}
    		return true;
    	} catch (Exception e) {
    		logger.error("获取缓存失败key[" + key + ", error[" + e + "]");
    	}
    	return false;
    }

    /**
     * 获取key对应的过期时间, 秒
     * @MethodName：getExpireTime
     * @param key
     * @return
     * @ReturnType：Long
     * @Description：
     * @Creator：yangbiao
     * @CreateTime：2017年7月10日上午9:51:22
     * @Modifier：
     * @ModifyTime：
     */
    public static Long getExpireTimeValue(String key){
        key = KEY_PREFIX_VALUE + key;
        Long expire = -2L;
        try {
            expire = redisTemplate.getExpire(key);

        }catch (Exception e){
            logger.error("获取缓存剩余时间失败key[" + key + ", error[" + e + "]");
        }
        return expire;
    }
    
}