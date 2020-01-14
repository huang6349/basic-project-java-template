package org.hyl.system.web.rest.vm;

import com.google.common.collect.Sets;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.hyl.data.auditing.AbstractIdAuditingEntity;
import org.hyl.system.config.Constants;
import org.hyl.data.auditing.AbstractIdAuditingVM;
import org.hyl.system.domain.Authority;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

@ApiModel("角色视图模型")
public class AuthorityVM extends AbstractIdAuditingVM {

    @ApiModelProperty("角色名称")
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 50, message = "角色名称的长度只能小于50个字符")
    private String name;

    @ApiModelProperty("角色唯一标识码")
    @NotBlank(message = "角色唯一标识码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{1,18}$", message = "角色唯一标识码只能是1-18个字母和数字的组合")
    private String code;

    @ApiModelProperty("角色描述")
    private String desc;

    @ApiModelProperty("角色信息最后修改人")
    private String lastModifiedBy;

    @ApiModelProperty("角色信息最后修改时间")
    private String lastModifiedDate_text;

    @ApiModelProperty("角色菜单编号列表")
    private Set<Long> permissions = Sets.newHashSet();

    public static AuthorityVM adapt(Authority authority) {
        AuthorityVM vm = new AuthorityVM();
        BeanUtils.copyProperties(authority, vm);
        vm.setCode(StringUtils.upperCase(StringUtils.replaceOnce(authority.getCode(), "ROLE_", "")));
        if (authority.getLastModifiedDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(authority.getLastModifiedDate(), ZoneId.systemDefault());
            vm.setLastModifiedDate_text(localDateTime.format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMATTER)));
        }
        vm.setPermissions(authority.getPermissions().stream().map(AbstractIdAuditingEntity::getId).collect(Collectors.toSet()));
        return vm;
    }

    public AuthorityVM() {
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

    public Set<Long> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Long> permissions) {
        this.permissions = permissions;
    }
}
