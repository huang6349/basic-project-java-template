package org.hyl.system.errors;

import org.hyl.system.commons.result.RESTful;
import org.hyl.system.commons.result.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 自定义异常处理器
 */
@ControllerAdvice
public class ExceptionHandling {

    private final Logger log = LoggerFactory.getLogger(ExceptionHandling.class);

    @ExceptionHandler(BindException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Message handleBindException(final BindException e) {
        log.info("【全局异常拦截】 BindException: 异常信息 {}", e.getMessage());

        return RESTful.error(e.getClass().getName(), HttpStatus.BAD_REQUEST.value(), e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Message handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.info("【全局异常拦截】 MethodArgumentNotValidException: 异常信息 {}", e.getMessage());

        return RESTful.error(e.getClass().getName(), HttpStatus.BAD_REQUEST.value(), e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Message handleBadCredentialsException(final BadCredentialsException e) {
        log.info("【全局异常拦截】 BadCredentialsException: {}", e.getMessage());

        return RESTful.error(e.getClass().getName(), HttpStatus.UNAUTHORIZED.value(), "您输入的帐号或者密码不正确，请重试");
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Message handleInternalAuthenticationServiceException(final InternalAuthenticationServiceException e) {
        log.info("【全局异常拦截】 InternalAuthenticationServiceException: {}", e.getMessage());

        return RESTful.error(e.getClass().getName(), HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Message handleInternalServerErrorException(final InternalServerErrorException e) {
        log.info("【全局异常拦截】 InternalServerErrorException: {}", e.getMessage());

        return RESTful.error(e.getType(), e.getClass().getName(), e.getState(), e.getMessage(), e.getData(), e.getParams());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Message handleThrowable(final Throwable throwable) {
        log.info("【全局异常拦截】 Throwable: {}", throwable.getMessage());

        return RESTful.error(throwable.getClass().getName());
    }
}
