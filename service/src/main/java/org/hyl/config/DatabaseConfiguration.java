package org.hyl.config;

import org.hyl.data.annotations.EnableDataModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("org.hyl.repository")
@EnableDataModule
@EnableTransactionManagement
public class DatabaseConfiguration {
}
