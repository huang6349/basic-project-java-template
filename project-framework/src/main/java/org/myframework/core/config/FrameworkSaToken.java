package org.myframework.core.config;

import cn.dev33.satoken.config.SaTokenConfig;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.myframework.core.satoken.interceptor.SaTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class FrameworkSaToken implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaTokenInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    @Primary
    SaTokenConfig saTokenConfig() {
        val config = new SaTokenConfig();
        config.setIsWriteHeader(Boolean.TRUE);
        config.setIsConcurrent(Boolean.TRUE);
        config.setIsShare(Boolean.TRUE);
        config.setIsPrint(Boolean.FALSE);
        return config;
    }
}
