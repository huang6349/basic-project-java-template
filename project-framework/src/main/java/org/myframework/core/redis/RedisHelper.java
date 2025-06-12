package org.myframework.core.redis;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.myframework.core.redis.RedisUtil.getChannelName;

@Slf4j
public class RedisHelper extends RedisTemplate {

    static StringRedisTemplate redisTemplate;

    @PostConstruct
    void init() {
        log.debug("RedisHelper 初始化开始...");
        redisTemplate = getStringRedisTemplate();
        log.debug("RedisHelper 初始化完成，静态模板已注入");
    }

    /* --------------------key相关操作--------------------- */

    @SuppressWarnings("unused")
    public static void delete(Collection<String> keys) {
        log.debug("批量删除key: {}", keys);
        redisTemplate.delete(keys);
    }

    @SuppressWarnings("unused")
    public static void delete(String key) {
        log.debug("删除key: {}", key);
        redisTemplate.delete(key);
    }

    @SuppressWarnings("unused")
    public static Boolean hasKey(String key) {
        log.debug("是否存在key: {}", key);
        return redisTemplate.hasKey(key);
    }

    @SuppressWarnings("unused")
    public static Boolean expire(String key,
                                 long timeout,
                                 TimeUnit unit) {
        log.debug("设置key的过期时间: {}", key);
        return redisTemplate.expire(key, timeout, unit);
    }

    @SuppressWarnings("unused")
    public static Set<String> keys(String pattern) {
        log.debug("获取匹配的key: {}", pattern);
        return redisTemplate.keys(pattern);
    }

    @SuppressWarnings("unused")
    public static Long getExpire(String key,
                                 TimeUnit unit) {
        log.debug("获取key的剩余时间: {}", key);
        return redisTemplate.getExpire(key, unit);
    }

    @SuppressWarnings("unused")
    public static Long getExpire(String key) {
        log.debug("获取key的剩余时间: {}", key);
        return redisTemplate.getExpire(key);
    }

    /* --------------------string相关操作------------------ */

    @SuppressWarnings("unused")
    public static void setEx(String key,
                             String value,
                             long timeout,
                             TimeUnit unit) {
        log.debug("设置key的值: {} ，并设置过期时间: {}", key, timeout);
        redisTemplate.opsForValue()
                .set(key, value, timeout, unit);
    }

    @SuppressWarnings("unused")
    public static void set(String key, String value) {
        log.debug("设置key的值: {}", key);
        redisTemplate.opsForValue()
                .set(key, value);
    }

    @SuppressWarnings("unused")
    public static String get(String key) {
        log.debug("获取key的值: {}", key);
        return redisTemplate.opsForValue()
                .get(key);
    }

    /* --------------------list相关操作-------------------- */

    @SuppressWarnings("unused")
    public static void lLeftPushAll(String key, Collection<String> value) {
        log.debug("设置key的值: {}", key);
        redisTemplate.opsForList()
                .leftPushAll(key, value);
    }

    @SuppressWarnings("unused")
    public static void lLeftPushAll(String key, String... value) {
        log.debug("设置key的值: {}", key);
        redisTemplate.opsForList()
                .leftPushAll(key, value);
    }

    @SuppressWarnings("unused")
    public static List<String> lRange(String key, long start, long end) {
        log.debug("获取key的值: {}", key);
        return redisTemplate.opsForList()
                .range(key, start, end);
    }

    /* --------------------send相关操作-------------------- */

    @SuppressWarnings("unused")
    public static void send(Class<? extends MessageListener> listener,
                            Object message) {
        val channelName = getChannelName(listener);
        send(channelName, message);
    }

    @SuppressWarnings("unused")
    public static void send(String channelName,
                            Object message) {
        redisTemplate.convertAndSend(channelName, message);
        log.debug("已发布消息: {}: 到渠道: {}", message, channelName);
    }
}
