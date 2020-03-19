package org.hyl.modules.commons.result;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.assertj.core.api.Assertions;
import org.hyl.modules.commons.result.enums.MessageEnum;
import org.hyl.modules.commons.result.enums.NetworkEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ResultUtilTest {

    private Integer state;

    private String message;

    private List<Integer> data = Lists.newArrayList();

    private String e;

    private Map<String, Integer> params = Maps.newHashMap();

    @BeforeEach
    public void setUp() {
        this.state = new Random().nextInt(400) + 100;
        this.message = "COMMONS_TEST";
        this.data = Lists.newArrayList();
        this.data.add(new Random().nextInt());
        this.data.add(new Random().nextInt());
        this.data.add(new Random().nextInt());
        this.e = new RuntimeException("COMMONS_ERROR").getMessage();
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
        a.setState(MessageEnum.SUCCESS.getState());
        a.setMessage(MessageEnum.SUCCESS.getMessage());
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        compare(a, ResultUtil.success());
    }

    @Test
    public void success1() {
        Message a = new Message();
        a.setState(state);
        a.setMessage(MessageEnum.SUCCESS.getMessage());
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        compare(a, ResultUtil.success(state));
    }

    @Test
    public void success2() {
        Message a = new Message();
        a.setState(state);
        a.setMessage(message);
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        compare(a, ResultUtil.success(state, message));
    }

    @Test
    public void success3() {
        Message a = new Message();
        a.setState(state);
        a.setMessage(message);
        a.setData(data);
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        compare(a, ResultUtil.success(state, message, data));
    }

    @Test
    public void success4() {
        Message a = new Message();
        a.setState(state);
        a.setMessage(message);
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        a.setData(data);
        a.setParams(params);
        compare(a, ResultUtil.success(state, message, data, params));
    }

    @Test
    public void success5() {
        Message a = new Message();
        a.setState(state);
        a.setMessage(MessageEnum.SUCCESS.getMessage());
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        a.setData(data);
        compare(a, ResultUtil.success(state, data));
    }

    @Test
    public void success6() {
        Message a = new Message();
        a.setState(state);
        a.setMessage(MessageEnum.SUCCESS.getMessage());
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        a.setData(data);
        a.setParams(params);
        compare(a, ResultUtil.success(state, data, params));
    }

    @Test
    public void success7() {
        Message a = new Message();
        a.setState(MessageEnum.SUCCESS.getState());
        a.setMessage(message);
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        compare(a, ResultUtil.success(message));
    }

    @Test
    public void success8() {
        Message a = new Message();
        a.setState(MessageEnum.SUCCESS.getState());
        a.setMessage(message);
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        a.setData(data);
        compare(a, ResultUtil.success(message, data));
    }

    @Test
    public void success9() {
        Message a = new Message();
        a.setState(MessageEnum.SUCCESS.getState());
        a.setMessage(message);
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        a.setData(data);
        a.setParams(params);
        compare(a, ResultUtil.success(message, data, params));
    }

    @Test
    public void success10() {
        Message a = new Message();
        a.setState(MessageEnum.SUCCESS.getState());
        a.setMessage(MessageEnum.SUCCESS.getMessage());
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        a.setData(data);
        compare(a, ResultUtil.success(data));
    }

    @Test
    public void success11() {
        Message a = new Message();
        a.setState(MessageEnum.SUCCESS.getState());
        a.setMessage(MessageEnum.SUCCESS.getMessage());
        a.setSuccess(NetworkEnum.SUCCESS.isSuccess());
        a.setData(data);
        a.setParams(params);
        compare(a, ResultUtil.success(data, params));
    }

    @Test
    public void error0() {
        Message a = new Message();
        a.setE(e);
        a.setState(MessageEnum.ERROR.getState());
        a.setMessage(MessageEnum.ERROR.getMessage());
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        compare(a, ResultUtil.error(e));
    }

    @Test
    public void error1() {
        Message a = new Message();
        a.setE(e);
        a.setState(state);
        a.setMessage(MessageEnum.ERROR.getMessage());
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        compare(a, ResultUtil.error(e, state));
    }

    @Test
    public void error2() {
        Message a = new Message();
        a.setE(e);
        a.setState(state);
        a.setMessage(message);
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        compare(a, ResultUtil.error(e, state, message));
    }

    @Test
    public void error3() {
        Message a = new Message();
        a.setE(e);
        a.setState(state);
        a.setMessage(message);
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        a.setData(data);
        compare(a, ResultUtil.error(e, state, message, data));
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
        compare(a, ResultUtil.error(e, state, message, data, params));
    }

    @Test
    public void error5() {
        Message a = new Message();
        a.setE(e);
        a.setState(state);
        a.setMessage(MessageEnum.ERROR.getMessage());
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        a.setData(data);
        compare(a, ResultUtil.error(e, state, data));
    }

    @Test
    public void error6() {
        Message a = new Message();
        a.setE(e);
        a.setState(state);
        a.setMessage(MessageEnum.ERROR.getMessage());
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        a.setData(data);
        a.setParams(params);
        compare(a, ResultUtil.error(e, state, data, params));
    }

    @Test
    public void error7() {
        Message a = new Message();
        a.setE(e);
        a.setState(MessageEnum.ERROR.getState());
        a.setMessage(message);
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        compare(a, ResultUtil.error(e, message));
    }

    @Test
    public void error8() {
        Message a = new Message();
        a.setE(e);
        a.setState(MessageEnum.ERROR.getState());
        a.setMessage(message);
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        a.setData(data);
        compare(a, ResultUtil.error(e, message, data));
    }

    @Test
    public void error9() {
        Message a = new Message();
        a.setE(e);
        a.setState(MessageEnum.ERROR.getState());
        a.setMessage(message);
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        a.setData(data);
        a.setParams(params);
        compare(a, ResultUtil.error(e, message, data, params));
    }

    @Test
    public void error10() {
        Message a = new Message();
        a.setE(e);
        a.setState(MessageEnum.ERROR.getState());
        a.setMessage(MessageEnum.ERROR.getMessage());
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        a.setData(data);
        compare(a, ResultUtil.error(e, data));
    }

    @Test
    public void error11() {
        Message a = new Message();
        a.setE(e);
        a.setState(MessageEnum.ERROR.getState());
        a.setMessage(MessageEnum.ERROR.getMessage());
        a.setSuccess(NetworkEnum.ERROR.isSuccess());
        a.setData(data);
        a.setParams(params);
        compare(a, ResultUtil.error(e, data, params));
    }
}
