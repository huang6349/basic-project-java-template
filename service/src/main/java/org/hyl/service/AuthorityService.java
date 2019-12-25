package org.hyl.service;

import org.hyl.config.Constants;
import org.hyl.domain.Authority;
import org.hyl.errors.BadRequestException;
import org.hyl.errors.DataAlreadyExistException;
import org.hyl.repository.AuthorityRepository;
import org.hyl.web.rest.vm.AuthorityVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AuthorityService {

    private final Logger log = LoggerFactory.getLogger(AuthorityService.class);

    private final AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public AuthorityVM create(AuthorityVM vm) {
        log.debug("新增角色：{}", vm);

        if (authorityRepository.findByIdIgnoreCase(vm.getId()).isPresent()) {
            throw new DataAlreadyExistException("角色编号为【" + vm.getId() + "】的信息已经存在了");
        }
        if (authorityRepository.findByNameIgnoreCase(vm.getName()).isPresent()) {
            throw new DataAlreadyExistException("角色名称为【" + vm.getName() + "】的信息已经存在了");
        }
        Authority authority = new Authority();
        BeanUtils.copyProperties(vm, authority);
        authority.setState(Constants.DATA_NORMAL_STATE);
        return AuthorityVM.adapt(authorityRepository.save(authority));
    }

    public AuthorityVM update(AuthorityVM vm) {
        log.debug("修改角色：{}", vm);

        Optional<Authority> optional = authorityRepository.findByIdIgnoreCase(vm.getId());
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
        BeanUtils.copyProperties(vm, authority);
        return AuthorityVM.adapt(authorityRepository.save(authority));
    }

    public AuthorityVM delete(String id) {
        log.debug("删除角色：{}", id);

        Optional<Authority> optional = authorityRepository.findByIdIgnoreCase(id);
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要删除的角色信息");
        }
        Authority authority = optional.get();
        authority.setState(Constants.DATA_DELETE_STATE);
        return AuthorityVM.adapt(authorityRepository.save(authority));
    }
}
