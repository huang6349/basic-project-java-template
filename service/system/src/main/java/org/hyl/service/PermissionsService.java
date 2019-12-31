package org.hyl.service;

import org.hyl.config.Constants;
import org.hyl.errors.DataAlreadyExistException;
import org.hyl.web.rest.vm.PermissionsVM;
import org.apache.commons.lang3.StringUtils;
import org.hyl.data.auditing.LevelUtil;
import org.hyl.domain.Permissions;
import org.hyl.errors.BadRequestException;
import org.hyl.repository.PermissionsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PermissionsService {

    private final PermissionsRepository permissionsRepository;

    @Autowired
    public PermissionsService(PermissionsRepository permissionsRepository) {
        this.permissionsRepository = permissionsRepository;
    }

    public PermissionsVM create(PermissionsVM vm) {
        if (permissionsRepository.findByNameIgnoreCase(vm.getName()).isPresent()) {
            throw new DataAlreadyExistException("权限名称为【" + vm.getName() + "】的信息已经存在了");
        }
        Permissions permissions = new Permissions();
        BeanUtils.copyProperties(vm, permissions);
        permissions.setState(Constants.DATA_NORMAL_STATE);
        permissions.setLevel(LevelUtil.calculateLevel(getLevel(vm.getPid()), vm.getPid()));
        return PermissionsVM.adapt(permissionsRepository.save(permissions));
    }

    public PermissionsVM update(PermissionsVM vm) {
        Optional<Permissions> optional = permissionsRepository.findById(vm.getId());
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要修改的权限信息");
        }
        Permissions permissions = optional.get();
        if (vm.getState() == null || !vm.getState().equals(permissions.getState())) {
            throw new BadRequestException("权限状态不允许修改");
        }
        if (permissionsRepository.findByNameIgnoreCaseAndIdNot(vm.getName(), vm.getId()).isPresent()) {
            throw new DataAlreadyExistException("权限名称为【" + vm.getName() + "】的信息已经存在了");
        }
        String beforeLevel = permissions.getLevel();
        String afterLevel = LevelUtil.calculateLevel(getLevel(vm.getPid()), vm.getPid());
        if (StringUtils.startsWith(afterLevel, LevelUtil.calculateLevel(beforeLevel, vm.getId()))) {
            throw new BadRequestException("不允许将自己设置为自己的子级");
        }
        BeanUtils.copyProperties(vm, permissions);
        if (!StringUtils.equals(beforeLevel, afterLevel)) {
            permissions.setLevel(afterLevel);
            batchUpdateLevel(beforeLevel, afterLevel, vm.getId());
        }
        return PermissionsVM.adapt(permissionsRepository.save(permissions));
    }

    public PermissionsVM delete(Long id) {
        Optional<Permissions> optional = permissionsRepository.findById(id);
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要删除的权限信息");
        }
        Permissions permissions = optional.get();
        permissions.setState(Constants.DATA_DELETE_STATE);
        batchUpdateState(optional.get().getLevel(), Constants.DATA_DELETE_STATE, id);
        return PermissionsVM.adapt(permissionsRepository.save(permissions));
    }

    private String getLevel(Long id) {
        return permissionsRepository.findById(id).map(Permissions::getLevel).orElse(null);
    }

    private void batchUpdateLevel(String beforeLevel, String afterLevel, Long id) {
        permissionsRepository.findByLevelIgnoreCaseStartingWith(LevelUtil.calculateLevel(beforeLevel, id)).forEach(permissions -> {
            permissions.setLevel(StringUtils.replaceOnce(permissions.getLevel(), beforeLevel, afterLevel));
            permissionsRepository.save(permissions);
        });
    }

    private void batchUpdateState(String level, Byte state, Long id) {
        permissionsRepository.findByLevelIgnoreCaseStartingWith(LevelUtil.calculateLevel(level, id)).forEach(permissions -> {
            permissions.setState(state);
            permissionsRepository.save(permissions);
        });
    }
}
