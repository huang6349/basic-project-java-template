package org.hyl.modules.data.auditing.vm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel("数据审计视图模型（树形结构）")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractLevelAuditingVM<E> extends AbstractIdAuditingVM {

    @ApiModelProperty("上级数据编号")
    private Long pid = 0L;

    @JsonIgnore
    private String level;

    @ApiModelProperty("子级数据列表")
    private List<E> children = Lists.newArrayList();
}
