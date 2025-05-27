package org.myframework.ai.web;

import jakarta.annotation.Resource;
import lombok.Getter;
import org.springframework.ai.chat.client.ChatClient;

@SuppressWarnings("LombokGetterMayBeUsed")
public abstract class OllamaController {

    @Resource
    @Getter
    protected ChatClient chatClient;
}
