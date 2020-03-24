package org.hyl.modules.auth.security;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties("app.jwt")
public class SecurityProperties {

    private String secret;

    private String base64Secret;

    private Long tokenValidityInSeconds = 1800L;

    private List<List<String>> rbacIgnorings = Lists.newArrayList();
}
