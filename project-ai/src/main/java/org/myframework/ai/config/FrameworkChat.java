package org.myframework.ai.config;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.myframework.ai.core.ChatCustomizer;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FrameworkChat {

    @Bean
    ChatClient chatClient(ObjectProvider<ToolCallbackProvider> callbackProviders,
                          ObjectProvider<ChatCustomizer> chatProvider,
                          ObjectProvider<ChatModel> modelProvider) {
        val chatCustomizer = chatProvider.getIfAvailable();
        val chatModel = modelProvider.getObject();
        val tools = callbackProviders.stream()
                .toArray(ToolCallbackProvider[]::new);
        val builder = ChatClient.builder(chatModel);
        if (chatCustomizer != null)
            chatCustomizer.customize(builder);
        builder.defaultTools(tools);
        return builder.build();
    }
}
