package org.hyl.system.web.rest.vm;

import com.google.common.collect.Sets;
import org.hyl.system.config.Constants;
import org.hyl.data.auditing.AbstractIdAuditingEntity;
import org.hyl.data.auditing.AbstractIdAuditingVM;
import org.hyl.system.domain.Authority;
import org.hyl.system.domain.MyUser;
import org.hyl.system.domain.MyUserInfo;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class UserVM extends AbstractIdAuditingVM {

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z][-_a-zA-Z0-9]{4,19}$", message = "用户名只能是5-19个字母，数字，减号，下划线的组合，且首位必须是字母")
    private String username;

    @NotBlank(message = "用户昵称不能为空")
    @Size(max = 50, message = "用户昵称的长度只能小于50个字符")
    private String nickname;

    private String realname;

    @NotNull(message = "用户性别不能为空")
    private Long sexId;

    private String sex_text;

    @Past(message = "用户生日必须是一个过去的日期")
    private Date birthday;

    @Pattern(regexp = "(^$|\\d{8}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}$)|(^\\d{6}(18|19|20)\\d{2}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}(\\d|X|x)$)", message = "错误的身份证格式")
    private String idCard;

    @Email(message = "错误的邮箱格式")
    private String email;

    @Pattern(regexp = "^$|(?:(?:\\+|00)86)?1[3-9]\\d{9}$", message = "错误的手机号格式")
    private String mobilePhone;

    private String lastModifiedBy;

    private String lastModifiedDate_text;

    @NotEmpty(message = "用户角色不能为空")
    private Set<Long> authorities = Sets.newHashSet();

    private Set<String> authorities_text = Sets.newHashSet();

    public static UserVM adapt(MyUser user) {
        UserVM vm = new UserVM();
        BeanUtils.copyProperties(user, vm);
        if (user.getInfo() != null) {
            MyUserInfo info = user.getInfo();
            vm.setNickname(info.getNickname());
            vm.setRealname(info.getRealname());
            if (info.getSex() != null) {
                vm.setSexId(info.getSex().getId());
                vm.setSex_text(info.getSex().getName());
            }
            vm.setBirthday(info.getBirthday());
            vm.setIdCard(info.getIdCard());
            vm.setEmail(info.getEmail());
            vm.setMobilePhone(info.getMobilePhone());
        }
        if (user.getLastModifiedDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(user.getLastModifiedDate(), ZoneId.systemDefault());
            vm.setLastModifiedDate_text(localDateTime.format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMATTER)));
        }
        vm.setAuthorities(user.getAuthorities().stream().map(AbstractIdAuditingEntity::getId).collect(Collectors.toSet()));
        vm.setAuthorities_text(user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()));
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Long getSexId() {
        return sexId;
    }

    public void setSexId(Long sexId) {
        this.sexId = sexId;
    }

    public String getSex_text() {
        return sex_text;
    }

    public void setSex_text(String sex_text) {
        this.sex_text = sex_text;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
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

    public Set<Long> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Long> authorities) {
        this.authorities = authorities;
    }

    public Set<String> getAuthorities_text() {
        return authorities_text;
    }

    public void setAuthorities_text(Set<String> authorities_text) {
        this.authorities_text = authorities_text;
    }
}
