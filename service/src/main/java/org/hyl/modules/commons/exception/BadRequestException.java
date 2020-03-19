package org.hyl.modules.commons.exception;

import org.hyl.modules.commons.result.enums.RestTypeEnum;
import org.springframework.http.HttpStatus;

public class BadRequestException extends InternalServerErrorException {

    private static final long serialVersionUID = -3076250638377926943L;

    private static final Integer STATE = HttpStatus.BAD_REQUEST.value();

    public BadRequestException(String message) {
        super(message, STATE);
    }

    public BadRequestException(String message, RestTypeEnum type) {
        super(message, type, STATE);
    }

    public BadRequestException(String message, RestTypeEnum type, Object params) {
        super(message, type, STATE, params);
    }

    public BadRequestException(String message, RestTypeEnum type, Object params, Object data) {
        super(message, type, STATE, params, data);
    }

    public BadRequestException(String message, Object params) {
        super(message, STATE, params);
    }

    public BadRequestException(String message, Object params, Object data) {
        super(message, STATE, params, data);
    }
}
