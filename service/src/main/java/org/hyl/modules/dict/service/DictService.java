package org.hyl.modules.dict.service;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.hyl.config.GlobalConstants;
import org.hyl.modules.commons.exception.BadRequestException;
import org.hyl.modules.commons.exception.DataAlreadyExistException;
import org.hyl.modules.data.auditing.utils.LevelUtil;
import org.hyl.modules.dict.domain.Dict;
import org.hyl.modules.dict.repository.DictRepository;
import org.hyl.modules.dict.domain.vm.DictVM;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class DictService {

    private final DictRepository dictRepository;

    public DictService(DictRepository dictRepository) {
        this.dictRepository = dictRepository;
    }

    public DictVM create(DictVM vm) {
        if ((vm.getPid() == null || vm.getPid().equals(0L)) && StringUtils.isBlank(vm.getCode())) {
            throw new BadRequestException("一级字典信息的唯一标识码不能为空");
        }
        if ((vm.getPid() == null || vm.getPid().equals(0L)) && StringUtils.isNotBlank(vm.getData())) {
            throw new BadRequestException("一级字典信息的数据必须为空");
        }
        if (vm.getPid() != null && !vm.getPid().equals(0L) && StringUtils.isNotBlank(vm.getCode())) {
            throw new BadRequestException("子级字典信息的唯一标识码必须为空");
        }
        if (vm.getPid() != null && !vm.getPid().equals(0L) && StringUtils.isBlank(vm.getData())) {
            throw new BadRequestException("子级字典信息的数据不能为空");
        }
        if ((vm.getPid() == null || vm.getPid().equals(0L)) && dictRepository.findByCodeIgnoreCase(vm.getCode()).isPresent()) {
            throw new DataAlreadyExistException("字典唯一标识码为【" + vm.getCode() + "】的信息已经存在了");
        }
        Dict dict = new Dict();
        BeanUtils.copyProperties(vm, dict);
        dict.setState(GlobalConstants.DATA_NORMAL_STATE);
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
        if (GlobalConstants.DATA_KEEP_STATE.equals(dict.getState())) {
            if (!StringUtils.equals(StringUtils.trimToNull(vm.getName()), StringUtils.trimToNull(dict.getName()))) {
                throw new BadRequestException("该字典为系统保留字典，无法进行字典名称修改操作");
            }
            if (!StringUtils.equals(StringUtils.trimToNull(vm.getCode()), StringUtils.trimToNull(dict.getCode()))) {
                throw new BadRequestException("该字典为系统保留字典，无法进行字典唯一标识码修改操作");
            }
            if (!StringUtils.equals(StringUtils.trimToNull(vm.getData()), StringUtils.trimToNull(dict.getData()))) {
                throw new BadRequestException("该字典为系统保留字典，无法进行字典数据修改操作");
            }
        }
        if ((vm.getPid() == null || vm.getPid().equals(0L)) && StringUtils.isBlank(vm.getCode())) {
            throw new BadRequestException("一级字典信息的唯一标识码不能为空");
        }
        if ((vm.getPid() == null || vm.getPid().equals(0L)) && StringUtils.isNotBlank(vm.getData())) {
            throw new BadRequestException("一级字典信息的数据必须为空");
        }
        if (vm.getPid() != null && !vm.getPid().equals(0L) && StringUtils.isNotBlank(vm.getCode())) {
            throw new BadRequestException("子级字典信息的唯一标识码必须为空");
        }
        if (vm.getPid() != null && !vm.getPid().equals(0L) && StringUtils.isBlank(vm.getData())) {
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
        if (GlobalConstants.DATA_KEEP_STATE.equals(dict.getState())) {
            throw new BadRequestException("该字典为系统保留字典，无法进行删除操作");
        }
        dict.setState(GlobalConstants.DATA_DELETE_STATE);
        batchUpdateState(optional.get().getLevel(), GlobalConstants.DATA_DELETE_STATE, id);
        return DictVM.adapt(dictRepository.save(dict));
    }

    public Dict findById(Long id) {
        return findById(id, "字典");
    }

    public Dict findById(Long id, Long pid) {
        return findById(id, pid, "字典");
    }

    public Dict findById(Long id, String name) {
        Optional<Dict> dict = dictRepository.findById(id);
        if (!dict.isPresent()) {
            throw new BadRequestException(StrUtil.format("未能在系统中找到数据编号为【{}】的{}信息", id, name));
        }
        return dict.get();
    }

    public Dict findById(Long id, Long pid, String name) {
        Dict dict = findById(id, name);
        if (!dict.getPid().equals(pid)) {
            throw new BadRequestException(StrUtil.format("数据编号为【{}】的{}信息与对应的分类不匹配", id, name));
        }
        return dict;
    }

    private String getLevel(Long id) {
        return dictRepository.findById(id).map(Dict::getLevel).orElse(null);
    }

    private void batchUpdateLevel(String beforeLevel, String afterLevel, Long id) {
        dictRepository.findByLevelIgnoreCaseStartingWith(LevelUtil.calculateLevel(beforeLevel, id)).forEach(dict -> {
            dict.setLevel(StringUtils.replaceOnce(dict.getLevel(), beforeLevel, afterLevel));
            dictRepository.save(dict);
        });
    }

    private void batchUpdateState(String level, Byte state, Long id) {
        dictRepository.findByLevelIgnoreCaseStartingWith(LevelUtil.calculateLevel(level, id)).forEach(dict -> {
            dict.setState(state);
            dictRepository.save(dict);
        });
    }
}
