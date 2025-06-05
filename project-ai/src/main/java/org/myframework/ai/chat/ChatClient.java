package org.myframework.ai.chat;

import com.agentsflex.core.llm.Llm;
import com.agentsflex.core.llm.functions.Function;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatClient {

    private List<Function> defaultTools;

    private Llm defaultLlm;

    public ChatRequest prompt(String message) {
        return ChatRequest.builder()
                .defaultTools(defaultTools)
                .llm(defaultLlm)
                .message(message)
                .build();
    }
}
