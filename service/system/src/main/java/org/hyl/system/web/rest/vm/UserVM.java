package org.hyl.system.web.rest.vm;

import com.google.common.collect.Sets;
import org.hyl.system.config.Constants;
import org.hyl.data.auditing.AbstractIdAuditingEntity;
import org.hyl.data.auditing.AbstractIdAuditingVM;
import org.hyl.system.domain.Authority;
import org.hyl.system.domain.MyUser;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

public class UserVM extends AbstractIdAuditingVM {

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[A-Za-z]+[A-Za-z0-9]{3,16}$", message = "用户名只能是数字和字母的组合，且长度在4-16个字符之间、首位必须是字母")
    private String username;

    @NotBlank(message = "用户密码不能为空")
    @Pattern(regexp = "^[A-Za-z0-9]{4,16}$", message = "密码只能是数字和字母的组合，且长度在4-16个字符之间")
    private String password;

    private String lastModifiedBy;

    private String lastModifiedDate_zh;

    private Set<Long> authorities = Sets.newHashSet();

    private Set<String> authorities_zh = Sets.newHashSet();

    public static UserVM adapt(MyUser user) {
        UserVM vm = new UserVM();
        BeanUtils.copyProperties(user, vm);
        if (user.getLastModifiedDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(user.getLastModifiedDate(), ZoneId.systemDefault());
            vm.setLastModifiedDate_zh(localDateTime.format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMATTER)));
        }
        vm.setAuthorities(user.getAuthorities().stream().map(AbstractIdAuditingEntity::getId).collect(Collectors.toSet()));
        vm.setAuthorities_zh(user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()));
        return vm;
    }

    public UserVM() {
        // Empty constructor needed for Jackson.
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<Long> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Long> authorities) {
        this.authorities = authorities;
    }

    public Set<String> getAuthorities_zh() {
        return authorities_zh;
    }

    public void setAuthorities_zh(Set<String> authorities_zh) {
        this.authorities_zh = authorities_zh;
    }
}
