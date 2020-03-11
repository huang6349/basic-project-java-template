package org.hyl.auth.web.rest.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hyl.data.auditing.AbstractIdAuditingVM;
import org.hyl.data.config.DataConstants;
import org.hyl.auth.domain.Resource;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("菜单资源视图模型")
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
    private Long permissionsId;

    @ApiModelProperty("所属菜单（文字）")
    private String permissions_text;

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
        if (resource.getPermissions() != null) {
            vm.setPermissionsId(resource.getPermissions().getId());
            vm.setPermissions_text(resource.getPermissions().getName());
        }
        if (resource.getLastModifiedDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(resource.getLastModifiedDate(), ZoneId.systemDefault());
            vm.setLastModifiedDate_text(localDateTime.format(DateTimeFormatter.ofPattern(DataConstants.DATE_TIME_FORMATTER)));
        }
        return vm;
    }
}
