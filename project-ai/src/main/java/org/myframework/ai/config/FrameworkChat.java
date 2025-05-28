package org.myframework.ai.config;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.myframework.ai.IMcpServerEndpoint;
import org.myframework.ai.config.properties.ChatProperties;
import org.noear.solon.ai.chat.ChatModel;
import org.noear.solon.ai.chat.tool.MethodToolProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class FrameworkChat {

    @Bean
    ChatModel chatModel(ObjectProvider<IMcpServerEndpoint> serverEndpoints,
                        ChatProperties chatProperties) {
        val tools = serverEndpoints.stream()
                .map(MethodToolProvider::new)
                .map(MethodToolProvider::getTools)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return ChatModel.of(chatProperties.getApiUrl())
                .apiKey(chatProperties.getApiKey())
                .provider(chatProperties.getProvider())
                .model(chatProperties.getModel())
                .defaultToolsAdd(tools)
                .build();
    }
}
