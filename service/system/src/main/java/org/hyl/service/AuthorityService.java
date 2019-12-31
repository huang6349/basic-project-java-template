package org.hyl.service;

import org.hyl.config.Constants;
import org.hyl.domain.Authority;
import org.hyl.errors.DataAlreadyExistException;
import org.hyl.web.rest.vm.AuthorityVM;
import org.hyl.domain.Permissions;
import org.hyl.errors.BadRequestException;
import org.hyl.repository.AuthorityRepository;
import org.hyl.repository.PermissionsRepository;
import org.hyl.web.rest.vm.UpdateAuthorityPermissionsVM;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    private final PermissionsRepository permissionsRepository;

    @Autowired
    public AuthorityService(AuthorityRepository authorityRepository, PermissionsRepository permissionsRepository) {
        this.authorityRepository = authorityRepository;
        this.permissionsRepository = permissionsRepository;
    }

    public AuthorityVM create(AuthorityVM vm) {
        if (authorityRepository.findByNameIgnoreCase(vm.getName()).isPresent()) {
            throw new DataAlreadyExistException("角色名称为【" + vm.getName() + "】的信息已经存在了");
        }
        if (authorityRepository.findByCodeIgnoreCase(vm.getCode()).isPresent()) {
            throw new DataAlreadyExistException("角色唯一标识码为【" + vm.getCode() + "】的信息已经存在了");
        }
        Authority authority = new Authority();
        BeanUtils.copyProperties(vm, authority);
        authority.setState(Constants.DATA_NORMAL_STATE);
        return AuthorityVM.adapt(authorityRepository.save(authority));
    }

    public AuthorityVM update(AuthorityVM vm) {
        Optional<Authority> optional = authorityRepository.findById(vm.getId());
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要修改的角色信息");
        }
        Authority authority = optional.get();
        if (vm.getState() == null || !vm.getState().equals(authority.getState())) {
            throw new BadRequestException("角色状态不允许修改");
        }
        if (authorityRepository.findByNameIgnoreCaseAndIdNot(vm.getName(), vm.getId()).isPresent()) {
            throw new DataAlreadyExistException("角色名称为【" + vm.getName() + "】的信息已经存在了");
        }
        if (authorityRepository.findByCodeIgnoreCaseAndIdNot(vm.getCode(), vm.getId()).isPresent()) {
            throw new DataAlreadyExistException("角色唯一标识码为【" + vm.getCode() + "】的信息已经存在了");
        }
        BeanUtils.copyProperties(vm, authority);
        return AuthorityVM.adapt(authorityRepository.save(authority));
    }

    public AuthorityVM update(UpdateAuthorityPermissionsVM vm) {
        Optional<Authority> optional = authorityRepository.findById(vm.getId());
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要修改的角色信息");
        }
        Authority authority = optional.get();
        authority.setPermissions(setPermissions(vm.getPermissions()));
        return AuthorityVM.adapt(authorityRepository.save(authority));
    }

    public AuthorityVM delete(Long id) {
        Optional<Authority> optional = authorityRepository.findById(id);
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要删除的角色信息");
        }
        Authority authority = optional.get();
        authority.setState(Constants.DATA_DELETE_STATE);
        return AuthorityVM.adapt(authorityRepository.save(authority));
    }

    private Set<Permissions> setPermissions(Set<Long> permissions) {
        return permissions.stream().map(permission -> {
            Optional<Permissions> optional = permissionsRepository.findById(permission);
            if (!optional.isPresent()) {
                throw new BadRequestException("未能在系统中找到数据编号为【" + permission + "】的权限信息");
            }
            return optional.get();
        }).collect(Collectors.toSet());
    }
}