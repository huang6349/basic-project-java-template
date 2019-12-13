package org.hyl.web.rest.vm;

import org.hyl.config.Constants;
import org.hyl.domain.MyUser;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class UserVM {

    private Long id;

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[A-Za-z]+[A-Za-z0-9]{3,16}$", message = "用户名只能是数字和字母的组合，且长度在4-16个字符之间、首位必须是字母")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^[A-Za-z0-9]{4,16}$", message = "密码只能是数字和字母的组合，且长度在4-16个字符之间")
    private String password;

    private Byte state = Constants.DATA_NORMAL_STATE;

    private String state_zh;

    private String lastModifiedBy;

    private String lastModifiedDate_zh;

    public static UserVM adapt(MyUser user) {
        UserVM vm = new UserVM();
        BeanUtils.copyProperties(user, vm);
        if (user.getLastModifiedDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(user.getLastModifiedDate(), ZoneId.systemDefault());
            vm.setLastModifiedDate_zh(localDateTime.format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMATTER)));
        }
        return vm;
    }

    public UserVM() {
        // Empty constructor needed for Jackson.
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getState_zh() {
        state_zh = Constants.DATA_NORMAL_STATE_ZH;
        if (Constants.DATA_DELETE_STATE.equals(state)) {
            state_zh = Constants.DATA_DELETE_STATE_ZH;
        }
        return state_zh;
    }

    public void setState_zh(String state_zh) {
        this.state_zh = state_zh;
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
