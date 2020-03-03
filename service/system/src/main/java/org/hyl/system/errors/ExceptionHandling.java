package org.hyl.system.errors;

import cn.hutool.core.util.StrUtil;
import org.hyl.commons.exception.ThrowableAdviceTrait;
import org.hyl.commons.result.Message;
import org.hyl.commons.result.RESTful;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class ExceptionHandling implements ThrowableAdviceTrait {

    private final MultipartProperties multipartProperties;

    public ExceptionHandling(MultipartProperties multipartProperties) {
        this.multipartProperties = multipartProperties;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message handleMaxUploadSizeExceededException(final MaxUploadSizeExceededException e) {
        return RESTful.error(e.getClass().getName(), HttpStatus.BAD_REQUEST.value(), StrUtil.format("上传失败，文件大小超出了最大限制（{}MB）", multipartProperties.getMaxFileSize().toMegabytes()));
    }
}
