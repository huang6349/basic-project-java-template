package org.hyl.modules.auth.security;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface RbacService {

    boolean hasPermission(Authentication authentication, HttpServletRequest request);
}
