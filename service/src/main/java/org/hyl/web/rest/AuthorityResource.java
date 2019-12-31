package org.hyl.web.rest;

import org.hyl.commons.result.Message;
import org.hyl.commons.pagination.PaginationUtil;
import org.hyl.commons.result.enums.RestTypeEnum;
import org.hyl.commons.result.RESTful;
import org.hyl.domain.Authority;
import org.hyl.errors.DataNotAlreadyIDException;
import org.hyl.repository.AuthorityRepository;
import org.hyl.service.AuthorityService;
import org.hyl.web.rest.vm.AuthorityVM;
import org.hyl.web.rest.vm.UpdateAuthorityPermissionsVM;
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

    private final AuthorityRepository authorityRepository;

    private final AuthorityService authorityService;

    @Autowired
    public AuthorityResource(AuthorityRepository authorityRepository, AuthorityService authorityService) {
        this.authorityRepository = authorityRepository;
        this.authorityService = authorityService;
    }

    @PostMapping("/authority")
    public Message create(@Valid @RequestBody AuthorityVM vm) {
        if (vm.getId() != null) {
            throw new DataNotAlreadyIDException();
        }
        return RESTful.success(RestTypeEnum.POST, authorityService.create(vm));
    }

    @GetMapping("/authority")
    public Message query() {
        return RESTful.success(RestTypeEnum.GET, authorityRepository.findAll().stream().map(AuthorityVM::adapt));
    }

    @GetMapping("/authority/{id}")
    public Message query(@PathVariable Long id) {
        Optional<Authority> optional = authorityRepository.findById(id);
        return RESTful.success(RestTypeEnum.GET, optional.map(AuthorityVM::adapt).orElse(null));
    }

    @GetMapping("/authority/pageable")
    public Message query(@PageableDefault(sort = "seq", direction = Sort.Direction.DESC) Pageable pageable) {
        return PaginationUtil.execute(authorityRepository.findAll(pageable).map(AuthorityVM::adapt));
    }

    @PutMapping("/authority")
    public Message update(@Valid @RequestBody AuthorityVM vm) {
        return RESTful.success(RestTypeEnum.PUT, authorityService.update(vm));
    }

    @PutMapping("/authority/permissions")
    public Message update(@Valid @RequestBody UpdateAuthorityPermissionsVM vm) {
        return RESTful.success(RestTypeEnum.PUT, authorityService.update(vm));
    }

    @DeleteMapping("/authority/{id}")
    public Message delete(@PathVariable Long id) {
        return RESTful.success(RestTypeEnum.DELETE, authorityService.delete(id));
    }
}
