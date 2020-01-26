package org.hyl.commons.result;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.assertj.core.api.Assertions;
import org.hyl.commons.result.enums.NetworkEnum;
import org.hyl.commons.result.enums.RestMessageEnum;
import org.hyl.commons.result.enums.RestTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class RESTfulTest {

    private Integer state;

    private String message;

    private List<Integer> data = Lists.newArrayList();

    private String e;

    private Map<String, Integer> params = Maps.newHashMap();

    @BeforeEach
    public void setUp() {
        this.state = new Random().nextInt(400) + 100;
        this.message = "REST_TEST";
        this.data = Lists.newArrayList();
        this.data.add(new Random().nextInt());
        this.data.add(new Random().nextInt());
        this.data.add(new Random().nextInt());
        this.e = new RuntimeException("REST_ERROR").getMessage();
        this.params = Maps.newHashMap();
        this.params.put("id", new Random().nextInt(100));
    }

    private void compare(Message a, Message b) {
        Assertions.assertThat(a.getState()).isEqualTo(b.getState());
        Assertions.assertThat(a.getMessage()).isEqualTo(b.getMessage());
        Assertions.assertThat(a.getData()).isEqualTo(b.getData());
        Assertions.assertThat(a.getSuccess()).isEqualTo(b.getSuccess());
        Assertions.assertThat(a.getE()).isEqualTo(b.getE());
        Assertions.assertThat(a.getParams()).isEqualTo(b.getParams());
    }

    @Test
    public void success0() {
        Message a = new Message();
        a.setState(RestMessageEnum.SUCCESS.getState());
        a.setMessage(RestMessageEnum.SUCCESS.getMessage());
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        compare(a, RESTful.success());
    }

    @Test
    public void success1() {
        Message a = new Message();
        a.setState(state);
        a.setMessage(RestMessageEnum.SUCCESS.getMessage());
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        compare(a, RESTful.success(state));
    }

    @Test
    public void success2() {
        Message a = new Message();
        a.setState(state);
        a.setMessage(message);
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        compare(a, RESTful.success(state, message));
    }

    @Test
    public void success3() {
        Message a = new Message();
        a.setState(state);
        a.setMessage(message);
        a.setData(data);
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        compare(a, RESTful.success(state, message, data));
    }

    @Test
    public void success4() {
        Message a = new Message();
        a.setState(state);
        a.setMessage(message);
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        a.setData(data);
        a.setParams(params);
        compare(a, RESTful.success(state, message, data, params));
    }

    @Test
    public void success5() {
        Message a = new Message();
        a.setState(state);
        a.setMessage(RestMessageEnum.SUCCESS.getMessage());
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        a.setData(data);
        compare(a, RESTful.success(state, data));
    }

    @Test
    public void success6() {
        Message a = new Message();
        a.setState(state);
        a.setMessage(RestMessageEnum.SUCCESS.getMessage());
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        a.setData(data);
        a.setParams(params);
        compare(a, RESTful.success(state, data, params));
    }

    @Test
    public void success7() {
        Message a = new Message();
        a.setState(RestMessageEnum.SUCCESS.getState());
        a.setMessage(message);
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        compare(a, RESTful.success(message));
    }

    @Test
    public void success8() {
        Message a = new Message();
        a.setState(RestMessageEnum.SUCCESS.getState());
        a.setMessage(message);
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        a.setData(data);
        compare(a, RESTful.success(message, data));
    }

    @Test
    public void success9() {
        Message a = new Message();
        a.setState(RestMessageEnum.SUCCESS.getState());
        a.setMessage(message);
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        a.setData(data);
        a.setParams(params);
        compare(a, RESTful.success(message, data, params));
    }

    @Test
    public void success10() {
        Message a = new Message();
        a.setState(RestMessageEnum.SUCCESS.getState());
        a.setMessage(RestMessageEnum.SUCCESS.getMessage());
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        a.setData(data);
        compare(a, RESTful.success(data));
    }

    @Test
    public void success11() {
        Message a = new Message();
        a.setState(RestMessageEnum.SUCCESS.getState());
        a.setMessage(RestMessageEnum.SUCCESS.getMessage());
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        a.setData(data);
        a.setParams(params);
        compare(a, RESTful.success(data, params));
    }

    @Test
    public void success12() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setState(RestMessageEnum.getState(NetworkEnum.SUCCESS, type));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.SUCCESS, type));
            a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
            compare(a, RESTful.success(type));
        }
    }

    @Test
    public void success13() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setState(RestMessageEnum.getState(NetworkEnum.SUCCESS, type, state));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.SUCCESS, type));
            a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
            compare(a, RESTful.success(type, state));
        }
    }

    @Test
    public void success14() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setState(RestMessageEnum.getState(NetworkEnum.SUCCESS, type, state));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.SUCCESS, type, message));
            a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
            compare(a, RESTful.success(type, state, message));
        }
    }

    @Test
    public void success15() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setState(RestMessageEnum.getState(NetworkEnum.SUCCESS, type, state));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.SUCCESS, type, message));
            a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
            a.setData(data);
            compare(a, RESTful.success(type, state, message, data));
        }
    }

    @Test
    public void success16() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setState(RestMessageEnum.getState(NetworkEnum.SUCCESS, type, state));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.SUCCESS, type, message));
            a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
            a.setData(data);
            a.setParams(params);
            compare(a, RESTful.success(type, state, message, data, params));
        }
    }

    @Test
    public void success17() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setState(RestMessageEnum.getState(NetworkEnum.SUCCESS, type, state));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.SUCCESS, type));
            a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
            a.setData(data);
            compare(a, RESTful.success(type, state, data));
        }
    }

    @Test
    public void success18() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setState(RestMessageEnum.getState(NetworkEnum.SUCCESS, type, state));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.SUCCESS, type));
            a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
            a.setData(data);
            a.setParams(params);
            compare(a, RESTful.success(type, state, data, params));
        }
    }

    @Test
    public void success19() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setState(RestMessageEnum.getState(NetworkEnum.SUCCESS, type));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.SUCCESS, type, message));
            a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
            compare(a, RESTful.success(type, message));
        }
    }

    @Test
    public void success20() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setState(RestMessageEnum.getState(NetworkEnum.SUCCESS, type));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.SUCCESS, type, message));
            a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
            a.setData(data);
            compare(a, RESTful.success(type, message, data));
        }
    }

    @Test
    public void success21() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setState(RestMessageEnum.getState(NetworkEnum.SUCCESS, type));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.SUCCESS, type, message));
            a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
            a.setData(data);
            a.setParams(params);
            compare(a, RESTful.success(type, message, data, params));
        }
    }

    @Test
    public void success22() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setState(RestMessageEnum.getState(NetworkEnum.SUCCESS, type));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.SUCCESS, type));
            a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
            a.setData(data);
            compare(a, RESTful.success(type, data));
        }
    }

    @Test
    public void success23() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setState(RestMessageEnum.getState(NetworkEnum.SUCCESS, type));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.SUCCESS, type));
            a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
            a.setData(data);
            a.setParams(params);
            compare(a, RESTful.success(type, data, params));
        }
    }

    @Test
    public void error0() {
        Message a = new Message();
        a.setE(e);
        a.setState(RestMessageEnum.ERROR.getState());
        a.setMessage(RestMessageEnum.ERROR.getMessage());
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        compare(a, RESTful.error(e));
    }

    @Test
    public void error1() {
        Message a = new Message();
        a.setE(e);
        a.setState(state);
        a.setMessage(RestMessageEnum.ERROR.getMessage());
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        compare(a, RESTful.error(e, state));
    }

    @Test
    public void error2() {
        Message a = new Message();
        a.setE(e);
        a.setState(state);
        a.setMessage(message);
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        compare(a, RESTful.error(e, state, message));
    }

    @Test
    public void error3() {
        Message a = new Message();
        a.setE(e);
        a.setState(state);
        a.setMessage(message);
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        a.setData(data);
        compare(a, RESTful.error(e, state, message, data));
    }

    @Test
    public void error4() {
        Message a = new Message();
        a.setE(e);
        a.setState(state);
        a.setMessage(message);
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        a.setData(data);
        a.setParams(params);
        compare(a, RESTful.error(e, state, message, data, params));
    }

    @Test
    public void error5() {
        Message a = new Message();
        a.setE(e);
        a.setState(state);
        a.setMessage(RestMessageEnum.ERROR.getMessage());
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        a.setData(data);
        compare(a, RESTful.error(e, state, data));
    }

    @Test
    public void error6() {
        Message a = new Message();
        a.setE(e);
        a.setState(state);
        a.setMessage(RestMessageEnum.ERROR.getMessage());
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        a.setData(data);
        a.setParams(params);
        compare(a, RESTful.error(e, state, data, params));
    }

    @Test
    public void error7() {
        Message a = new Message();
        a.setE(e);
        a.setState(RestMessageEnum.ERROR.getState());
        a.setMessage(message);
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        compare(a, RESTful.error(e, message));
    }

    @Test
    public void error8() {
        Message a = new Message();
        a.setE(e);
        a.setState(RestMessageEnum.ERROR.getState());
        a.setMessage(message);
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        a.setData(data);
        compare(a, RESTful.error(e, message, data));
    }

    @Test
    public void error9() {
        Message a = new Message();
        a.setE(e);
        a.setState(RestMessageEnum.ERROR.getState());
        a.setMessage(message);
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        a.setData(data);
        a.setParams(params);
        compare(a, RESTful.error(e, message, data, params));
    }

    @Test
    public void error10() {
        Message a = new Message();
        a.setE(e);
        a.setState(RestMessageEnum.ERROR.getState());
        a.setMessage(RestMessageEnum.ERROR.getMessage());
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        a.setData(data);
        compare(a, RESTful.error(e, data));
    }

    @Test
    public void error11() {
        Message a = new Message();
        a.setE(e);
        a.setState(RestMessageEnum.ERROR.getState());
        a.setMessage(RestMessageEnum.ERROR.getMessage());
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        a.setData(data);
        a.setParams(params);
        compare(a, RESTful.error(e, data, params));
    }

    @Test
    public void error12() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setE(e);
            a.setState(RestMessageEnum.getState(NetworkEnum.ERROR, type));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.ERROR, type));
            a.setSuccess(NetworkEnum.ERROR.isSuccess());
            compare(a, RESTful.error(type, e));
        }
    }

    @Test
    public void error13() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setE(e);
            a.setState(RestMessageEnum.getState(NetworkEnum.ERROR, type, state));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.ERROR, type));
            a.setSuccess(NetworkEnum.ERROR.isSuccess());
            compare(a, RESTful.error(type, e, state));
        }
    }

    @Test
    public void error14() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setE(e);
            a.setState(RestMessageEnum.getState(NetworkEnum.ERROR, type, state));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.ERROR, type, message));
            a.setSuccess(NetworkEnum.ERROR.isSuccess());
            compare(a, RESTful.error(type, e, state, message));
        }
    }

    @Test
    public void error15() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setE(e);
            a.setState(RestMessageEnum.getState(NetworkEnum.ERROR, type, state));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.ERROR, type, message));
            a.setSuccess(NetworkEnum.ERROR.isSuccess());
            a.setData(data);
            compare(a, RESTful.error(type, e, state, message, data));
        }
    }

    @Test
    public void error16() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setE(e);
            a.setState(RestMessageEnum.getState(NetworkEnum.ERROR, type, state));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.ERROR, type, message));
            a.setSuccess(NetworkEnum.ERROR.isSuccess());
            a.setData(data);
            a.setParams(params);
            compare(a, RESTful.error(type, e, state, message, data, params));
        }
    }

    @Test
    public void error17() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setE(e);
            a.setState(RestMessageEnum.getState(NetworkEnum.ERROR, type, state));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.ERROR, type));
            a.setSuccess(NetworkEnum.ERROR.isSuccess());
            a.setData(data);
            compare(a, RESTful.error(type, e, state, data));
        }
    }

    @Test
    public void error18() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setE(e);
            a.setState(RestMessageEnum.getState(NetworkEnum.ERROR, type, state));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.ERROR, type));
            a.setSuccess(NetworkEnum.ERROR.isSuccess());
            a.setData(data);
            a.setParams(params);
            compare(a, RESTful.error(type, e, state, data, params));
        }
    }


    @Test
    public void error19() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setE(e);
            a.setState(RestMessageEnum.getState(NetworkEnum.ERROR, type));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.ERROR, type, message));
            a.setSuccess(NetworkEnum.ERROR.isSuccess());
            compare(a, RESTful.error(type, e, message));
        }
    }

    @Test
    public void error20() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setE(e);
            a.setState(RestMessageEnum.getState(NetworkEnum.ERROR, type));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.ERROR, type, message));
            a.setSuccess(NetworkEnum.ERROR.isSuccess());
            a.setData(data);
            compare(a, RESTful.error(type, e, message, data));
        }
    }

    @Test
    public void error21() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setE(e);
            a.setState(RestMessageEnum.getState(NetworkEnum.ERROR, type));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.ERROR, type, message));
            a.setSuccess(NetworkEnum.ERROR.isSuccess());
            a.setData(data);
            a.setParams(params);
            compare(a, RESTful.error(type, e, message, data, params));
        }
    }

    @Test
    public void error22() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setE(e);
            a.setState(RestMessageEnum.getState(NetworkEnum.ERROR, type));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.ERROR, type));
            a.setSuccess(NetworkEnum.ERROR.isSuccess());
            a.setData(data);
            compare(a, RESTful.error(type, e, data));
        }
    }

    @Test
    public void error23() {
        for (RestTypeEnum type : RestTypeEnum.values()) {
            Message a = new Message();
            a.setE(e);
            a.setState(RestMessageEnum.getState(NetworkEnum.ERROR, type));
            a.setMessage(RestMessageEnum.getMessage(NetworkEnum.ERROR, type));
            a.setSuccess(NetworkEnum.ERROR.isSuccess());
            a.setData(data);
            a.setParams(params);
            compare(a, RESTful.error(type, e, data, params));
        }
    }
}
