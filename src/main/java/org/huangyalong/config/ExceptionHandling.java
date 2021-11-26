package org.huangyalong.config;

import cn.dev33.satoken.exception.DisableLoginException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.huangyalong.commons.exception.InternalServerErrorException;
import org.huangyalong.commons.info.Info;
import org.huangyalong.commons.info.InfoStructure;
import org.huangyalong.commons.info.ShowType;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Objects;

@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
public class ExceptionHandling {

    private final MultipartProperties multipartProperties;

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public InfoStructure handleThrowable(final Throwable throwable) {
        return Info.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), throwable.getMessage(), throwable.getClass().getName());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public InfoStructure handleNoHandlerFoundException(final NoHandlerFoundException e) {
        return Info.error(HttpStatus.NOT_FOUND.value(), "请检查请求路径或者类型是否正确", e.getClass().getName());
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public InfoStructure handleBindException(final BindException e) {
        return Info.error(HttpStatus.BAD_REQUEST.value(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage(), e.getClass().getName());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public InfoStructure handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        return Info.error(HttpStatus.BAD_REQUEST.value(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage(), e.getClass().getName());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public InfoStructure handleMaxUploadSizeExceededException(final MaxUploadSizeExceededException e) {
        return Info.error(HttpStatus.BAD_REQUEST.value(), StrUtil.format("上传失败，文件大小超出了最大限制（{}MB）", multipartProperties.getMaxFileSize().toMegabytes()), e.getClass().getName());
    }

    @ExceptionHandler(DisableLoginException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public InfoStructure handleDisableLoginException(final DisableLoginException e) {
        return Info.error(HttpStatus.UNAUTHORIZED.value(), StrUtil.format("您的帐号被封禁，请在 {} 秒后重新登陆", e.getDisableTime()), e.getClass().getName(), ShowType.REDIRECT);
    }

    @ExceptionHandler(NotLoginException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public InfoStructure handleNotLoginException(final NotLoginException e) {
        return Info.error(HttpStatus.UNAUTHORIZED.value(), "您的帐号信息已过期，请重新登陆", e.getClass().getName(), ShowType.REDIRECT);
    }

    @ExceptionHandler(NotPermissionException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public InfoStructure handleNotPermissionException(final NotPermissionException e) {
        return Info.error(HttpStatus.FORBIDDEN.value(), "您没有足够的权限执行该操作", e.getClass().getName());
    }

    @ExceptionHandler(NotRoleException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public InfoStructure handleNotRoleException(final NotRoleException e) {
        return Info.error(HttpStatus.FORBIDDEN.value(), "您没有足够的权限执行该操作", e.getClass().getName());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public InfoStructure handleInternalServerErrorException(final InternalServerErrorException e) {
        return Info.error(e.getErrorCode(), e.getMessage(), e.getClass().getName(), e.getShowType(), e.getTraceId(), e.getHost());
    }
}
