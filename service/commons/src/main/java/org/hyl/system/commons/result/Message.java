package org.hyl.system.commons.result;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = -6005192455561666565L;

    private Integer state;

    private String message;

    private Object data;

    private Boolean success;

    private String e;

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
