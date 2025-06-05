package org.myframework.ai.chat;

import com.agentsflex.core.llm.Llm;
import com.agentsflex.core.llm.functions.Function;
import com.agentsflex.core.llm.response.AiMessageResponse;
import com.agentsflex.core.prompt.FunctionPrompt;
import com.agentsflex.core.prompt.ToolPrompt;
import lombok.Builder;
import lombok.Data;
import lombok.val;

import java.util.List;

@Data
@Builder
public class ChatRequest {

    private List<Function> defaultTools;

    private Llm llm;

    private String message;

    public AiMessageResponse call() {
        val prompt = new FunctionPrompt(message, defaultTools);
        val res = llm.chat(prompt);
        if (res.isFunctionCall()) {
            return llm.chat(ToolPrompt.of(res));
        } else return res;
    }
}
