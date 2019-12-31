package org.hyl.system.errors;

import org.hyl.system.commons.result.RESTful;
import org.hyl.system.commons.result.Message;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(BindException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message handleBindException(final BindException e) {
        return RESTful.error(e.getClass().getName(), HttpStatus.BAD_REQUEST.value(), e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        return RESTful.error(e.getClass().getName(), HttpStatus.BAD_REQUEST.value(), e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message handleInternalServerErrorException(final InternalServerErrorException e) {
        return RESTful.error(e.getType(), e.getClass().getName(), e.getState(), e.getMessage(), e.getData(), e.getParams());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Message handleThrowable(final Throwable throwable) {
        return RESTful.error(throwable.getClass().getName());
    }
}
