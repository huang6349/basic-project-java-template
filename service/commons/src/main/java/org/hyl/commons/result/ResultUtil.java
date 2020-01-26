package org.hyl.commons.result;

import org.hyl.commons.result.enums.NetworkEnum;

public interface ResultUtil {

    static Message success() {
        return MessageVM.adapt(new MessageVM(NetworkEnum.SUCCESS, null, null, null, null));
    }

    static Message success(Integer state) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.SUCCESS, state, null, null, null));
    }

    static Message success(Integer state, String message) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.SUCCESS, state, message, null, null));
    }

    static Message success(Integer state, String message, Object data) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.SUCCESS, state, message, data, null));
    }

    static Message success(Integer state, String message, Object data, Object params) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.SUCCESS, state, message, data, params));
    }

    static Message success(Integer state, Object data) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.SUCCESS, state, null, data, null));
    }

    static Message success(Integer state, Object data, Object params) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.SUCCESS, state, null, data, params));
    }

    static Message success(String message) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.SUCCESS, null, message, null, null));
    }

    static Message success(String message, Object data) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.SUCCESS, null, message, data, null));
    }

    static Message success(String message, Object data, Object params) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.SUCCESS, null, message, data, params));
    }

    static Message success(Object data) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.SUCCESS, null, null, data, null));
    }

    static Message success(Object data, Object params) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.SUCCESS, null, null, data, params));
    }

    static Message error(String e) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.ERROR, null, null, null, e, null));
    }

    static Message error(String e, Integer state) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.ERROR, state, null, null, e, null));
    }

    static Message error(String e, Integer state, String message) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.ERROR, state, message, null, e, null));
    }

    static Message error(String e, Integer state, String message, Object data) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.ERROR, state, message, data, e, null));
    }

    static Message error(String e, Integer state, String message, Object data, Object params) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.ERROR, state, message, data, e, params));
    }

    static Message error(String e, Integer state, Object data) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.ERROR, state, null, data, e, null));
    }

    static Message error(String e, Integer state, Object data, Object params) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.ERROR, state, null, data, e, params));
    }

    static Message error(String e, String message) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.ERROR, null, message, null, e, null));
    }

    static Message error(String e, String message, Object data) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.ERROR, null, message, data, e, null));
    }

    static Message error(String e, String message, Object data, Object params) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.ERROR, null, message, data, e, params));
    }

    static Message error(String e, Object data) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.ERROR, null, null, data, e, null));
    }

    static Message error(String e, Object data, Object params) {
        return MessageVM.adapt(new MessageVM(NetworkEnum.ERROR, null, null, data, e, params));
    }
}
