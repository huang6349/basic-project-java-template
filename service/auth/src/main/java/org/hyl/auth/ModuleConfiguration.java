package org.hyl.auth;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "org.hyl.auth")
@EnableJpaRepositories(basePackages = "org.hyl.auth.repository")
@EntityScan(basePackages = "org.hyl.auth.domain")
public class ModuleConfiguration {
}
