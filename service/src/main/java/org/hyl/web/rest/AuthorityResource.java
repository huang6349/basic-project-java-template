package org.hyl.web.rest;

import org.apache.commons.lang3.StringUtils;
import org.hyl.commons.result.Message;
import org.hyl.commons.result.PaginationUtil;
import org.hyl.commons.result.enums.rest.RestTypeEnum;
import org.hyl.commons.result.rest.RESTful;
import org.hyl.domain.Authority;
import org.hyl.errors.DataNotAlreadyIDException;
import org.hyl.repository.AuthorityRepository;
import org.hyl.service.AuthorityService;
import org.hyl.web.rest.vm.AuthorityVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthorityResource {

    private final Logger log = LoggerFactory.getLogger(AuthorityResource.class);

    private final AuthorityRepository authorityRepository;

    private final AuthorityService authorityService;

    @Autowired
    public AuthorityResource(AuthorityRepository authorityRepository, AuthorityService authorityService) {
        this.authorityRepository = authorityRepository;
        this.authorityService = authorityService;
    }

    @PostMapping("/authority")
    public Message create(@Valid @RequestBody AuthorityVM vm) {
        log.debug("新增角色：{}", vm);

        if (StringUtils.isBlank(vm.getId())) {
            throw new DataNotAlreadyIDException();
        }
        return RESTful.success(RestTypeEnum.POST, authorityService.create(vm));
    }

    @GetMapping("/authority")
    public Message query() {
        log.debug("根据条件查询角色：{}");

        return RESTful.success(RestTypeEnum.GET, authorityRepository.findAll().stream().map(AuthorityVM::adapt));
    }

    @GetMapping("/authority/{id}")
    public Message query(@PathVariable String id) {
        log.debug("根据编号查询角色：{}", id);

        Optional<Authority> optional = authorityRepository.findById(id);
        return RESTful.success(RestTypeEnum.GET, optional.map(AuthorityVM::adapt).orElse(null));
    }

    @GetMapping("/authority/pageable")
    public Message query(@PageableDefault(sort = "seq", direction = Sort.Direction.DESC) Pageable pageable) {
        log.debug("分页查询角色：{}", pageable);

        return PaginationUtil.execute(authorityRepository.findAll(pageable).map(AuthorityVM::adapt));
    }

    @PutMapping("/authority")
    public Message update(@Valid @RequestBody AuthorityVM vm) {
        log.debug("修改角色：{}", vm);

        return RESTful.success(RestTypeEnum.PUT, authorityService.update(vm));
    }

    @DeleteMapping("/authority/{id}")
    public Message delete(@PathVariable String id) {
        log.debug("删除角色：{}", id);

        return RESTful.success(RestTypeEnum.DELETE, authorityService.delete(id));
    }
}
