package org.hyl.commons.result;

import org.hyl.commons.result.enums.MessageEnum;
import org.hyl.commons.result.enums.NetworkEnum;

public class MessageVM {

    private NetworkEnum network;

    private Integer state;

    private String message;

    private Object data;

    private String e;

    private Object params;

    public MessageVM() {
        // Empty constructor needed for Jackson.
    }

    public MessageVM(NetworkEnum network, Integer state, String message, Object data, Object params) {
        this.network = network;
        this.state = state;
        this.message = message;
        this.data = data;
        this.params = params;
    }

    public MessageVM(NetworkEnum network, Integer state, String message, Object data, String e, Object params) {
        this.network = network;
        this.state = state;
        this.message = message;
        this.data = data;
        this.e = e;
        this.params = params;
    }

    public static Message adapt(MessageVM vm) {
        NetworkEnum network = vm.getNetwork();
        Message message = new Message();
        message.setState(MessageEnum.getState(network, vm.getState()));
        message.setMessage(MessageEnum.getMessage(network, vm.getMessage()));
        message.setData(vm.getData());
        message.setSuccess(network.isSuccess());
        message.setE(vm.getE());
        message.setParams(vm.getParams());
        return message;
    }

    public NetworkEnum getNetwork() {
        return network;
    }

    public void setNetwork(NetworkEnum network) {
        this.network = network;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }
}
