package org.myframework.ai.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("app-chat")
public class ChatProperties {

    private String apiUrl;

    private String apiKey;

    private String provider;

    private String model;
}
