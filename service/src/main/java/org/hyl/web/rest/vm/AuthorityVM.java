package org.hyl.web.rest.vm;

import org.hyl.auditing.AbstractAuditingVM;
import org.hyl.config.Constants;
import org.hyl.domain.Authority;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class AuthorityVM extends AbstractAuditingVM {

    @NotBlank(message = "角色编号不能为空")
    @Size(max = 50, message = "角色编号的长度只能小于50个字符")
    @Pattern(regexp = "^[A-Z]+[A-Z_]+[A-Z]+$", message = "角色编号必须满足“ROLE_ADMIN”规则，即开头和结尾是大写字母中间包含至少一个“_”")
    private String id;

    @NotBlank(message = "角色名称不能为空")
    @Size(max = 50, message = "角色名称的长度只能小于50个字符")
    private String name;

    private Integer seq = 0;

    private String desc;

    private String lastModifiedBy;

    private String lastModifiedDate_zh;

    public static AuthorityVM adapt(Authority authority) {
        AuthorityVM vm = new AuthorityVM();
        BeanUtils.copyProperties(authority, vm);
        if (authority.getLastModifiedDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(authority.getLastModifiedDate(), ZoneId.systemDefault());
            vm.setLastModifiedDate_zh(localDateTime.format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMATTER)));
        }
        return vm;
    }

    public AuthorityVM() {
        // Empty constructor needed for Jackson.
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLastModifiedDate_zh() {
        return lastModifiedDate_zh;
    }

    public void setLastModifiedDate_zh(String lastModifiedDate_zh) {
        this.lastModifiedDate_zh = lastModifiedDate_zh;
    }
}
