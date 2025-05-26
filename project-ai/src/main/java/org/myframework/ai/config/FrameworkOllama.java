package org.myframework.ai.config;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Slf4j
@Configuration
public class FrameworkOllama {

    @Bean
    ChatClient chatClient(ObjectProvider<ToolCallbackProvider> callbackProviders,
                          ObjectProvider<ChatModel> modelProvider) {
        val advisors = new ArrayList<Advisor>();
        advisors.add(new SimpleLoggerAdvisor());
        val tools = callbackProviders.stream()
                .toArray(ToolCallbackProvider[]::new);
        return ChatClient.builder(modelProvider.getObject())
                .defaultAdvisors(advisors)
                .defaultTools(tools)
                .build();
    }
}
