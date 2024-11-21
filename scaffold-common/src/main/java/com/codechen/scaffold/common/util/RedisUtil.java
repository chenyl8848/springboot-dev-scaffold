package com.codechen.scaffold.common.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author：Java陈序员
 * @date 2023-04-28 15:46
 * @description redis 操作工具类
 */
public class RedisUtil {

    private static RedisTemplate redisTemplate = ApplicationContextUtil.getBean(RedisTemplate.class);

    public static void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public static void set(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    public static void set(String key, Object value, long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    public static void mset(Map<String, Object> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    public static Boolean setnx(String key, Object value) {
       return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public static Boolean setnx(String key, Object value, long time) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, time, TimeUnit.SECONDS);
    }

    public static Boolean setnx(String key, Object value, long time, TimeUnit timeUnit) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, time, timeUnit);
    }

    public static void msetnx(Map<String, Object> map) {
        redisTemplate.opsForValue().multiSetIfAbsent(map);
    }

    public static Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public static Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    public static Long deleteKeys(String keyPattern) {
        Set keys = redisTemplate.keys(keyPattern);

        if (!CollectionUtils.isEmpty(keys)) {
            return redisTemplate.delete(keys);
        } else {
            return 0L;
        }
    }

    public static Long delete(List<String> keys) {
        return redisTemplate.delete(keys);
    }

    public static Boolean expire(String key, Long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    public static void expire(String key, Long time, TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }

    public static Long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    public static Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    public static Long incrby(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public static Long decrby(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    public static void hset(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public static Boolean hset(String key, String hashKey, Object value, long time) {
        hset(key, hashKey, value);
        return expire(key, time);
    }

    public static Boolean hmset(String key, Map<String, Object> map, long time) {
        redisTemplate.opsForHash().putAll(key, map);
        return expire(key, time);
    }

    public static Object hget(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }


    public static Map<Object, Object> hgetall(String key) {
        return redisTemplate.opsForHash().entries(key);
    }


    public static void hdelete(String key, Object... hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }


    public static Boolean hexists(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

}
