package org.hyl.system.config;

import org.hyl.data.annotations.EnableDataModule;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "org.hyl.system.repository")
@EntityScan(basePackages = "org.hyl.system.domain")
@EnableDataModule
@EnableTransactionManagement
public class DatabaseConfiguration {
}
