package org.hyl.commons.exception;

import org.hyl.commons.result.enums.RestTypeEnum;
import org.springframework.http.HttpStatus;

public class DataNotAlreadyIDException extends InternalServerErrorException {

    private static final long serialVersionUID = -5050265227748557450L;

    private static final Integer STATE = HttpStatus.CONFLICT.value();

    private static final String MESSAGE = "新增的数据不能存在编号";

    public DataNotAlreadyIDException() {
        super(MESSAGE, RestTypeEnum.POST, STATE);
    }

    public DataNotAlreadyIDException(Object params) {
        super(MESSAGE, RestTypeEnum.POST, STATE, params);
    }

    public DataNotAlreadyIDException(Object params, Object data) {
        super(MESSAGE, RestTypeEnum.POST, STATE, params, data);
    }

    public DataNotAlreadyIDException(String message) {
        super(message, RestTypeEnum.POST, STATE);
    }

    public DataNotAlreadyIDException(String message, Object params) {
        super(message, RestTypeEnum.POST, STATE, params);
    }

    public DataNotAlreadyIDException(String message, Object params, Object data) {
        super(message, RestTypeEnum.POST, STATE, params, data);
    }
}
