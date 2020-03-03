package org.hyl.commons.exception;

import org.hyl.commons.result.enums.RestTypeEnum;
import org.springframework.http.HttpStatus;

public class DataAlreadyIDException extends InternalServerErrorException {

    private static final long serialVersionUID = -5050265227748557450L;

    private static final Integer STATE = HttpStatus.CONFLICT.value();

    private static final String MESSAGE = "数据编号已存在，请勿重复添加";

    public DataAlreadyIDException() {
        super(MESSAGE, RestTypeEnum.POST, STATE);
    }

    public DataAlreadyIDException(Object params) {
        super(MESSAGE, RestTypeEnum.POST, STATE, params);
    }

    public DataAlreadyIDException(Object params, Object data) {
        super(MESSAGE, RestTypeEnum.POST, STATE, params, data);
    }

    public DataAlreadyIDException(String message) {
        super(message, RestTypeEnum.POST, STATE);
    }

    public DataAlreadyIDException(String message, Object params) {
        super(message, RestTypeEnum.POST, STATE, params);
    }

    public DataAlreadyIDException(String message, Object params, Object data) {
        super(message, RestTypeEnum.POST, STATE, params, data);
    }
}
