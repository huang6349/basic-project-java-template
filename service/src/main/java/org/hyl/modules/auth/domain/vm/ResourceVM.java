package org.hyl.modules.auth.domain.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hyl.modules.auth.domain.Resource;
import org.hyl.modules.commons.utils.InstantUtil;
import org.hyl.modules.data.auditing.vm.AbstractIdAuditingVM;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("菜单资源视图模型")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceVM extends AbstractIdAuditingVM {

    @ApiModelProperty("资源地址")
    @NotBlank(message = "资源地址不能为空")
    private String pattern;

    @ApiModelProperty("资源描述")
    private String desc;

    @ApiModelProperty("资源类型")
    @NotNull(message = "资源类型不能为空")
    private Long methodId;

    @ApiModelProperty("资源类型（文字）")
    private String method_text;

    @ApiModelProperty("所属菜单")
    @NotNull(message = "所属菜单不能为空")
    private Long permissionId;

    @ApiModelProperty("所属菜单（文字）")
    private String permission_text;

    @ApiModelProperty("资源最后修改人")
    private String lastModifiedBy;

    @ApiModelProperty("资源最后修改时间")
    private String lastModifiedDate_text;

    public static ResourceVM adapt(Resource resource) {
        ResourceVM vm = new ResourceVM();
        BeanUtils.copyProperties(resource, vm);
        if (resource.getMethod() != null) {
            vm.setMethodId(resource.getMethod().getId());
            vm.setMethod_text(resource.getMethod().getName());
        }
        if (resource.getPermission() != null) {
            vm.setPermissionId(resource.getPermission().getId());
            vm.setPermission_text(resource.getPermission().getName());
        }
        vm.setLastModifiedDate_text(InstantUtil.format(resource.getLastModifiedDate()));
        return vm;
    }
}
