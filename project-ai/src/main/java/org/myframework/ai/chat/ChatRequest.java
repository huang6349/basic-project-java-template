package org.myframework.ai.chat;

import com.agentsflex.core.llm.Llm;
import com.agentsflex.core.llm.response.AiMessageResponse;
import com.agentsflex.core.prompt.Prompt;
import com.agentsflex.core.prompt.ToolPrompt;
import lombok.Builder;
import lombok.Data;
import lombok.val;

@Data
@Builder
public class ChatRequest {

    private Prompt prompt;

    private Llm llm;

    public AiMessageResponse call() {
        val res = llm.chat(prompt);
        if (res.isFunctionCall()) {
            return llm.chat(ToolPrompt.of(res));
        } else return res;
    }
}
