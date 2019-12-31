package org.hyl.data.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "hylDataAuditorAware")
public class DataConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "hylDataAuditorAware")
    public AuditorAware<String> auditorAware() {
        return new DataAuditorAware();
    }
}
