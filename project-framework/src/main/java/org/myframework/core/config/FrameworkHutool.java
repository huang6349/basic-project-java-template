package org.myframework.core.config;

import cn.hutool.cron.CronUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PreDestroy;

@Configuration
@Import(SpringUtil.class)
public class FrameworkHutool implements CommandLineRunner {

    @Override
    public void run(String... args) {
        CronUtil.setMatchSecond(Boolean.FALSE);
        CronUtil.start(Boolean.TRUE);
    }

    @PreDestroy
    public void destroy() {
        CronUtil.stop();
    }
}
