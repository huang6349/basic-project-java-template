package org.myframework.ai.tool;

import com.agentsflex.core.llm.functions.Function;

import java.util.Collection;

public interface ToolProvider {

    Collection<Function> getTools();
}
