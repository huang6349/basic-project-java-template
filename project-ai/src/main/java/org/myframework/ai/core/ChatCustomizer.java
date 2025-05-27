package org.myframework.ai.core;

import org.springframework.ai.chat.client.ChatClient;

public interface ChatCustomizer {

    void customize(ChatClient.Builder builder);
}
