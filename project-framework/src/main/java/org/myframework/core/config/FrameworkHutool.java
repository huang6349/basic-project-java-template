package org.myframework.core.config;

import cn.hutool.cron.CronUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PreDestroy;

@Slf4j
@Configuration
@Import(SpringUtil.class)
public class FrameworkHutool implements CommandLineRunner {

    @Override
    public void run(String... args) {
        log.debug("启动定时任务");
        CronUtil.setMatchSecond(Boolean.FALSE);
        CronUtil.start(Boolean.TRUE);
    }

    @PreDestroy
    void destroy() {
        log.debug("停止定时任务");
        CronUtil.stop();
    }
}
