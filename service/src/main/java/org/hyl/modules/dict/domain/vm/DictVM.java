package org.hyl.modules.dict.domain.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hyl.modules.commons.utils.InstantUtil;
import org.hyl.modules.data.auditing.vm.AbstractIdAuditingVM;
import org.hyl.modules.dict.domain.Dict;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel("字典视图模型")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictVM extends AbstractIdAuditingVM {

    @ApiModelProperty("上级数据编号")
    private Long pid = 0L;

    @ApiModelProperty("字典名称")
    @NotBlank(message = "字典名称不能为空")
    @Size(max = 50, message = "字典名称的长度只能小于50个字符")
    private String name;

    @ApiModelProperty("字典唯一标识码")
    @Size(max = 50, message = "字典唯一标识码的长度只能小于50个字符")
    private String code;

    @ApiModelProperty("字典数据")
    @Size(max = 50, message = "字典数据的长度只能小于50个字符")
    private String data;

    @ApiModelProperty("字典描述")
    private String desc;

    @ApiModelProperty("字典信息最后修改人")
    private String lastModifiedBy;

    @ApiModelProperty("字典信息最后修改时间")
    private String lastModifiedDate_text;

    public static DictVM adapt(Dict dict) {
        DictVM vm = new DictVM();
        BeanUtils.copyProperties(dict, vm);
        vm.setLastModifiedDate_text(InstantUtil.format(dict.getLastModifiedDate()));
        return vm;
    }
}
