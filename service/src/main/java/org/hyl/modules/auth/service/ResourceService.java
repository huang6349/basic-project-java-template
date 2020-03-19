package org.hyl.modules.auth.service;

import org.apache.commons.lang3.StringUtils;
import org.hyl.config.GlobalConstants;
import org.hyl.modules.auth.domain.Permission;
import org.hyl.modules.auth.domain.Resource;
import org.hyl.modules.auth.repository.PermissionRepository;
import org.hyl.modules.auth.repository.ResourceRepository;
import org.hyl.modules.auth.repository.UserRepository;
import org.hyl.modules.auth.security.SecurityUtils;
import org.hyl.modules.auth.domain.vm.ResourceVM;
import org.hyl.modules.commons.exception.BadRequestException;
import org.hyl.modules.dict.domain.Dict;
import org.hyl.modules.dict.service.DictService;
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

    private final PermissionRepository permissionRepository;

    private final UserRepository userRepository;

    private final DictService dictService;

    public ResourceService(ResourceRepository resourceRepository, PermissionRepository permissionRepository, UserRepository userRepository, DictService dictService) {
        this.resourceRepository = resourceRepository;
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
        this.dictService = dictService;
    }

    public ResourceVM create(ResourceVM vm) {
        Optional<Permission> permission = permissionRepository.findById(vm.getPermissionId());
        if (!permission.isPresent()) {
            throw new BadRequestException("未能在系统中找到数据编号为【" + vm.getPermissionId() + "】的菜单信息");
        }
        Dict method = dictService.findById(vm.getMethodId(), GlobalConstants.DICT_ID_METHOD, "请求类型");
        Resource resource = new Resource();
        BeanUtils.copyProperties(vm, resource);
        resource.setPermission(permission.get());
        resource.setMethod(method);
        resource.setState(GlobalConstants.DATA_NORMAL_STATE);
        return ResourceVM.adapt(resourceRepository.save(resource));
    }

    public ResourceVM update(ResourceVM vm) {
        Optional<Resource> optional = resourceRepository.findById(vm.getId());
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要修改的资源信息");
        }
        Resource resource = optional.get();
        if (GlobalConstants.DATA_KEEP_STATE.equals(resource.getState())) {
            if (!StringUtils.equals(StringUtils.trimToNull(vm.getPattern()), StringUtils.trimToNull(resource.getPattern()))) {
                throw new BadRequestException("该资源为系统保留资源，无法进行资源地址修改操作");
            }
            if (vm.getMethodId() == null || !vm.getMethodId().equals(resource.getMethod().getId())) {
                throw new BadRequestException("该资源为系统保留资源，无法进行资源类型修改操作");
            }
        }
        Optional<Permission> permission = permissionRepository.findById(vm.getPermissionId());
        if (!permission.isPresent()) {
            throw new BadRequestException("未能在系统中找到数据编号为【" + vm.getPermissionId() + "】的菜单信息");
        }
        Dict method = dictService.findById(vm.getMethodId(), GlobalConstants.DICT_ID_METHOD, "请求类型");
        BeanUtils.copyProperties(vm, resource, "state");
        resource.setPermission(permission.get());
        resource.setMethod(method);
        return ResourceVM.adapt(resourceRepository.save(resource));
    }

    public ResourceVM delete(Long id) {
        Optional<Resource> optional = resourceRepository.findById(id);
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要删除的资源信息");
        }
        Resource resource = optional.get();
        if (GlobalConstants.DATA_KEEP_STATE.equals(resource.getState())) {
            throw new BadRequestException("该资源为系统保留资源，无法进行删除操作");
        }
        resource.setState(GlobalConstants.DATA_DELETE_STATE);
        return ResourceVM.adapt(resourceRepository.save(resource));
    }

    @Transactional(readOnly = true)
    public Optional<List<ResourceVM>> getUserResource() {
        return SecurityUtils.getCurrentUserUsername()
                .flatMap(userRepository::findByUsernameIgnoreCase)
                .map(user -> permissionRepository.findByAuthoritiesInOrderBySeqDesc(user.getAuthorities())
                        .stream()
                        .distinct()
                        .flatMap(permission -> permission.getResources().stream())
                        .distinct()
                        .map(ResourceVM::adapt)
                        .collect(Collectors.toList())
                );
    }
}
