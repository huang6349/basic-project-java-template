package org.hyl.modules.auth.domain.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@ApiModel("登录视图模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVM {

    @ApiModelProperty("用户名")
    @NotNull(message = "用户名不能为空")
    private String username;

    @ApiModelProperty("用户密码")
    @NotNull(message = "密码不能为空")
    private String password;
}
