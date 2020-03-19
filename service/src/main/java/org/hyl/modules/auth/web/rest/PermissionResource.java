package org.hyl.modules.auth.web.rest;

import com.github.wenhao.jpa.Specifications;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.hyl.modules.auth.domain.Permission;
import org.hyl.modules.auth.repository.PermissionRepository;
import org.hyl.modules.auth.service.PermissionService;
import org.hyl.modules.auth.domain.vm.PermissionVM;
import org.hyl.modules.auth.service.mapper.PermissionLevelMapper;
import org.hyl.modules.auth.service.mapper.PermissionMapper;
import org.hyl.modules.commons.exception.DataNotAlreadyIDException;
import org.hyl.modules.commons.result.Message;
import org.hyl.modules.commons.result.PaginationUtil;
import org.hyl.modules.commons.result.RESTful;
import org.hyl.modules.commons.result.enums.RestTypeEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "菜单管理")
@RestController
@RequestMapping("/api")
public class PermissionResource {

    private final PermissionRepository permissionRepository;

    private final PermissionService permissionService;

    private final PermissionMapper permissionMapper;

    private final PermissionLevelMapper permissionLevelMapper;

    public PermissionResource(PermissionRepository permissionRepository, PermissionService permissionService, PermissionMapper permissionMapper, PermissionLevelMapper permissionLevelMapper) {
        this.permissionRepository = permissionRepository;
        this.permissionService = permissionService;
        this.permissionMapper = permissionMapper;
        this.permissionLevelMapper = permissionLevelMapper;
    }

    @ApiOperation("新增一个菜单")
    @PostMapping("/permissions")
    public Message create(@Valid @RequestBody PermissionVM vm) {
        if (vm.getId() != null) {
            throw new DataNotAlreadyIDException();
        }
        return RESTful.success(RestTypeEnum.POST, permissionService.create(vm));
    }

    @ApiOperation("查询所有的菜单")
    @GetMapping("/permissions")
    public Message query() {
        return RESTful.success(RestTypeEnum.GET, permissionMapper.adapt(permissionRepository.findAll()));
    }

    @ApiOperation("查询一个菜单")
    @GetMapping("/permissions/{id}")
    public Message query(@PathVariable Long id) {
        return RESTful.success(RestTypeEnum.GET, permissionMapper.adapt(permissionRepository.findById(id).orElse(null)));
    }

    @ApiOperation("分页查询菜单")
    @GetMapping("/permissions/pageable")
    public Message query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return PaginationUtil.execute(permissionRepository.findAll(pageable).map(permissionMapper::adapt));
    }

    @ApiOperation("查询所有的菜单（树形数据）")
    @GetMapping("/permissions/tree")
    public Message queryToTree(String name) {
        Specification<Permission> specification = Specifications.<Permission>and()
                .like(StringUtils.isNotBlank(name), "name", "%" + StringUtils.trim(name) + "%")
                .build();
        return RESTful.success(RestTypeEnum.GET, permissionLevelMapper.adapt(permissionRepository.findAll(specification)));
    }

    @ApiOperation("修改一个菜单")
    @PutMapping("/permissions")
    public Message update(@Valid @RequestBody PermissionVM vm) {
        return RESTful.success(RestTypeEnum.PUT, permissionService.update(vm));
    }

    @ApiOperation("删除一个菜单")
    @DeleteMapping("/permissions/{id}")
    public Message delete(@PathVariable Long id) {
        return RESTful.success(RestTypeEnum.DELETE, permissionService.delete(id));
    }
}
