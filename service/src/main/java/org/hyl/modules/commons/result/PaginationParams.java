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
@ApiModel("分页消息参数模型")
class PaginationParams implements Serializable {

    private static final long serialVersionUID = -6783945610780798542L;

    @ApiModelProperty("当前页数")
    private Integer page;

    @ApiModelProperty("每页条数")
    private Integer size;

    @ApiModelProperty("数据总数")
    private Long total;
}
