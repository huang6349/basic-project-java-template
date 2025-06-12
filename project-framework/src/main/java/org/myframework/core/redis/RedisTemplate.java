package org.myframework.core.redis;

import lombok.Getter;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

@SuppressWarnings("LombokGetterMayBeUsed")
public abstract class RedisTemplate {

    @Resource
    @Getter
    protected StringRedisTemplate stringRedisTemplate;
}
