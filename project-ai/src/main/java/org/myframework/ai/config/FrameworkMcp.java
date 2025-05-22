package org.myframework.ai.config;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.myframework.ai.IMcpServerEndpoint;
import org.noear.solon.Solon;
import org.noear.solon.ai.chat.tool.MethodToolProvider;
import org.noear.solon.ai.mcp.server.McpServerEndpointProvider;
import org.noear.solon.ai.mcp.server.annotation.McpServerEndpoint;
import org.noear.solon.ai.mcp.server.prompt.MethodPromptProvider;
import org.noear.solon.ai.mcp.server.resource.MethodResourceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.PreDestroy;
import java.util.List;

@Slf4j
@Configuration
public class FrameworkMcp {

    @Bean("startFrameworkMcp")
    FrameworkMcp frameworkMcp(List<IMcpServerEndpoint> serverEndpoints) {
        log.debug("启动人工智能");
        Solon.start(FrameworkMcp.class, new String[]{"--cfg=application.yml"});
        serverEndpoints.forEach(serverEndpoint -> {
            val aClass = serverEndpoint.getClass();
            val anno = AnnotationUtils.findAnnotation(aClass, McpServerEndpoint.class);
            if (anno != null) {
                val provider = McpServerEndpointProvider.builder()
                        .from(serverEndpoint.getClass(), anno)
                        .build();
                provider.addTool(new MethodToolProvider(provider));
                provider.addResource(new MethodResourceProvider(provider));
                provider.addPrompt(new MethodPromptProvider(provider));
                provider.postStart();
            }
        });
        return this;
    }

    @PreDestroy
    void destroy() {
        log.debug("停止人工智能");
        if (Solon.app() != null) {
            val delay = Solon.cfg().stopDelay();
            Solon.stopBlock(Boolean.FALSE, delay);
        }
    }
}
