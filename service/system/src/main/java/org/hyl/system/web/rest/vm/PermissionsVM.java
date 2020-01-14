package org.hyl.system.web.rest.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hyl.system.config.Constants;
import org.hyl.data.auditing.AbstractIdAuditingVM;
import org.hyl.system.domain.Permissions;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
            vm.setLastModifiedDate_text(localDateTime.format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMATTER)));
        }
        return vm;
    }

    public PermissionsVM() {
        // Empty constructor needed for Jackson.
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedDate_text() {
        return lastModifiedDate_text;
    }

    public void setLastModifiedDate_text(String lastModifiedDate_text) {
        this.lastModifiedDate_text = lastModifiedDate_text;
    }
}
