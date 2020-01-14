package org.hyl.system.web.rest.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@ApiModel("修改用户密码视图模型")
public class ChangePasswordVM {

    @ApiModelProperty("旧的用户密码")
    @NotBlank(message = "旧的密码不能为空")
    private String oldPassword;

    @ApiModelProperty("新的用户密码")
    @NotBlank(message = "新的密码不能为空")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{6,16}$", message = "新的密码的长度只能在6-16个字符之间，且至少包括1个大写字母，1个小写字母，1个数字")
    private String newPassword;

    @ApiModelProperty("确认新的用户密码")
    private String confirm;

    public ChangePasswordVM() {
        // Empty constructor needed for Jackson.
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
