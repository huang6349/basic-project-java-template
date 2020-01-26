package org.hyl.system.web.rest.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hyl.system.config.Constants;
import org.hyl.system.domain.Permissions;
import org.hyl.data.auditing.AbstractLevelAuditingVM;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("菜单视图模型（树形数据）")
public class PermissionsLevelVM extends AbstractLevelAuditingVM<PermissionsLevelVM> {

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

    public static PermissionsLevelVM adapt(Permissions permissions) {
        PermissionsLevelVM vm = new PermissionsLevelVM();
        BeanUtils.copyProperties(permissions, vm);
        if (permissions.getLastModifiedDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(permissions.getLastModifiedDate(), ZoneId.systemDefault());
            vm.setLastModifiedDate_text(localDateTime.format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMATTER)));
        }
        return vm;
    }
}
