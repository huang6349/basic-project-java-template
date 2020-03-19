package org.hyl.modules.commons.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hyl.modules.commons.result.enums.NetworkEnum;
import org.hyl.modules.commons.result.enums.RestMessageEnum;
import org.hyl.modules.commons.result.enums.RestTypeEnum;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestMessageVM extends MessageVM {

    private RestTypeEnum type;

    RestMessageVM(NetworkEnum network, RestTypeEnum type, Integer state, String message, Object data, Object params) {
        super(network, state, message, data, params);
        this.type = type;
    }

    RestMessageVM(NetworkEnum network, RestTypeEnum type, Integer state, String message, Object data, String e, Object params) {
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
}
