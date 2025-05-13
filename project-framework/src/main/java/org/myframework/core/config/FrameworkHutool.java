package org.myframework.core.config;

import cn.hutool.cron.CronUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PreDestroy;

@Configuration
@Import(SpringUtil.class)
@Slf4j
public class FrameworkHutool implements CommandLineRunner {

    @Override
    public void run(String... args) {
        log.info("Starting Scheduled task");
        CronUtil.setMatchSecond(Boolean.FALSE);
        CronUtil.start(Boolean.TRUE);
    }

    @PreDestroy
    void destroy() {
        log.info("Stopping Scheduled task");
        CronUtil.stop();
    }
}
