package org.hyl.modules.auth.security;

import org.hyl.modules.auth.domain.vm.ResourceVM;
import org.hyl.modules.auth.service.ResourceService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

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
        for (List<String> rbacIgnoring : securityProperties.getRbacIgnorings()) {
            if (new AntPathRequestMatcher(rbacIgnoring.get(1), rbacIgnoring.get(0)).matches(httpServletRequest)) {
                return true;
            }
        }
        Optional<List<ResourceVM>> optional = resourceService.getUserResource();
        if (!optional.isPresent()) {
            return false;
        }
        for (ResourceVM vm : optional.get()) {
            if (new AntPathRequestMatcher(vm.getPattern(), vm.getMethod_text()).matches(httpServletRequest)) {
                return true;
            }
        }
        return false;
    }
}
