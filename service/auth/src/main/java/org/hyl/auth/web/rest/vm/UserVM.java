package org.hyl.auth.web.rest.vm;

import com.google.common.collect.Sets;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hyl.data.auditing.AbstractIdAuditingEntity;
import org.hyl.data.auditing.AbstractIdAuditingVM;
import org.hyl.data.config.DataConstants;
import org.hyl.auth.domain.Authority;
import org.hyl.auth.domain.MyUser;
import org.hyl.auth.domain.MyUserInfo;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户视图模型")
public class UserVM extends AbstractIdAuditingVM {

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z][-_a-zA-Z0-9]{4,19}$", message = "用户名只能是5-19个字母，数字，减号，下划线的组合，且首位必须是字母")
    private String username;

    @ApiModelProperty("用户昵称")
    @NotBlank(message = "用户昵称不能为空")
    @Size(max = 50, message = "用户昵称的长度只能小于50个字符")
    private String nickname;

    @ApiModelProperty("用户真实姓名")
    private String realname;

    @ApiModelProperty("用户性别")
    @NotNull(message = "用户性别不能为空")
    private Long sexId;

    @ApiModelProperty("用户性别（文字）")
    private String sex_text;

    @ApiModelProperty("用户生日")
    @Past(message = "用户生日必须是一个过去的日期")
    private Date birthday;

    @ApiModelProperty("用户身份证")
    @Pattern(regexp = "(^$|^\\d{8}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}$)|(^\\d{6}(18|19|20)\\d{2}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}(\\d|X|x)$)", message = "错误的身份证格式")
    private String idCard;

    @ApiModelProperty("用户邮箱")
    @Email(message = "错误的邮箱格式")
    private String email;

    @ApiModelProperty("用户手机号")
    @Pattern(regexp = "^$|^(?:(?:\\+|00)86)?1[3-9]\\d{9}$", message = "错误的手机号格式")
    private String mobilePhone;

    @ApiModelProperty("用户信息最后修改人")
    private String lastModifiedBy;

    @ApiModelProperty("用户信息最后修改时间")
    private String lastModifiedDate_text;

    @ApiModelProperty("用户角色编号列表")
    @NotEmpty(message = "用户角色不能为空")
    private Set<Long> authorities = Sets.newHashSet();

    @ApiModelProperty("用户角色名称列表")
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
            vm.setLastModifiedDate_text(localDateTime.format(DateTimeFormatter.ofPattern(DataConstants.DATE_TIME_FORMATTER)));
        }
        vm.setAuthorities(user.getAuthorities().stream().map(AbstractIdAuditingEntity::getId).collect(Collectors.toSet()));
        vm.setAuthorities_text(user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()));
        return vm;
    }
}
