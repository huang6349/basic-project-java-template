package org.hyl.modules.auth.service;

import org.apache.commons.lang3.StringUtils;
import org.hyl.config.GlobalConstants;
import org.hyl.modules.auth.domain.Authority;
import org.hyl.modules.auth.domain.Permission;
import org.hyl.modules.auth.repository.AuthorityRepository;
import org.hyl.modules.auth.repository.PermissionRepository;
import org.hyl.modules.auth.domain.vm.AuthorityVM;
import org.hyl.modules.auth.domain.vm.UpdateAuthorityPermissionsVM;
import org.hyl.modules.commons.exception.BadRequestException;
import org.hyl.modules.commons.exception.DataAlreadyExistException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    private final PermissionRepository permissionRepository;

    public AuthorityService(AuthorityRepository authorityRepository, PermissionRepository permissionRepository) {
        this.authorityRepository = authorityRepository;
        this.permissionRepository = permissionRepository;
    }

    public AuthorityVM create(AuthorityVM vm) {
        if (authorityRepository.findByNameIgnoreCase(vm.getName()).isPresent()) {
            throw new DataAlreadyExistException("角色名称为【" + vm.getName() + "】的信息已经存在了");
        }
        if (authorityRepository.findByCodeIgnoreCase(StringUtils.join("ROLE_", vm.getCode())).isPresent()) {
            throw new DataAlreadyExistException("角色唯一标识码为【" + vm.getCode() + "】的信息已经存在了");
        }
        Authority authority = new Authority();
        BeanUtils.copyProperties(vm, authority);
        authority.setCode(StringUtils.join("ROLE_", vm.getCode()));
        authority.setState(GlobalConstants.DATA_NORMAL_STATE);
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
        if (GlobalConstants.DATA_KEEP_STATE.equals(authority.getState())) {
            if (!StringUtils.equals(StringUtils.trimToNull(vm.getName()), StringUtils.trimToNull(authority.getName()))) {
                throw new BadRequestException("该角色为系统保留角色，无法进行角色名称修改操作");
            }
            if (!StringUtils.equals(StringUtils.trimToNull(StringUtils.join("ROLE_", vm.getCode())), StringUtils.trimToNull(authority.getCode()))) {
                throw new BadRequestException("该角色为系统保留角色，无法进行角色唯一标识码修改操作");
            }
        }
        if (authorityRepository.findByNameIgnoreCaseAndIdNot(vm.getName(), vm.getId()).isPresent()) {
            throw new DataAlreadyExistException("角色名称为【" + vm.getName() + "】的信息已经存在了");
        }
        if (authorityRepository.findByCodeIgnoreCaseAndIdNot(StringUtils.join("ROLE_", vm.getCode()), vm.getId()).isPresent()) {
            throw new DataAlreadyExistException("角色唯一标识码为【" + vm.getCode() + "】的信息已经存在了");
        }
        BeanUtils.copyProperties(vm, authority, "permissions");
        authority.setCode(StringUtils.join("ROLE_", vm.getCode()));
        return AuthorityVM.adapt(authorityRepository.save(authority));
    }

    public AuthorityVM update(UpdateAuthorityPermissionsVM vm) {
        Optional<Authority> optional = authorityRepository.findById(vm.getId());
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要授权的角色信息");
        }
        Authority authority = optional.get();
        if (GlobalConstants.DATA_KEEP_STATE.equals(authority.getState())) {
            throw new BadRequestException("该角色为系统保留角色，无法进行授权操作");
        }
        authority.setPermissions(setPermissions(vm.getPermissions()));
        return AuthorityVM.adapt(authorityRepository.save(authority));
    }

    public AuthorityVM delete(Long id) {
        Optional<Authority> optional = authorityRepository.findById(id);
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要删除的角色信息");
        }
        Authority authority = optional.get();
        if (GlobalConstants.DATA_KEEP_STATE.equals(authority.getState())) {
            throw new BadRequestException("该角色为系统保留角色，无法进行删除操作");
        }
        authority.setState(GlobalConstants.DATA_DELETE_STATE);
        return AuthorityVM.adapt(authorityRepository.save(authority));
    }

    private Set<Permission> setPermissions(Set<Long> permissions) {
        return permissions.stream().map(permission -> {
            Optional<Permission> optional = permissionRepository.findById(permission);
            if (!optional.isPresent()) {
                throw new BadRequestException("未能在系统中找到数据编号为【" + permission + "】的菜单信息");
            }
            return optional.get();
        }).collect(Collectors.toSet());
    }
}
