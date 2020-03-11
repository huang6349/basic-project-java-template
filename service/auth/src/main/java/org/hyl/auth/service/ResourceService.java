package org.hyl.auth.service;

import org.apache.commons.lang3.StringUtils;
import org.hyl.data.config.DataConstants;
import org.hyl.dict.domain.Dict;
import org.hyl.auth.domain.Permissions;
import org.hyl.auth.domain.Resource;
import org.hyl.commons.exception.BadRequestException;
import org.hyl.dict.repository.DictRepository;
import org.hyl.auth.repository.PermissionsRepository;
import org.hyl.auth.repository.ResourceRepository;
import org.hyl.auth.repository.UserRepository;
import org.hyl.auth.security.SecurityUtils;
import org.hyl.auth.web.rest.vm.ResourceVM;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ResourceService {

    private final ResourceRepository resourceRepository;

    private final PermissionsRepository permissionsRepository;

    private final DictRepository dictRepository;

    private final UserRepository userRepository;

    public ResourceService(ResourceRepository resourceRepository, PermissionsRepository permissionsRepository, DictRepository dictRepository, UserRepository userRepository) {
        this.resourceRepository = resourceRepository;
        this.permissionsRepository = permissionsRepository;
        this.dictRepository = dictRepository;
        this.userRepository = userRepository;
    }

    public ResourceVM create(ResourceVM vm) {
        Optional<Permissions> permissions = permissionsRepository.findById(vm.getPermissionsId());
        if (!permissions.isPresent()) {
            throw new BadRequestException("未能在系统中找到数据编号为【" + vm.getPermissionsId() + "】的菜单信息");
        }
        Optional<Dict> method = dictRepository.findById(vm.getMethodId());
        if (!method.isPresent()) {
            throw new BadRequestException("未能在系统中找到数据编号为【" + vm.getMethodId() + "】的资源类型信息");
        }
        Resource resource = new Resource();
        BeanUtils.copyProperties(vm, resource);
        resource.setPermissions(permissions.get());
        resource.setMethod(method.get());
        resource.setState(DataConstants.DATA_NORMAL_STATE);
        return ResourceVM.adapt(resourceRepository.save(resource));
    }

    public ResourceVM update(ResourceVM vm) {
        Optional<Resource> optional = resourceRepository.findById(vm.getId());
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要修改的资源信息");
        }
        Resource resource = optional.get();
        if (DataConstants.DATA_KEEP_STATE.equals(resource.getState())) {
            if (!StringUtils.equals(StringUtils.trimToNull(vm.getPattern()), StringUtils.trimToNull(resource.getPattern()))) {
                throw new BadRequestException("该资源为系统保留资源，无法进行资源地址修改操作");
            }
            if (vm.getMethodId() == null || !vm.getMethodId().equals(resource.getMethod().getId())) {
                throw new BadRequestException("该资源为系统保留资源，无法进行资源类型修改操作");
            }
        }
        Optional<Permissions> permissions = permissionsRepository.findById(vm.getPermissionsId());
        if (!permissions.isPresent()) {
            throw new BadRequestException("未能在系统中找到数据编号为【" + vm.getPermissionsId() + "】的菜单信息");
        }
        Optional<Dict> method = dictRepository.findById(vm.getMethodId());
        if (!method.isPresent()) {
            throw new BadRequestException("未能在系统中找到数据编号为【" + vm.getMethodId() + "】的资源类型信息");
        }
        BeanUtils.copyProperties(vm, resource, "state");
        resource.setPermissions(permissions.get());
        resource.setMethod(method.get());
        return ResourceVM.adapt(resourceRepository.save(resource));
    }

    public ResourceVM delete(Long id) {
        Optional<Resource> optional = resourceRepository.findById(id);
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要删除的资源信息");
        }
        Resource resource = optional.get();
        if (DataConstants.DATA_KEEP_STATE.equals(resource.getState())) {
            throw new BadRequestException("该资源为系统保留资源，无法进行删除操作");
        }
        resource.setState(DataConstants.DATA_DELETE_STATE);
        return ResourceVM.adapt(resourceRepository.save(resource));
    }

    @Transactional(readOnly = true)
    public Optional<List<ResourceVM>> getUserResource() {
        return SecurityUtils.getCurrentUserUsername()
                .flatMap(userRepository::findByUsernameIgnoreCase)
                .map(user -> permissionsRepository.findByAuthoritiesInOrderBySeqDesc(user.getAuthorities())
                        .stream()
                        .distinct()
                        .flatMap(permissions -> permissions.getResources().stream())
                        .distinct()
                        .map(ResourceVM::adapt)
                        .collect(Collectors.toList())
                );
    }
}
