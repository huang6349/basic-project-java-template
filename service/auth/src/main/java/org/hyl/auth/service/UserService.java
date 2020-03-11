package org.hyl.auth.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hyl.data.auditing.AbstractIdAuditingEntity;
import org.hyl.data.config.DataConstants;
import org.hyl.auth.domain.Authority;
import org.hyl.dict.domain.Dict;
import org.hyl.auth.domain.MyUserInfo;
import org.hyl.dict.repository.DictRepository;
import org.hyl.auth.security.SecurityUtils;
import org.hyl.auth.web.rest.vm.ChangePasswordVM;
import org.hyl.auth.web.rest.vm.UserVM;
import org.hyl.auth.domain.MyUser;
import org.hyl.commons.exception.BadRequestException;
import org.hyl.commons.exception.DataAlreadyExistException;
import org.hyl.auth.repository.AuthorityRepository;
import org.hyl.auth.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {


    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final DictRepository dictRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, DictRepository dictRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.dictRepository = dictRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserVM create(UserVM vm) {
        if (userRepository.findByUsernameIgnoreCase(vm.getUsername()).isPresent()) {
            throw new DataAlreadyExistException("用户名为【" + vm.getUsername() + "】的信息已经存在了");
        }
        Optional<Dict> sex = dictRepository.findById(vm.getSexId());
        if (!sex.isPresent()) {
            throw new BadRequestException("未能在系统中找到数据编号为【" + vm.getSexId() + "】的性别信息");
        }
        MyUserInfo info = new MyUserInfo();
        info.setNickname(vm.getNickname());
        info.setRealname(vm.getRealname());
        info.setSex(sex.get());
        info.setBirthday(vm.getBirthday());
        info.setIdCard(vm.getIdCard());
        info.setEmail(vm.getEmail());
        info.setMobilePhone(vm.getMobilePhone());
        MyUser user = new MyUser();
        BeanUtils.copyProperties(vm, user);
        user.setPassword(passwordEncoder.encode("123456"));
        user.setInfo(info);
        user.setState(DataConstants.DATA_NORMAL_STATE);
        user.setAuthorities(setAuthorities(vm.getAuthorities()));
        return UserVM.adapt(userRepository.save(user));
    }

    public UserVM update(UserVM vm) {
        Optional<MyUser> optional = userRepository.findById(vm.getId());
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要修改的用户信息");
        }
        MyUser user = optional.get();
        if (vm.getState() == null || !vm.getState().equals(user.getState())) {
            throw new BadRequestException("用户状态不允许修改");
        }
        if (DataConstants.DATA_KEEP_STATE.equals(user.getState())) {
            Set<Long> authorities = user.getAuthorities().stream().map(AbstractIdAuditingEntity::getId).collect(Collectors.toSet());
            if (!CollectionUtils.isEqualCollection(authorities, vm.getAuthorities())) {
                throw new BadRequestException("该用户为系统保留用户，无法进行权限修改操作");
            }
        }
        if (!StringUtils.equals(vm.getUsername(), user.getUsername())) {
            throw new BadRequestException("用户名不允许修改");
        }
        Optional<Dict> sex = dictRepository.findById(vm.getSexId());
        if (!sex.isPresent()) {
            throw new BadRequestException("未能在系统中找到数据编号为【" + vm.getSexId() + "】的性别信息");
        }
        MyUserInfo info = user.getInfo();
        info.setNickname(vm.getNickname());
        info.setRealname(vm.getRealname());
        info.setSex(sex.get());
        info.setBirthday(vm.getBirthday());
        info.setIdCard(vm.getIdCard());
        info.setEmail(vm.getEmail());
        info.setMobilePhone(vm.getMobilePhone());
        BeanUtils.copyProperties(vm, user);
        user.setInfo(info);
        user.setAuthorities(setAuthorities(vm.getAuthorities()));
        return UserVM.adapt(userRepository.save(user));
    }

    public UserVM delete(Long id) {
        Optional<MyUser> optional = userRepository.findById(id);
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要删除的用户信息");
        }
        MyUser user = optional.get();
        if (DataConstants.DATA_KEEP_STATE.equals(user.getState())) {
            throw new BadRequestException("该用户为系统保留用户，无法进行删除操作");
        }
        MyUserInfo info = user.getInfo();
        info.setState(DataConstants.DATA_DELETE_STATE);
        user.setInfo(info);
        user.setState(DataConstants.DATA_DELETE_STATE);
        return UserVM.adapt(userRepository.save(user));
    }

    public UserVM enable(Long id) {
        Optional<MyUser> optional = userRepository.findById(id);
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要启用的用户信息");
        }
        MyUser user = optional.get();
        if (DataConstants.DATA_KEEP_STATE.equals(user.getState())) {
            throw new BadRequestException("该用户为系统保留用户，无法进行启用操作");
        }
        MyUserInfo info = user.getInfo();
        info.setState(DataConstants.DATA_NORMAL_STATE);
        user.setInfo(info);
        user.setState(DataConstants.DATA_NORMAL_STATE);
        return UserVM.adapt(userRepository.save(user));
    }

    public UserVM disable(Long id) {
        Optional<MyUser> optional = userRepository.findById(id);
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要禁用的用户信息");
        }
        MyUser user = optional.get();
        if (DataConstants.DATA_KEEP_STATE.equals(user.getState())) {
            throw new BadRequestException("该用户为系统保留用户，无法进行禁用操作");
        }
        MyUserInfo info = user.getInfo();
        info.setState(DataConstants.DATA_DISABLED_STATE);
        user.setInfo(info);
        user.setState(DataConstants.DATA_DISABLED_STATE);
        return UserVM.adapt(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public Optional<UserVM> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserUsername()
                .flatMap(userRepository::findByUsernameIgnoreCase)
                .map(UserVM::adapt);
    }

    public UserVM changePassword(ChangePasswordVM vm) {
        if (!StringUtils.equals(StringUtils.trimToNull(vm.getNewPassword()), StringUtils.trimToNull(vm.getConfirm()))) {
            throw new BadRequestException("两次密码不一致，无法进行密码修改操作");
        }
        Optional<MyUser> optional = SecurityUtils.getCurrentUserUsername().flatMap(userRepository::findByUsernameIgnoreCase);
        if (!optional.isPresent()) {
            throw new BadRequestException("未授权的用户，无法进行密码修改操作");
        }
        MyUser user = optional.get();
        if (!passwordEncoder.matches(vm.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("请输入的旧密码不正确，无法进行密码修改操作");
        }
        user.setPassword(passwordEncoder.encode(vm.getNewPassword()));
        return UserVM.adapt(userRepository.save(user));
    }

    public UserVM resetPassword(Long id) {
        Optional<MyUser> optional = userRepository.findById(id);
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要重置密码的用户信息");
        }
        MyUser user = optional.get();
        user.setPassword(passwordEncoder.encode("123456"));
        return UserVM.adapt(userRepository.save(user));
    }

    private Set<Authority> setAuthorities(Set<Long> authorities) {
        return authorities.stream().map(authoritie -> {
            Optional<Authority> optional = authorityRepository.findById(authoritie);
            if (!optional.isPresent()) {
                throw new BadRequestException("未能在系统中找到数据编号为【" + authoritie + "】的角色信息");
            }
            return optional.get();
        }).collect(Collectors.toSet());
    }
}
