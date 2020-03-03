package org.hyl.system.auditing;

import org.hyl.commons.config.GlobalConstants;
import org.hyl.system.security.SecurityUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("hylDataAuditorAware")
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityUtils.getCurrentUserUsername().orElse(GlobalConstants.SYSTEM_ACCOUNT));
    }
}
