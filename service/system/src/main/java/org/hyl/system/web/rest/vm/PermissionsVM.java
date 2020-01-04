package org.hyl.system.web.rest.vm;

import org.hyl.system.config.Constants;
import org.hyl.data.auditing.AbstractIdAuditingVM;
import org.hyl.system.domain.Permissions;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class PermissionsVM extends AbstractIdAuditingVM {

    private Long pid = 0L;

    @NotBlank(message = "角色名称不能为空")
    @Size(max = 50, message = "角色名称的长度只能小于50个字符")
    private String name;

    private String path;

    private String icon;

    private Integer seq = 0;

    private String desc;

    private String lastModifiedBy;

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
