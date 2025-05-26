package org.myframework.ai.web;

import lombok.Getter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "LombokGetterMayBeUsed"})
public abstract class OllamaController {

    @Autowired
    @Getter
    protected ChatClient chatClient;
}
