package org.myframework.ai.config;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.noear.solon.Solon;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Slf4j
@Configuration
@AutoConfigureBefore(FrameworkMcp.class)
public class FrameworkAi implements CommandLineRunner {

    @Override
    public void run(String... args) {
        log.debug("启动人工智能");
        val cfg = new String[]{"--cfg=application.yml"};
        Solon.start(FrameworkAi.class, cfg);
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
