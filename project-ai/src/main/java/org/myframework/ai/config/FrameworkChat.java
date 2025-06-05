package org.myframework.ai.config;

import com.agentsflex.llm.ollama.OllamaLlm;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.myframework.ai.chat.ChatClient;
import org.myframework.ai.tool.MethodToolProvider;
import org.myframework.ai.tool.ToolService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

@Slf4j
@Configuration
public class FrameworkChat {

    @Bean
    ChatClient chatClient(ObjectProvider<ToolService> toolProviders,
                          ObjectProvider<OllamaLlm> llmProvider) {
        val tools = toolProviders.stream()
                .map(MethodToolProvider::new)
                .map(MethodToolProvider::getTools)
                .flatMap(Collection::stream)
                .toList();
        return ChatClient.of(llmProvider.getObject())
                .defaultTools(tools)
                .build();
    }
}
