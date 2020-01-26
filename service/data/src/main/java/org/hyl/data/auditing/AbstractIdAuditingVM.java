package org.hyl.data.auditing;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("数据审计视图模型（带编号）")
public class AbstractIdAuditingVM extends AbstractAuditingVM {

    @ApiModelProperty("数据编号")
    private Long id;
}
