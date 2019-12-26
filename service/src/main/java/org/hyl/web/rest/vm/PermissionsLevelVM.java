package org.hyl.web.rest.vm;

import org.hyl.auditing.AbstractLevelAuditingVM;
import org.hyl.config.Constants;
import org.hyl.domain.Permissions;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class PermissionsLevelVM extends AbstractLevelAuditingVM<PermissionsLevelVM> {

    private String name;

    private String path;

    private String desc;

    private String lastModifiedBy;

    private String lastModifiedDate_zh;

    public static PermissionsLevelVM adapt(Permissions permissions) {
        PermissionsLevelVM vm = new PermissionsLevelVM();
        BeanUtils.copyProperties(permissions, vm);
        if (permissions.getLastModifiedDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(permissions.getLastModifiedDate(), ZoneId.systemDefault());
            vm.setLastModifiedDate_zh(localDateTime.format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMATTER)));
        }
        return vm;
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

    public String getLastModifiedDate_zh() {
        return lastModifiedDate_zh;
    }

    public void setLastModifiedDate_zh(String lastModifiedDate_zh) {
        this.lastModifiedDate_zh = lastModifiedDate_zh;
    }
}
