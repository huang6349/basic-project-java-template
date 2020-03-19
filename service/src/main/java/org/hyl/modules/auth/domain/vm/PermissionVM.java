package org.hyl.modules.auth.domain.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hyl.modules.auth.domain.Permission;
import org.hyl.modules.commons.utils.InstantUtil;
import org.hyl.modules.data.auditing.vm.AbstractIdAuditingVM;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel("菜单视图模型")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionVM extends AbstractIdAuditingVM {

    @ApiModelProperty("上级数据编号")
    private Long pid = 0L;

    @ApiModelProperty("菜单名称")
    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 50, message = "菜单名称的长度只能小于50个字符")
    private String name;

    @ApiModelProperty("菜单路径")
    private String path;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("排序")
    @NotNull(message = "排序不能为空")
    private Integer seq = 0;

    @ApiModelProperty("菜单描述")
    private String desc;

    @ApiModelProperty("菜单信息最后修改人")
    private String lastModifiedBy;

    @ApiModelProperty("菜单信息最后修改时间")
    private String lastModifiedDate_text;

    public static PermissionVM adapt(Permission permission) {
        PermissionVM vm = new PermissionVM();
        BeanUtils.copyProperties(permission, vm);
        vm.setLastModifiedDate_text(InstantUtil.format(permission.getLastModifiedDate()));
        return vm;
    }
}
