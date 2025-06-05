package org.myframework.ai.chat;

import com.agentsflex.core.llm.Llm;
import com.agentsflex.core.llm.functions.Function;
import com.agentsflex.core.message.HumanMessage;
import com.agentsflex.core.prompt.HistoriesPrompt;
import lombok.Builder;
import lombok.Data;
import lombok.val;

import java.util.List;

@Data
@Builder
public class ChatClient {

    private final HistoriesPrompt prompt = new HistoriesPrompt();

    private List<Function> defaultTools;

    private Llm defaultLlm;

    public ChatRequest prompt(String content) {
        val message = new HumanMessage(content);
        message.addFunctions(defaultTools);
        prompt.addMessage(message);
        return ChatRequest.builder()
                .prompt(prompt)
                .llm(defaultLlm)
                .build();
    }
}
