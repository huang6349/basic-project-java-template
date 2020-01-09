package org.hyl.system.service;

import org.apache.commons.lang3.StringUtils;
import org.hyl.data.config.DataConstants;
import org.hyl.system.domain.Authority;
import org.hyl.system.errors.DataAlreadyExistException;
import org.hyl.system.web.rest.vm.AuthorityVM;
import org.hyl.system.domain.Permissions;
import org.hyl.system.errors.BadRequestException;
import org.hyl.system.repository.AuthorityRepository;
import org.hyl.system.repository.PermissionsRepository;
import org.hyl.system.web.rest.vm.UpdateAuthorityPermissionsVM;
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
        authority.setState(DataConstants.DATA_NORMAL_STATE);
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
        if (DataConstants.DATA_KEEP_STATE.equals(authority.getState())) {
            if (!StringUtils.equals(StringUtils.trimToNull(vm.getName()), StringUtils.trimToNull(authority.getName()))) {
                throw new BadRequestException("该角色为系统保留角色，无法进行角色名称修改操作");
            }
            if (!StringUtils.equals(StringUtils.trimToNull(vm.getCode()), StringUtils.trimToNull(authority.getCode()))) {
                throw new BadRequestException("该角色为系统保留角色，无法进行角色唯一标识码修改操作");
            }
        }
        if (authorityRepository.findByNameIgnoreCaseAndIdNot(vm.getName(), vm.getId()).isPresent()) {
            throw new DataAlreadyExistException("角色名称为【" + vm.getName() + "】的信息已经存在了");
        }
        if (authorityRepository.findByCodeIgnoreCaseAndIdNot(vm.getCode(), vm.getId()).isPresent()) {
            throw new DataAlreadyExistException("角色唯一标识码为【" + vm.getCode() + "】的信息已经存在了");
        }
        BeanUtils.copyProperties(vm, authority, "permissions");
        return AuthorityVM.adapt(authorityRepository.save(authority));
    }

    public AuthorityVM update(UpdateAuthorityPermissionsVM vm) {
        Optional<Authority> optional = authorityRepository.findById(vm.getId());
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要授权的角色信息");
        }
        Authority authority = optional.get();
        if (DataConstants.DATA_KEEP_STATE.equals(authority.getState())) {
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
        if (DataConstants.DATA_KEEP_STATE.equals(authority.getState())) {
            throw new BadRequestException("该角色为系统保留角色，无法进行删除操作");
        }
        authority.setState(DataConstants.DATA_DELETE_STATE);
        return AuthorityVM.adapt(authorityRepository.save(authority));
    }

    private Set<Permissions> setPermissions(Set<Long> permissions) {
        return permissions.stream().map(permission -> {
            Optional<Permissions> optional = permissionsRepository.findById(permission);
            if (!optional.isPresent()) {
                throw new BadRequestException("未能在系统中找到数据编号为【" + permission + "】的菜单信息");
            }
            return optional.get();
        }).collect(Collectors.toSet());
    }
}
