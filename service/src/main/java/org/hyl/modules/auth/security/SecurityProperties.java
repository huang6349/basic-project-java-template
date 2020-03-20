package org.hyl.modules.auth.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("app.jwt")
public class SecurityProperties {

    private String secret;

    private String base64Secret;

    private Long tokenValidityInSeconds = 1800L;
}
