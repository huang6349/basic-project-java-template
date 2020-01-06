package org.hyl.system.security;

import org.springframework.security.core.AuthenticationException;

public class UserNotActivatedException extends AuthenticationException {

    private static final long serialVersionUID = 2165673966616782575L;

    private static final String MESSAGE = "您的账号已被禁用，请联系管理员";

    public UserNotActivatedException() {
        super(MESSAGE);
    }

    public UserNotActivatedException(String msg) {
        super(msg);
    }

    public UserNotActivatedException(String msg, Throwable t) {
        super(msg, t);
    }
}
