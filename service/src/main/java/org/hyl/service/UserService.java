package org.hyl.service;

import org.hyl.config.Constants;
import org.hyl.domain.Authority;
import org.hyl.domain.MyUser;
import org.hyl.errors.BadRequestException;
import org.hyl.errors.DataAlreadyExistException;
import org.hyl.repository.AuthorityRepository;
import org.hyl.repository.UserRepository;
import org.hyl.web.rest.vm.AuthorityVM;
import org.hyl.web.rest.vm.UserVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    @Autowired
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    public UserVM create(UserVM vm) {
        log.debug("新增用户：{}", vm);

        if (userRepository.findByUsernameIgnoreCase(vm.getUsername()).isPresent()) {
            throw new DataAlreadyExistException("用户名为【" + vm.getUsername() + "】的信息已经存在了");
        }
        MyUser user = new MyUser();
        BeanUtils.copyProperties(vm, user);
        user.setState(Constants.DATA_NORMAL_STATE);
        user.setAuthorities(setAuthorities(vm.getRoles()));
        return UserVM.adapt(userRepository.save(user));
    }

    public UserVM update(UserVM vm) {
        log.debug("修改用户：{}", vm);

        Optional<MyUser> optional = userRepository.findById(vm.getId());
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要修改的用户信息");
        }
        MyUser user = optional.get();
        if (vm.getUsername() == null || !vm.getUsername().equals(user.getUsername())) {
            throw new BadRequestException("用户名不允许修改");
        }
        if (vm.getState() == null || !vm.getState().equals(user.getState())) {
            throw new BadRequestException("用户状态不允许修改信息");
        }
        BeanUtils.copyProperties(vm, user);
        user.setAuthorities(setAuthorities(vm.getRoles()));
        return UserVM.adapt(userRepository.save(user));
    }

    public UserVM delete(Long id) {
        log.debug("删除用户：{}", id);

        Optional<MyUser> optional = userRepository.findById(id);
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要删除的用户");
        }
        MyUser user = optional.get();
        user.setState(Constants.DATA_DELETE_STATE);
        return UserVM.adapt(userRepository.save(user));
    }

    private Set<Authority> setAuthorities(Set<AuthorityVM> roles) {
        return roles.stream().map(o -> {
            Optional<Authority> optional = authorityRepository.findById(o.getId());
            if (!optional.isPresent()) {
                throw new BadRequestException("未能在系统中找到角色信息[" + o.getName() + "]");
            }
            return optional.get();
        }).collect(Collectors.toSet());
    }
}
