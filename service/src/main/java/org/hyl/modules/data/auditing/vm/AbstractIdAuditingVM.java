package org.hyl.modules.data.auditing.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel("数据审计视图模型（带编号）")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractIdAuditingVM extends AbstractAuditingVM {

    @ApiModelProperty("数据编号")
    private Long id;
}
