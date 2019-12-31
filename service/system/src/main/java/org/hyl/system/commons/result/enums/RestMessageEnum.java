package org.hyl.system.commons.result.enums;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

public enum RestMessageEnum {
    SUCCESS(HttpStatus.OK.value(), "请求成功"),
    SUCCESS_POST(HttpStatus.CREATED.value(), "新增数据成功"),
    SUCCESS_DELETE(HttpStatus.OK.value(), "删除数据成功"),
    SUCCESS_GET(HttpStatus.OK.value(), "查询数据成功"),
    SUCCESS_PUT(HttpStatus.OK.value(), "修改数据成功"),
    ERROR(HttpStatus.BAD_REQUEST.value(), "请求失败，请稍后再试"),
    ERROR_POST(HttpStatus.BAD_REQUEST.value(), "新增数据失败，请稍后再试"),
    ERROR_DELETE(HttpStatus.BAD_REQUEST.value(), "删除数据失败，请稍后再试"),
    ERROR_GET(HttpStatus.BAD_REQUEST.value(), "查询数据失败，请稍后再试"),
    ERROR_PUT(HttpStatus.BAD_REQUEST.value(), "修改数据失败，请稍后再试");

    private final Integer state;

    private final String message;

    RestMessageEnum(Integer state, String message) {
        this.state = state;
        this.message = message;
    }

    public Integer getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    public static Integer getState(NetworkEnum network, RestTypeEnum type) {
        return RestMessageEnum.getState(network, type, null);
    }

    public static String getMessage(NetworkEnum network, RestTypeEnum type) {
        return RestMessageEnum.getMessage(network, type, null);
    }

    public static Integer getState(NetworkEnum network, Integer state) {
        return RestMessageEnum.getState(network, RestTypeEnum.DEFAULT, state);
    }

    public static String getMessage(NetworkEnum network, String message) {
        return RestMessageEnum.getMessage(network, RestTypeEnum.DEFAULT, message);
    }

    public static Integer getState(NetworkEnum network, RestTypeEnum type, Integer state) {
        if (state != null) {
            return state;
        }
        switch (type) {
            case POST:
                state = NetworkEnum.SUCCESS == network ? RestMessageEnum.SUCCESS_POST.getState() : RestMessageEnum.ERROR_POST.getState();
                break;
            case DELETE:
                state = NetworkEnum.SUCCESS == network ? RestMessageEnum.SUCCESS_DELETE.getState() : RestMessageEnum.ERROR_DELETE.getState();
                break;
            case GET:
                state = NetworkEnum.SUCCESS == network ? RestMessageEnum.SUCCESS_GET.getState() : RestMessageEnum.ERROR_GET.getState();
                break;
            case PUT:
                state = NetworkEnum.SUCCESS == network ? RestMessageEnum.SUCCESS_PUT.getState() : RestMessageEnum.ERROR_PUT.getState();
                break;
            default:
                state = NetworkEnum.SUCCESS == network ? RestMessageEnum.SUCCESS.getState() : RestMessageEnum.ERROR.getState();
        }
        return state;
    }

    public static String getMessage(NetworkEnum network, RestTypeEnum type, String message) {
        if (StringUtils.isNotBlank(message)) {
            return message;
        }
        switch (type) {
            case POST:
                message = NetworkEnum.SUCCESS == network ? RestMessageEnum.SUCCESS_POST.getMessage() : RestMessageEnum.ERROR_POST.getMessage();
                break;
            case DELETE:
                message = NetworkEnum.SUCCESS == network ? RestMessageEnum.SUCCESS_DELETE.getMessage() : RestMessageEnum.ERROR_DELETE.getMessage();
                break;
            case GET:
                message = NetworkEnum.SUCCESS == network ? RestMessageEnum.SUCCESS_GET.getMessage() : RestMessageEnum.ERROR_GET.getMessage();
                break;
            case PUT:
                message = NetworkEnum.SUCCESS == network ? RestMessageEnum.SUCCESS_PUT.getMessage() : RestMessageEnum.ERROR_PUT.getMessage();
                break;
            default:
                message = NetworkEnum.SUCCESS == network ? RestMessageEnum.SUCCESS.getMessage() : RestMessageEnum.ERROR.getMessage();
        }
        return message;
    }
}
