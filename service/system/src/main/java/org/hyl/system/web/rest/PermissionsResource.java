package org.hyl.system.web.rest;

import com.github.wenhao.jpa.Specifications;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.hyl.system.commons.result.enums.RestTypeEnum;
import org.hyl.system.web.rest.vm.PermissionsVM;
import org.hyl.data.auditing.DefaultLevelUtil;
import org.hyl.data.auditing.LevelUtil;
import org.hyl.system.commons.result.Message;
import org.hyl.system.commons.result.PaginationUtil;
import org.hyl.system.commons.result.RESTful;
import org.hyl.system.domain.Permissions;
import org.hyl.system.errors.DataNotAlreadyIDException;
import org.hyl.system.repository.PermissionsRepository;
import org.hyl.system.service.PermissionsService;
import org.hyl.system.web.rest.vm.PermissionsLevelVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(tags = "菜单管理", position = 997)
@RestController
@RequestMapping("/api")
public class PermissionsResource {

    private LevelUtil<PermissionsLevelVM> levelUtil = new DefaultLevelUtil<>();

    private final PermissionsRepository permissionsRepository;

    private final PermissionsService permissionsService;

    @Autowired
    public PermissionsResource(PermissionsRepository permissionsRepository, PermissionsService permissionsService) {
        this.permissionsRepository = permissionsRepository;
        this.permissionsService = permissionsService;
    }

    @ApiOperation("新增一个菜单")
    @PostMapping("/permissions")
    public Message create(@Valid @RequestBody PermissionsVM vm) {
        if (vm.getId() != null) {
            throw new DataNotAlreadyIDException();
        }
        return RESTful.success(RestTypeEnum.POST, permissionsService.create(vm));
    }

    @ApiOperation("查询所有的菜单")
    @GetMapping("/permissions")
    public Message query() {
        return RESTful.success(RestTypeEnum.GET, permissionsRepository.findAll().stream().map(PermissionsVM::adapt).collect(Collectors.toList()));
    }

    @ApiOperation("查询一个菜单")
    @GetMapping("/permissions/{id}")
    public Message query(@PathVariable Long id) {
        Optional<Permissions> optional = permissionsRepository.findById(id);
        return RESTful.success(RestTypeEnum.GET, optional.map(PermissionsVM::adapt).orElse(null));
    }

    @ApiOperation("分页查询菜单")
    @GetMapping("/permissions/pageable")
    public Message query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return PaginationUtil.execute(permissionsRepository.findAll(pageable).map(PermissionsVM::adapt));
    }

    @ApiOperation("查询所有的菜单（树形数据）")
    @GetMapping("/permissions/tree")
    public Message queryToTree(String name) {
        Specification<Permissions> specification = Specifications.<Permissions>and()
                .like(StringUtils.isNotBlank(name), "name", "%" + StringUtils.trim(name) + "%")
                .build();
        return RESTful.success(RestTypeEnum.GET, levelUtil.listToTree(permissionsRepository.findAll(specification).stream().map(PermissionsLevelVM::adapt).collect(Collectors.toList())));
    }

    @ApiOperation("修改一个菜单")
    @PutMapping("/permissions")
    public Message update(@Valid @RequestBody PermissionsVM vm) {
        return RESTful.success(RestTypeEnum.PUT, permissionsService.update(vm));
    }

    @ApiOperation("删除一个菜单")
    @DeleteMapping("/permissions/{id}")
    public Message delete(@PathVariable Long id) {
        return RESTful.success(RestTypeEnum.DELETE, permissionsService.delete(id));
    }
}
