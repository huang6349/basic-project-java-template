package org.hyl.dict;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "org.hyl.dict")
@EnableJpaRepositories(basePackages = "org.hyl.dict.repository")
@EntityScan(basePackages = "org.hyl.dict.domain")
public class ModuleConfiguration {
}
