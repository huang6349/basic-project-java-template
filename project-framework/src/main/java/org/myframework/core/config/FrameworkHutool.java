package org.myframework.core.config;

import cn.hutool.cron.CronUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.log.StaticLog;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PreDestroy;

@Configuration
@Import(SpringUtil.class)
public class FrameworkHutool implements CommandLineRunner {

    @Override
    public void run(String... args) {
        StaticLog.trace("启动定时任务");
        CronUtil.setMatchSecond(Boolean.FALSE);
        CronUtil.start(Boolean.TRUE);
    }

    @PreDestroy
    void destroy() {
        StaticLog.trace("停止定时任务");
        CronUtil.stop();
    }
}
