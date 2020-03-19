package org.hyl.modules.commons.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
