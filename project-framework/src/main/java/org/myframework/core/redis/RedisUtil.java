package org.myframework.core.redis;

import lombok.val;
import org.springframework.data.redis.connection.MessageListener;

import static cn.hutool.core.text.CharSequenceUtil.*;
import static cn.hutool.core.util.ClassUtil.getClassName;

public final class RedisUtil {

    private RedisUtil() {
    }

    public static String getChannelName(Class<? extends MessageListener> clazz) {
        val className = getClassName(clazz, Boolean.TRUE);
        val channel = removeSufAndLowerFirst(className, "Subscriber");
        val name = toSymbolCase(channel, '-');
        return format("channel:{}", name);
    }
}
