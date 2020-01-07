package org.hyl.system.service;

import org.apache.commons.lang3.StringUtils;
import org.hyl.data.auditing.LevelUtil;
import org.hyl.data.config.DataConstants;
import org.hyl.system.domain.Dict;
import org.hyl.system.errors.BadRequestException;
import org.hyl.system.errors.DataAlreadyExistException;
import org.hyl.system.repository.DictRepository;
import org.hyl.system.web.rest.vm.DictVM;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class DictService {

    private final DictRepository dictRepository;

    @Autowired
    public DictService(DictRepository dictRepository) {
        this.dictRepository = dictRepository;
    }

    public DictVM create(DictVM vm) {
        if ((vm.getPid() == null || vm.getPid().equals(0L)) && vm.getCode() == null) {
            throw new BadRequestException("一级字典信息的唯一标识码不能为空");
        }
        if ((vm.getPid() == null || vm.getPid().equals(0L)) && vm.getData() != null) {
            throw new BadRequestException("一级字典信息的数据必须为空");
        }
        if (vm.getPid() != null && !vm.getPid().equals(0L) && vm.getCode() != null) {
            throw new BadRequestException("子级字典信息的唯一标识码必须为空");
        }
        if (vm.getPid() != null && !vm.getPid().equals(0L) && vm.getData() == null) {
            throw new BadRequestException("子级字典信息的数据不能为空");
        }
        if ((vm.getPid() == null || vm.getPid().equals(0L)) && dictRepository.findByCodeIgnoreCase(vm.getCode()).isPresent()) {
            throw new DataAlreadyExistException("字典唯一标识码为【" + vm.getCode() + "】的信息已经存在了");
        }
        Dict dict = new Dict();
        BeanUtils.copyProperties(vm, dict);
        dict.setState(DataConstants.DATA_NORMAL_STATE);
        dict.setLevel(LevelUtil.calculateLevel(getLevel(vm.getPid()), vm.getPid()));
        return DictVM.adapt(dictRepository.save(dict));
    }

    public DictVM update(DictVM vm) {
        Optional<Dict> optional = dictRepository.findById(vm.getId());
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要修改的字典信息");
        }
        Dict dict = optional.get();
        if (vm.getState() == null || !vm.getState().equals(dict.getState())) {
            throw new BadRequestException("字典状态不允许修改");
        }
        if (DataConstants.DATA_KEEP_STATE.equals(dict.getState())) {
            if (vm.getName() == null || !vm.getName().equals(dict.getName())) {
                throw new BadRequestException("该字典为系统保留字典，无法进行字典名称修改操作");
            }
            if (vm.getCode() == null || !vm.getCode().equals(dict.getCode())) {
                throw new BadRequestException("该字典为系统保留字典，无法进行字典唯一标识码修改操作");
            }
            if (vm.getData() == null || !vm.getData().equals(dict.getData())) {
                throw new BadRequestException("该字典为系统保留字典，无法进行字典数据修改操作");
            }
        }
        if ((vm.getPid() == null || vm.getPid().equals(0L)) && vm.getCode() == null) {
            throw new BadRequestException("一级字典信息的唯一标识码不能为空");
        }
        if ((vm.getPid() == null || vm.getPid().equals(0L)) && vm.getData() != null) {
            throw new BadRequestException("一级字典信息的数据必须为空");
        }
        if (vm.getPid() != null && !vm.getPid().equals(0L) && vm.getCode() != null) {
            throw new BadRequestException("子级字典信息的唯一标识码必须为空");
        }
        if (vm.getPid() != null && !vm.getPid().equals(0L) && vm.getData() == null) {
            throw new BadRequestException("子级字典信息的数据不能为空");
        }
        if ((vm.getPid() == null || vm.getPid().equals(0L)) && dictRepository.findByCodeIgnoreCaseAndIdNot(vm.getCode(), vm.getId()).isPresent()) {
            throw new DataAlreadyExistException("字典唯一标识码为【" + vm.getCode() + "】的信息已经存在了");
        }
        String beforeLevel = dict.getLevel();
        String afterLevel = LevelUtil.calculateLevel(getLevel(vm.getPid()), vm.getPid());
        if (StringUtils.startsWith(afterLevel, LevelUtil.calculateLevel(beforeLevel, vm.getId()))) {
            throw new BadRequestException("不允许将自己设置为自己的子级");
        }
        BeanUtils.copyProperties(vm, dict);
        if (!StringUtils.equals(beforeLevel, afterLevel)) {
            dict.setLevel(afterLevel);
            batchUpdateLevel(beforeLevel, afterLevel, vm.getId());
        }
        return DictVM.adapt(dictRepository.save(dict));
    }

    public DictVM delete(Long id) {
        Optional<Dict> optional = dictRepository.findById(id);
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要删除的字典信息");
        }
        Dict dict = optional.get();
        if (DataConstants.DATA_KEEP_STATE.equals(dict.getState())) {
            throw new BadRequestException("该菜单为系统保留字典，无法进行删除操作");
        }
        dict.setState(DataConstants.DATA_DELETE_STATE);
        batchUpdateState(optional.get().getLevel(), DataConstants.DATA_DELETE_STATE, id);
        return DictVM.adapt(dictRepository.save(dict));
    }

    private String getLevel(Long id) {
        return dictRepository.findById(id).map(Dict::getLevel).orElse(null);
    }

    private void batchUpdateLevel(String beforeLevel, String afterLevel, Long id) {
        dictRepository.findByLevelIgnoreCaseStartingWith(LevelUtil.calculateLevel(beforeLevel, id)).forEach(permissions -> {
            permissions.setLevel(StringUtils.replaceOnce(permissions.getLevel(), beforeLevel, afterLevel));
            dictRepository.save(permissions);
        });
    }

    private void batchUpdateState(String level, Byte state, Long id) {
        dictRepository.findByLevelIgnoreCaseStartingWith(LevelUtil.calculateLevel(level, id)).forEach(permissions -> {
            permissions.setState(state);
            dictRepository.save(permissions);
        });
    }
}
