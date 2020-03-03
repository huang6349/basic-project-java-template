package org.hyl.commons.exception;

import org.hyl.commons.result.Message;
import org.hyl.commons.result.RESTful;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

public interface ThrowableAdviceTrait {

    @ExceptionHandler(BindException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    default Message handleBindException(final BindException e) {
        return RESTful.error(e.getClass().getName(), HttpStatus.BAD_REQUEST.value(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    default Message handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        return RESTful.error(e.getClass().getName(), HttpStatus.BAD_REQUEST.value(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    default Message handleBadCredentialsException(final BadCredentialsException e) {
        return RESTful.error(e.getClass().getName(), HttpStatus.UNAUTHORIZED.value(), "您输入的帐号或者密码不正确，请重试");
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    default Message handleInternalAuthenticationServiceException(final InternalAuthenticationServiceException e) {
        return RESTful.error(e.getClass().getName(), HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    default Message handleInternalServerErrorException(final InternalServerErrorException e) {
        return RESTful.error(e.getType(), e.getClass().getName(), e.getState(), e.getMessage(), e.getData(), e.getParams());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    default Message handleThrowable(final Throwable throwable) {
        return RESTful.error(throwable.getClass().getName());
    }
}
