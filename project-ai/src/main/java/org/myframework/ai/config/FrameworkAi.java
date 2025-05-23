package org.myframework.ai.config;

import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
public class FrameworkAi {

    @Bean
    ChatClient chatClient(List<ToolCallbackProvider> tools,
                          ChatModel chatModel) {
        val aClass = ToolCallbackProvider.class;
        return ChatClient.builder(chatModel)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultTools(ArrayUtil.toArray(tools, aClass))
                .build();
    }
}
