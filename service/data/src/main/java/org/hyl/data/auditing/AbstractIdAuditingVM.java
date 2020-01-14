package org.hyl.data.auditing;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("数据审计视图模型（带编号）")
public class AbstractIdAuditingVM extends AbstractAuditingVM {

    @ApiModelProperty("数据编号")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
