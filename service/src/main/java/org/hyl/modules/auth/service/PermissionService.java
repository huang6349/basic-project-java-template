package org.hyl.modules.auth.service;

import org.apache.commons.lang3.StringUtils;
import org.hyl.config.GlobalConstants;
import org.hyl.modules.auth.domain.Permission;
import org.hyl.modules.auth.repository.PermissionRepository;
import org.hyl.modules.auth.repository.UserRepository;
import org.hyl.modules.auth.security.SecurityUtils;
import org.hyl.modules.auth.domain.vm.PermissionLevelVM;
import org.hyl.modules.auth.domain.vm.PermissionVM;
import org.hyl.modules.commons.exception.BadRequestException;
import org.hyl.modules.commons.exception.DataAlreadyExistException;
import org.hyl.modules.data.auditing.utils.impl.DefaultLevelUtil;
import org.hyl.modules.data.auditing.utils.LevelUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PermissionService {

    private LevelUtil<PermissionLevelVM> levelUtil = new DefaultLevelUtil<>();

    private final PermissionRepository permissionRepository;

    private final UserRepository userRepository;

    public PermissionService(PermissionRepository permissionRepository, UserRepository userRepository) {
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
    }

    public PermissionVM create(PermissionVM vm) {
        if (permissionRepository.findByNameIgnoreCase(vm.getName()).isPresent()) {
            throw new DataAlreadyExistException("菜单名称为【" + vm.getName() + "】的信息已经存在了");
        }
        Permission permission = new Permission();
        BeanUtils.copyProperties(vm, permission);
        permission.setState(GlobalConstants.DATA_NORMAL_STATE);
        permission.setLevel(LevelUtil.calculateLevel(getLevel(vm.getPid()), vm.getPid()));
        return PermissionVM.adapt(permissionRepository.save(permission));
    }

    public PermissionVM update(PermissionVM vm) {
        Optional<Permission> optional = permissionRepository.findById(vm.getId());
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要修改的菜单信息");
        }
        Permission permission = optional.get();
        if (vm.getState() == null || !vm.getState().equals(permission.getState())) {
            throw new BadRequestException("菜单状态不允许修改");
        }
        if (GlobalConstants.DATA_KEEP_STATE.equals(permission.getState())) {
            if (!StringUtils.equals(StringUtils.trimToNull(vm.getName()), StringUtils.trimToNull(permission.getName()))) {
                throw new BadRequestException("该菜单为系统保留菜单，无法进行菜单名称修改操作");
            }
            if (!StringUtils.equals(StringUtils.trimToNull(vm.getPath()), StringUtils.trimToNull(permission.getPath()))) {
                throw new BadRequestException("该菜单为系统保留菜单，无法进行菜单路径修改操作");
            }
            if (!StringUtils.equals(StringUtils.trimToNull(vm.getIcon()), StringUtils.trimToNull(permission.getIcon()))) {
                throw new BadRequestException("该菜单为系统保留菜单，无法进行菜单图标修改操作");
            }
        }
        if (permissionRepository.findByNameIgnoreCaseAndIdNot(vm.getName(), vm.getId()).isPresent()) {
            throw new DataAlreadyExistException("菜单名称为【" + vm.getName() + "】的信息已经存在了");
        }
        String beforeLevel = permission.getLevel();
        String afterLevel = LevelUtil.calculateLevel(getLevel(vm.getPid()), vm.getPid());
        if (StringUtils.startsWith(afterLevel, LevelUtil.calculateLevel(beforeLevel, vm.getId()))) {
            throw new BadRequestException("不允许将自己设置为自己的子级");
        }
        BeanUtils.copyProperties(vm, permission);
        if (!StringUtils.equals(beforeLevel, afterLevel)) {
            permission.setLevel(afterLevel);
            batchUpdateLevel(beforeLevel, afterLevel, vm.getId());
        }
        return PermissionVM.adapt(permissionRepository.save(permission));
    }

    public PermissionVM delete(Long id) {
        Optional<Permission> optional = permissionRepository.findById(id);
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要删除的菜单信息");
        }
        Permission permission = optional.get();
        if (GlobalConstants.DATA_KEEP_STATE.equals(permission.getState())) {
            throw new BadRequestException("该菜单为系统保留菜单，无法进行删除操作");
        }
        permission.setState(GlobalConstants.DATA_DELETE_STATE);
        batchUpdateState(optional.get().getLevel(), GlobalConstants.DATA_DELETE_STATE, id);
        return PermissionVM.adapt(permissionRepository.save(permission));
    }

    @Transactional(readOnly = true)
    public Optional<List<PermissionLevelVM>> getUserPermissions() {
        return SecurityUtils.getCurrentUserUsername()
                .flatMap(userRepository::findByUsernameIgnoreCase)
                .map(user -> permissionRepository.findByAuthoritiesInOrderBySeqDesc(user.getAuthorities())
                        .stream()
                        .distinct()
                        .map(PermissionLevelVM::adapt)
                        .collect(Collectors.toList())
                );
    }

    @Transactional(readOnly = true)
    public Optional<List<PermissionLevelVM>> getUserPermissionsToTree() {
        return SecurityUtils.getCurrentUserUsername()
                .flatMap(userRepository::findByUsernameIgnoreCase)
                .map(user -> levelUtil.listToTree(permissionRepository.findByAuthoritiesInOrderBySeqDesc(user.getAuthorities())
                        .stream()
                        .distinct()
                        .map(PermissionLevelVM::adapt)
                        .collect(Collectors.toList()))
                );
    }

    private String getLevel(Long id) {
        return permissionRepository.findById(id).map(Permission::getLevel).orElse(null);
    }

    private void batchUpdateLevel(String beforeLevel, String afterLevel, Long id) {
        permissionRepository.findByLevelIgnoreCaseStartingWith(LevelUtil.calculateLevel(beforeLevel, id)).forEach(permission -> {
            permission.setLevel(StringUtils.replaceOnce(permission.getLevel(), beforeLevel, afterLevel));
            permissionRepository.save(permission);
        });
    }

    private void batchUpdateState(String level, Byte state, Long id) {
        permissionRepository.findByLevelIgnoreCaseStartingWith(LevelUtil.calculateLevel(level, id)).forEach(permission -> {
            permission.setState(state);
            permissionRepository.save(permission);
        });
    }
}
