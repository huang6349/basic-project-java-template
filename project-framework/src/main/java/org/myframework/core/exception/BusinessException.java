package org.myframework.core.exception;

import lombok.Getter;
import lombok.Setter;
import org.myframework.base.response.ShowType;

import static cn.hutool.core.text.CharSequenceUtil.format;
import static org.myframework.core.exception.ErrorCode.ERR_BUSINESS;

@Getter
@Setter
public class BusinessException extends RuntimeException {

    private Integer errorCode;

    private ShowType showType;

    private String traceId;

    private String host;

    public BusinessException() {
        super(ERR_BUSINESS.getMessage());
        this.errorCode = ERR_BUSINESS.getCode();
        this.showType = ShowType.ERROR_MESSAGE;
    }

    public BusinessException(String message) {
        super(message);
        this.errorCode = ERR_BUSINESS.getCode();
        this.showType = ShowType.ERROR_MESSAGE;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.getCode();
        this.showType = ShowType.ERROR_MESSAGE;
    }

    public BusinessException(Integer errorCode,
                             String message) {
        super(message);
        this.errorCode = errorCode;
        this.showType = ShowType.ERROR_MESSAGE;
    }

    public BusinessException(String template,
                             Object... args) {
        super(format(template, args));
        this.errorCode = ERR_BUSINESS.getCode();
        this.showType = ShowType.ERROR_MESSAGE;
    }

    public BusinessException(ErrorCode errorCode,
                             ShowType showType) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.getCode();
        this.showType = showType;
    }

    public BusinessException(Integer errorCode,
                             String message,
                             ShowType showType) {
        super(message);
        this.errorCode = errorCode;
        this.showType = showType;
    }

    public BusinessException(String message,
                             String traceId,
                             String host) {
        super(message);
        this.errorCode = ERR_BUSINESS.getCode();
        this.showType = ShowType.ERROR_MESSAGE;
        this.traceId = traceId;
        this.host = host;
    }

    public BusinessException(ErrorCode errorCode,
                             String traceId,
                             String host) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.getCode();
        this.showType = ShowType.ERROR_MESSAGE;
        this.traceId = traceId;
        this.host = host;
    }

    public BusinessException(Integer errorCode,
                             String message,
                             String traceId,
                             String host) {
        super(message);
        this.errorCode = errorCode;
        this.showType = ShowType.ERROR_MESSAGE;
        this.traceId = traceId;
        this.host = host;
    }

    public BusinessException(String message,
                             ShowType showType,
                             String traceId,
                             String host) {
        super(message);
        this.errorCode = ERR_BUSINESS.getCode();
        this.showType = showType;
        this.traceId = traceId;
        this.host = host;
    }

    public BusinessException(ErrorCode errorCode,
                             ShowType showType,
                             String traceId,
                             String host) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.getCode();
        this.showType = showType;
        this.traceId = traceId;
        this.host = host;
    }

    public BusinessException(Integer errorCode,
                             String message,
                             ShowType showType,
                             String traceId,
                             String host) {
        super(message);
        this.errorCode = errorCode;
        this.showType = showType;
        this.traceId = traceId;
        this.host = host;
    }
}
