package org.hyl.system.commons.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("消息模型")
public class Message implements Serializable {

    private static final long serialVersionUID = -6005192455561666565L;

    @ApiModelProperty("消息状态码")
    private Integer state;

    @ApiModelProperty("消息信息")
    private String message;

    @ApiModelProperty("消息数据")
    private Object data;

    @ApiModelProperty("消息状态")
    private Boolean success;

    @ApiModelProperty("消息异常信息")
    private String e;

    @ApiModelProperty("消息参数")
    private Object params;

    public Message() {
        // Empty constructor needed for Jackson.
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

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
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
