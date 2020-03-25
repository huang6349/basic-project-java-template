package org.hyl.modules.auth.security;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hyl.modules.auth.service.ResourceService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component("rbacService")
public class DefaultRbacService implements RbacService {

    private final HttpServletRequest httpServletRequest;

    private final SecurityProperties securityProperties;

    private final ResourceService resourceService;

    public DefaultRbacService(HttpServletRequest httpServletRequest, SecurityProperties securityProperties, ResourceService resourceService) {
        this.httpServletRequest = httpServletRequest;
        this.securityProperties = securityProperties;
        this.resourceService = resourceService;
    }

    @Override
    public boolean hasPermission() {
        if (!SecurityUtils.isAuthenticated()) {
            return false;
        }
        List<RbacMatcher> matchers = Lists.newArrayList();
        securityProperties.getRbacIgnorings().forEach(ignoring -> matchers.add(new RbacMatcher(ignoring.get(1), ignoring.get(0))));
        resourceService.getUserResource().ifPresent(vms -> vms.forEach(vm -> {
            matchers.add(new RbacMatcher(vm.getPattern(), vm.getMethod_text()));
        }));
        for (RbacMatcher matcher : matchers) {
            if (new AntPathRequestMatcher(matcher.getPattern(), matcher.getMethod()).matches(httpServletRequest)) {
                return true;
            }
        }
        return false;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class RbacMatcher {

        private String pattern;

        private String method;
    }
}
