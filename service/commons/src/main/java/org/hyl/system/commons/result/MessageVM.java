package org.hyl.system.commons.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hyl.system.commons.result.enums.MessageEnum;
import org.hyl.system.commons.result.enums.NetworkEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageVM {

    private NetworkEnum network;

    private Integer state;

    private String message;

    private Object data;

    private String e;

    private Object params;

    MessageVM(NetworkEnum network, Integer state, String message, Object data, Object params) {
        this.network = network;
        this.state = state;
        this.message = message;
        this.data = data;
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
}
