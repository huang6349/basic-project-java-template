package org.hyl.auth.web.rest.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hyl.data.auditing.AbstractIdAuditingVM;
import org.hyl.data.config.DataConstants;
import org.hyl.auth.domain.Permissions;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("菜单视图模型")
public class PermissionsVM extends AbstractIdAuditingVM {

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

    public static PermissionsVM adapt(Permissions permissions) {
        PermissionsVM vm = new PermissionsVM();
        BeanUtils.copyProperties(permissions, vm);
        if (permissions.getLastModifiedDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(permissions.getLastModifiedDate(), ZoneId.systemDefault());
            vm.setLastModifiedDate_text(localDateTime.format(DateTimeFormatter.ofPattern(DataConstants.DATE_TIME_FORMATTER)));
        }
        return vm;
    }
}
