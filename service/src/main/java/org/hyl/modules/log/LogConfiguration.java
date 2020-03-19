package org.hyl.modules.log;

import org.hyl.modules.log.aop.LoggingAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class LogConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "hylLoggingAspect")
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
