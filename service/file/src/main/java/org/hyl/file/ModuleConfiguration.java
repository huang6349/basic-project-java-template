package org.hyl.file;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "org.hyl.file")
@EnableJpaRepositories(basePackages = "org.hyl.file.repository")
@EntityScan(basePackages = "org.hyl.file.domain")
public class ModuleConfiguration {
}
