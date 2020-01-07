package org.hyl.system.web.rest.vm;

import org.hyl.data.auditing.AbstractIdAuditingVM;
import org.hyl.system.config.Constants;
import org.hyl.system.domain.Dict;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DictVM extends AbstractIdAuditingVM {

    private Long pid = 0L;

    @NotBlank(message = "字典名称不能为空")
    @Size(max = 50, message = "字典名称的长度只能小于50个字符")
    private String name;

    @Size(max = 50, message = "字典唯一标识码的长度只能小于50个字符")
    private String code;

    @Size(max = 50, message = "字典数据的长度只能小于50个字符")
    private String data;

    private String desc;

    private String lastModifiedBy;

    private String lastModifiedDate_text;

    public static DictVM adapt(Dict dict) {
        DictVM vm = new DictVM();
        BeanUtils.copyProperties(dict, vm);
        if (dict.getLastModifiedDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(dict.getLastModifiedDate(), ZoneId.systemDefault());
            vm.setLastModifiedDate_text(localDateTime.format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMATTER)));
        }
        return vm;
    }

    public DictVM() {
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
