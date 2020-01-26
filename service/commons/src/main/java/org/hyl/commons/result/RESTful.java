package org.hyl.commons.result;

import org.hyl.commons.result.enums.NetworkEnum;
import org.hyl.commons.result.enums.RestTypeEnum;

public interface RESTful {

    static Message success() {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, RestTypeEnum.DEFAULT, null, null, null, null));
    }

    static Message success(Integer state) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, RestTypeEnum.DEFAULT, state, null, null, null));
    }

    static Message success(Integer state, String message) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, RestTypeEnum.DEFAULT, state, message, null, null));
    }

    static Message success(Integer state, String message, Object data) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, RestTypeEnum.DEFAULT, state, message, data, null));
    }

    static Message success(Integer state, String message, Object data, Object params) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, RestTypeEnum.DEFAULT, state, message, data, params));
    }

    static Message success(Integer state, Object data) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, RestTypeEnum.DEFAULT, state, null, data, null));
    }

    static Message success(Integer state, Object data, Object params) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, RestTypeEnum.DEFAULT, state, null, data, params));
    }

    static Message success(String message) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, RestTypeEnum.DEFAULT, null, message, null, null));
    }

    static Message success(String message, Object data) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, RestTypeEnum.DEFAULT, null, message, data, null));
    }

    static Message success(String message, Object data, Object params) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, RestTypeEnum.DEFAULT, null, message, data, params));
    }

    static Message success(Object data) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, RestTypeEnum.DEFAULT, null, null, data, null));
    }

    static Message success(Object data, Object params) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, RestTypeEnum.DEFAULT, null, null, data, params));
    }

    static Message success(RestTypeEnum type) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, type, null, null, null, null));
    }

    static Message success(RestTypeEnum type, Integer state) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, type, state, null, null, null));
    }

    static Message success(RestTypeEnum type, Integer state, String message) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, type, state, message, null, null));
    }

    static Message success(RestTypeEnum type, Integer state, String message, Object data) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, type, state, message, data, null));
    }

    static Message success(RestTypeEnum type, Integer state, String message, Object data, Object params) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, type, state, message, data, params));
    }

    static Message success(RestTypeEnum type, Integer state, Object data) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, type, state, null, data, null));
    }

    static Message success(RestTypeEnum type, Integer state, Object data, Object params) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, type, state, null, data, params));
    }

    static Message success(RestTypeEnum type, String message) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, type, null, message, null, null));
    }

    static Message success(RestTypeEnum type, String message, Object data) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, type, null, message, data, null));
    }

    static Message success(RestTypeEnum type, String message, Object data, Object params) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, type, null, message, data, params));
    }

    static Message success(RestTypeEnum type, Object data) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, type, null, null, data, null));
    }

    static Message success(RestTypeEnum type, Object data, Object params) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.SUCCESS, type, null, null, data, params));
    }

    static Message error(String e) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, RestTypeEnum.DEFAULT, null, null, null, e, null));
    }

    static Message error(String e, Integer state) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, RestTypeEnum.DEFAULT, state, null, null, e, null));
    }

    static Message error(String e, Integer state, String message) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, RestTypeEnum.DEFAULT, state, message, null, e, null));
    }

    static Message error(String e, Integer state, String message, Object data) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, RestTypeEnum.DEFAULT, state, message, data, e, null));
    }

    static Message error(String e, Integer state, String message, Object data, Object params) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, RestTypeEnum.DEFAULT, state, message, data, e, params));
    }

    static Message error(String e, Integer state, Object data) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, RestTypeEnum.DEFAULT, state, null, data, e, null));
    }

    static Message error(String e, Integer state, Object data, Object params) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, RestTypeEnum.DEFAULT, state, null, data, e, params));
    }

    static Message error(String e, String message) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, RestTypeEnum.DEFAULT, null, message, null, e, null));
    }

    static Message error(String e, String message, Object data) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, RestTypeEnum.DEFAULT, null, message, data, e, null));
    }

    static Message error(String e, String message, Object data, Object params) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, RestTypeEnum.DEFAULT, null, message, data, e, params));
    }

    static Message error(String e, Object data) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, RestTypeEnum.DEFAULT, null, null, data, e, null));
    }

    static Message error(String e, Object data, Object params) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, RestTypeEnum.DEFAULT, null, null, data, e, params));
    }

    static Message error(RestTypeEnum type, String e) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, type, null, null, null, e, null));
    }

    static Message error(RestTypeEnum type, String e, Integer state) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, type, state, null, null, e, null));
    }

    static Message error(RestTypeEnum type, String e, Integer state, String message) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, type, state, message, null, e, null));
    }

    static Message error(RestTypeEnum type, String e, Integer state, String message, Object data) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, type, state, message, data, e, null));
    }

    static Message error(RestTypeEnum type, String e, Integer state, String message, Object data, Object params) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, type, state, message, data, e, params));
    }

    static Message error(RestTypeEnum type, String e, Integer state, Object data) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, type, state, null, data, e, null));
    }

    static Message error(RestTypeEnum type, String e, Integer state, Object data, Object params) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, type, state, null, data, e, params));
    }

    static Message error(RestTypeEnum type, String e, String message) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, type, null, message, null, e, null));
    }

    static Message error(RestTypeEnum type, String e, String message, Object data) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, type, null, message, data, e, null));
    }

    static Message error(RestTypeEnum type, String e, String message, Object data, Object params) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, type, null, message, data, e, params));
    }

    static Message error(RestTypeEnum type, String e, Object data) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, type, null, null, data, e, null));
    }

    static Message error(RestTypeEnum type, String e, Object data, Object params) {
        return RestMessageVM.adapt(new RestMessageVM(NetworkEnum.ERROR, type, null, null, data, e, params));
    }
}
