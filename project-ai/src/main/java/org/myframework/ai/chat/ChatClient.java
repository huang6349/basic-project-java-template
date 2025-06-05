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

    private List<Function> defaultTools;

    private HistoriesPrompt prompt;

    private Llm llm;

    public static ChatClientBuilder of(Llm llm) {
        val prompt = new HistoriesPrompt();
        return ChatClient.builder()
                .prompt(prompt)
                .llm(llm);
    }

    public ChatRequest prompt(String content) {
        val message = new HumanMessage(content);
        message.addFunctions(defaultTools);
        prompt.addMessage(message);
        return ChatRequest.builder()
                .prompt(prompt)
                .llm(llm)
                .build();
    }
}
