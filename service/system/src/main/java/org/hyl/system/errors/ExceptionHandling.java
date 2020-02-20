package org.hyl.system.errors;

import cn.hutool.core.util.StrUtil;
import org.hyl.commons.result.Message;
import org.hyl.commons.result.RESTful;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.Objects;

@ControllerAdvice
public class ExceptionHandling {

    private final Logger log = LoggerFactory.getLogger(ExceptionHandling.class);

    private final MultipartProperties multipartProperties;

    public ExceptionHandling(MultipartProperties multipartProperties) {
        this.multipartProperties = multipartProperties;
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message handleBindException(final BindException e) {
        log.info("【全局异常拦截】 BindException: 异常信息 {}", e.getMessage());

        return RESTful.error(e.getClass().getName(), HttpStatus.BAD_REQUEST.value(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.info("【全局异常拦截】 MethodArgumentNotValidException: 异常信息 {}", e.getMessage());

        return RESTful.error(e.getClass().getName(), HttpStatus.BAD_REQUEST.value(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message handleBadCredentialsException(final BadCredentialsException e) {
        log.info("【全局异常拦截】 BadCredentialsException: {}", e.getMessage());

        return RESTful.error(e.getClass().getName(), HttpStatus.UNAUTHORIZED.value(), "您输入的帐号或者密码不正确，请重试");
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message handleInternalAuthenticationServiceException(final InternalAuthenticationServiceException e) {
        log.info("【全局异常拦截】 InternalAuthenticationServiceException: {}", e.getMessage());

        return RESTful.error(e.getClass().getName(), HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message handleMaxUploadSizeExceededException(final MaxUploadSizeExceededException e) {
        log.info("【全局异常拦截】 MaxUploadSizeExceededException: {}", e.getMessage());

        return RESTful.error(e.getClass().getName(), HttpStatus.BAD_REQUEST.value(), StrUtil.format("上传失败，文件大小超出了最大限制（{}MB）", multipartProperties.getMaxFileSize().toMegabytes()));
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
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
