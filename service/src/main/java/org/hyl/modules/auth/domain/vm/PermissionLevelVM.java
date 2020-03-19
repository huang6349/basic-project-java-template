package org.hyl.modules.auth.domain.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hyl.modules.auth.domain.Permission;
import org.hyl.modules.commons.utils.InstantUtil;
import org.hyl.modules.data.auditing.vm.AbstractLevelAuditingVM;
import org.springframework.beans.BeanUtils;

@ApiModel("菜单视图模型（树形数据）")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionLevelVM extends AbstractLevelAuditingVM<PermissionLevelVM> {

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("菜单路径")
    private String path;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("排序")
    private Integer seq = 0;

    @ApiModelProperty("菜单描述")
    private String desc;

    @ApiModelProperty("菜单信息最后修改人")
    private String lastModifiedBy;

    @ApiModelProperty("菜单信息最后修改时间")
    private String lastModifiedDate_text;

    public static PermissionLevelVM adapt(Permission permission) {
        PermissionLevelVM vm = new PermissionLevelVM();
        BeanUtils.copyProperties(permission, vm);
        vm.setLastModifiedDate_text(InstantUtil.format(permission.getLastModifiedDate()));
        return vm;
    }
}
