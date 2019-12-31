package org.hyl.errors;

import org.hyl.commons.result.enums.RestTypeEnum;
import org.springframework.http.HttpStatus;

/**
 * 自定义异常：数据已存在
 */
public class DataAlreadyExistException extends InternalServerErrorException {

    private static final long serialVersionUID = 3703844674111252656L;

    private static final Integer STATE = HttpStatus.CONFLICT.value();

    private static final String MESSAGE = "数据已存在，请勿重复添加";

    public DataAlreadyExistException() {
        super(MESSAGE, RestTypeEnum.POST, STATE);
    }

    public DataAlreadyExistException(Object params) {
        super(MESSAGE, RestTypeEnum.POST, STATE, params);
    }

    public DataAlreadyExistException(Object params, Object data) {
        super(MESSAGE, RestTypeEnum.POST, STATE, params, data);
    }

    public DataAlreadyExistException(String message) {
        super(message, RestTypeEnum.POST, STATE);
    }

    public DataAlreadyExistException(String message, Object params) {
        super(message, RestTypeEnum.POST, STATE, params);
    }

    public DataAlreadyExistException(String message, Object params, Object data) {
        super(message, RestTypeEnum.POST, STATE, params, data);
    }
}
