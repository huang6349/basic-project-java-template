package org.hyl.system.service;

import org.apache.commons.collections.CollectionUtils;
import org.hyl.data.auditing.AbstractIdAuditingEntity;
import org.hyl.data.config.DataConstants;
import org.hyl.system.domain.Authority;
import org.hyl.system.web.rest.vm.UpdateUserVM;
import org.hyl.system.web.rest.vm.UserVM;
import org.hyl.system.domain.MyUser;
import org.hyl.system.errors.BadRequestException;
import org.hyl.system.errors.DataAlreadyExistException;
import org.hyl.system.repository.AuthorityRepository;
import org.hyl.system.repository.UserRepository;
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

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserVM create(UserVM vm) {
        if (userRepository.findByUsernameIgnoreCase(vm.getUsername()).isPresent()) {
            throw new DataAlreadyExistException("用户名为【" + vm.getUsername() + "】的信息已经存在了");
        }
        MyUser user = new MyUser();
        BeanUtils.copyProperties(vm, user);
        user.setPassword(passwordEncoder.encode(vm.getPassword()));
        user.setState(DataConstants.DATA_NORMAL_STATE);
        user.setAuthorities(setAuthorities(vm.getAuthorities()));
        return UserVM.adapt(userRepository.save(user));
    }

    public UserVM update(UpdateUserVM vm) {
        Optional<MyUser> optional = userRepository.findById(vm.getId());
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要修改的用户信息");
        }
        MyUser user = optional.get();
        if (vm.getState() == null || !vm.getState().equals(user.getState())) {
            throw new BadRequestException("用户状态不允许修改信息");
        }
        if (DataConstants.DATA_KEEP_STATE.equals(user.getState())) {
            Set<Long> authorities = user.getAuthorities().stream().map(AbstractIdAuditingEntity::getId).collect(Collectors.toSet());
            if (!CollectionUtils.isEqualCollection(authorities, vm.getAuthorities())) {
                throw new BadRequestException("该用户为系统保留用户，不允许修改权限信息");
            }
        }
        BeanUtils.copyProperties(vm, user);
        user.setAuthorities(setAuthorities(vm.getAuthorities()));
        return UserVM.adapt(userRepository.save(user));
    }

    public UserVM delete(Long id) {
        Optional<MyUser> optional = userRepository.findById(id);
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要删除的用户");
        }
        MyUser user = optional.get();
        if (DataConstants.DATA_KEEP_STATE.equals(user.getState())) {
            throw new BadRequestException("该用户为系统保留用户，不允许删除");
        }
        user.setState(DataConstants.DATA_DELETE_STATE);
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
