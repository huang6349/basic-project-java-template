package org.hyl.modules.auth.service;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hyl.modules.auth.domain.vm.ResourceVM;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Component("rbacAuthorityService")
public class RbacAuthorityService {

    private final ResourceService resourceService;

    public RbacAuthorityService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Optional<List<ResourceVM>> vms = resourceService.getUserResource();
        if (!vms.isPresent()) {
            return false;
        }
        List<Ignoring> ignorings = Lists.newArrayList();
        ignorings.add(new Ignoring("/api/account/**", HttpMethod.GET.toString()));
        ignorings.add(new Ignoring("/api/authorities/**", HttpMethod.GET.toString()));
        ignorings.add(new Ignoring("/api/dict/**", HttpMethod.GET.toString()));
        ignorings.add(new Ignoring("/api/password/change", HttpMethod.PUT.toString()));
        ignorings.add(new Ignoring("/api/file/upload", HttpMethod.POST.toString()));
        ignorings.add(new Ignoring("/api/file/download/*", HttpMethod.GET.toString()));
        for (ResourceVM vm : vms.get()) {
            ignorings.add(new Ignoring(vm.getPattern(), vm.getMethod_text()));
        }
        boolean hasPermission = false;
        for (Ignoring ignoring : ignorings) {
            AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(ignoring.getPattern(), ignoring.getMethod());
            if (antPathRequestMatcher.matches(request)) {
                hasPermission = true;
                break;
            }
        }
        return hasPermission;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Ignoring {

        private String pattern;

        private String method;
    }
}
