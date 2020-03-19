package org.hyl.config;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.hyl.modules.commons.exception.InternalServerErrorException;
import org.hyl.modules.commons.result.Message;
import org.hyl.modules.commons.result.RESTful;
import org.hyl.modules.commons.utils.ThrowableUtil;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class ExceptionHandling {

    private final MultipartProperties multipartProperties;

    public ExceptionHandling(MultipartProperties multipartProperties) {
        this.multipartProperties = multipartProperties;
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Message handleThrowable(final Throwable throwable) {
        log.error(ThrowableUtil.getStackTrace(throwable));
        return RESTful.error(throwable.getClass().getName());
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message handleBindException(final BindException e) {
        return RESTful.error(e.getClass().getName(), HttpStatus.BAD_REQUEST.value(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        return RESTful.error(e.getClass().getName(), HttpStatus.BAD_REQUEST.value(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message handleMaxUploadSizeExceededException(final MaxUploadSizeExceededException e) {
        return RESTful.error(e.getClass().getName(), HttpStatus.BAD_REQUEST.value(), StrUtil.format("上传失败，文件大小超出了最大限制（{}MB）", multipartProperties.getMaxFileSize().toMegabytes()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message handleBadCredentialsException(final BadCredentialsException e) {
        return RESTful.error(e.getClass().getName(), HttpStatus.UNAUTHORIZED.value(), "您输入的帐号或者密码不正确，请重试");
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message handleInternalAuthenticationServiceException(final InternalAuthenticationServiceException e) {
        return RESTful.error(e.getClass().getName(), HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message handleInternalServerErrorException(final InternalServerErrorException e) {
        return RESTful.error(e.getType(), e.getClass().getName(), e.getState(), e.getMessage(), e.getData(), e.getParams());
    }
}
