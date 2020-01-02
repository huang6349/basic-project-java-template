package org.hyl.system.commons.result;

import org.hyl.system.commons.result.enums.RestMessageEnum;
import org.hyl.system.commons.result.enums.RestTypeEnum;
import org.hyl.system.commons.result.enums.NetworkEnum;

public class RestMessageVM extends MessageVM {

    private RestTypeEnum type;

    public RestMessageVM() {
        // Empty constructor needed for Jackson.
    }

    public RestMessageVM(NetworkEnum network, RestTypeEnum type, Integer state, String message, Object data, Object params) {
        super(network, state, message, data, params);
        this.type = type;
    }

    public RestMessageVM(NetworkEnum network, RestTypeEnum type, Integer state, String message, Object data, String e, Object params) {
        super(network, state, message, data, e, params);
        this.type = type;
    }

    public static Message adapt(RestMessageVM vm) {
        NetworkEnum network = vm.getNetwork();
        Message message = new Message();
        message.setState(RestMessageEnum.getState(network, vm.getType(), vm.getState()));
        message.setMessage(RestMessageEnum.getMessage(network, vm.getType(), vm.getMessage()));
        message.setData(vm.getData());
        message.setSuccess(network.isSuccess());
        message.setE(vm.getE());
        message.setParams(vm.getParams());
        return message;
    }

    public RestTypeEnum getType() {
        return type;
    }

    public void setType(RestTypeEnum type) {
        this.type = type;
    }
}