package org.hyl.system.web.rest;

import com.github.wenhao.jpa.Specifications;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.hyl.system.commons.result.enums.RestTypeEnum;
import org.hyl.system.domain.Authority;
import org.hyl.system.errors.DataNotAlreadyIDException;
import org.hyl.system.web.rest.vm.AuthorityVM;
import org.hyl.system.commons.result.Message;
import org.hyl.system.commons.result.PaginationUtil;
import org.hyl.system.commons.result.RESTful;
import org.hyl.system.repository.AuthorityRepository;
import org.hyl.system.service.AuthorityService;
import org.hyl.system.web.rest.vm.UpdateAuthorityPermissionsVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Api(tags = "角色管理", position = 998)
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

    @ApiOperation("新增一个角色")
    @PostMapping("/authority")
    public Message create(@Valid @RequestBody AuthorityVM vm) {
        if (vm.getId() != null) {
            throw new DataNotAlreadyIDException();
        }
        return RESTful.success(RestTypeEnum.POST, authorityService.create(vm));
    }

    @ApiOperation("查询所有的角色")
    @GetMapping("/authority")
    public Message query() {
        return RESTful.success(RestTypeEnum.GET, authorityRepository.findAll().stream().map(AuthorityVM::adapt));
    }

    @ApiOperation("查询一个角色")
    @GetMapping("/authority/{id}")
    public Message query(@PathVariable Long id) {
        Optional<Authority> optional = authorityRepository.findById(id);
        return RESTful.success(RestTypeEnum.GET, optional.map(AuthorityVM::adapt).orElse(null));
    }

    @ApiOperation("分页查询角色")
    @GetMapping("/authority/pageable")
    public Message query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, String name) {
        Specification<Authority> specification = Specifications.<Authority>and()
                .like(StringUtils.isNotBlank(name), "name", "%" + StringUtils.trim(name) + "%")
                .build();
        return PaginationUtil.execute(authorityRepository.findAll(specification, pageable).map(AuthorityVM::adapt));
    }

    @ApiOperation("修改一个角色")
    @PutMapping("/authority")
    public Message update(@Valid @RequestBody AuthorityVM vm) {
        return RESTful.success(RestTypeEnum.PUT, authorityService.update(vm));
    }

    @ApiOperation("修改一个角色的菜单")
    @PutMapping("/authority/permissions")
    public Message update(@Valid @RequestBody UpdateAuthorityPermissionsVM vm) {
        return RESTful.success(RestTypeEnum.PUT, authorityService.update(vm));
    }

    @ApiOperation("删除一个角色")
    @DeleteMapping("/authority/{id}")
    public Message delete(@PathVariable Long id) {
        return RESTful.success(RestTypeEnum.DELETE, authorityService.delete(id));
    }
}
