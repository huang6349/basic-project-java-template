package org.myframework.core.config;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.myframework.core.redis.RedisHelper;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import static org.myframework.core.redis.RedisUtil.getChannelName;

@Slf4j
@Configuration
@Import({RedisHelper.class})
public class FrameworkRedis {

    @Bean("redisMessageListenerContainer")
    RedisMessageListenerContainer container(ObjectProvider<MessageListener> listeners,
                                            RedisConnectionFactory factory) {
        val container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        if (CollUtil.isNotEmpty(listeners)) {
            for (val listener : listeners) {
                val channelName = getChannelName(listener.getClass());
                val topic = new ChannelTopic(channelName);
                container.addMessageListener(listener, topic);
            }
        }
        return container;
    }
}
