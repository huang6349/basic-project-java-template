package org.hyl.system.web.rest.vm;

import org.hyl.data.auditing.AbstractLevelAuditingVM;
import org.hyl.system.config.Constants;
import org.hyl.system.domain.Dict;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DictLevelVM extends AbstractLevelAuditingVM<DictLevelVM> {

    private String name;

    private String code;

    private String data;

    private String desc;

    private String lastModifiedBy;

    private String lastModifiedDate_text;

    public static DictLevelVM adapt(Dict dict) {
        DictLevelVM vm = new DictLevelVM();
        BeanUtils.copyProperties(dict, vm);
        if (dict.getLastModifiedDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(dict.getLastModifiedDate(), ZoneId.systemDefault());
            vm.setLastModifiedDate_text(localDateTime.format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMATTER)));
        }
        return vm;
    }

    public DictLevelVM() {
        // Empty constructor needed for Jackson.
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
