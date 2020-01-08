package org.hyl.system.auditing;

import org.hyl.system.config.Constants;
import org.hyl.system.security.SecurityUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("hylDataAuditorAware")
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityUtils.getCurrentUserUsername().orElse(Constants.SYSTEM_ACCOUNT));
    }
}
