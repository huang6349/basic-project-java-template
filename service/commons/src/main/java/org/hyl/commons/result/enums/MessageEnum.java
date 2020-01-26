package org.hyl.commons.result.enums;

import org.apache.commons.lang3.StringUtils;

public enum MessageEnum {
    SUCCESS(200, "请求成功"), ERROR(400, "请求失败，请稍后再试");

    private final Integer state;

    private final String message;

    MessageEnum(Integer state, String message) {
        this.state = state;
        this.message = message;
    }

    public Integer getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    public static Integer getState(NetworkEnum network, Integer state) {
        if (state != null) {
            return state;
        }
        return NetworkEnum.SUCCESS == network ? MessageEnum.SUCCESS.getState() : MessageEnum.ERROR.getState();
    }

    public static String getMessage(NetworkEnum network, String message) {
        if (StringUtils.isNotBlank(message)) {
            return message;
        }
        return NetworkEnum.SUCCESS == network ? MessageEnum.SUCCESS.getMessage() : MessageEnum.ERROR.getMessage();
    }
}
