package org.huangyalong.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("springSecurityAuditorAware")
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        return Optional.of(GlobalConstants.SYSTEM_ACCOUNT);
    }
}
