package org.hyl.system.web.rest;

import com.github.wenhao.jpa.Specifications;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.hyl.commons.result.Message;
import org.hyl.commons.result.PaginationUtil;
import org.hyl.commons.result.RESTful;
import org.hyl.commons.result.enums.RestTypeEnum;
import org.hyl.system.domain.Resource;
import org.hyl.system.errors.DataNotAlreadyIDException;
import org.hyl.system.repository.ResourceRepository;
import org.hyl.system.service.ResourceService;
import org.hyl.system.web.rest.vm.ResourceVM;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(tags = "菜单资源管理")
@RestController
@RequestMapping("/api")
public class ResourceResource {

    private final ResourceRepository resourceRepository;

    private final ResourceService resourceService;

    public ResourceResource(ResourceRepository resourceRepository, ResourceService resourceService) {
        this.resourceRepository = resourceRepository;
        this.resourceService = resourceService;
    }

    @ApiOperation("新增一个菜单资源")
    @PostMapping("/resource")
    public Message create(@Valid @RequestBody ResourceVM vm) {
        if (vm.getId() != null) {
            throw new DataNotAlreadyIDException();
        }
        return RESTful.success(RestTypeEnum.POST, resourceService.create(vm));
    }

    @ApiOperation("查询所有的菜单资源")
    @GetMapping("/resource")
    public Message query() {
        return RESTful.success(RestTypeEnum.GET, resourceRepository.findAll().stream().map(ResourceVM::adapt).collect(Collectors.toList()));
    }

    @ApiOperation("查询一个菜单资源")
    @GetMapping("/resource/{id}")
    public Message query(@PathVariable Long id) {
        Optional<Resource> optional = resourceRepository.findById(id);
        return RESTful.success(RestTypeEnum.GET, optional.map(ResourceVM::adapt).orElse(null));
    }

    @ApiOperation("分页查询菜单资源")
    @GetMapping("/resource/pageable")
    public Message query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Long permissionsId, String pattern) {
        Specification<Resource> specification = Specifications.<Resource>and()
                .eq(permissionsId != null, "permissions.id", permissionsId)
                .like(StringUtils.isNotBlank(pattern), "pattern", "%" + StringUtils.trim(pattern) + "%")
                .build();
        return PaginationUtil.execute(resourceRepository.findAll(specification, pageable).map(ResourceVM::adapt));
    }

    @ApiOperation("修改一个菜单资源")
    @PutMapping("/resource")
    public Message update(@Valid @RequestBody ResourceVM vm) {
        return RESTful.success(RestTypeEnum.PUT, resourceService.update(vm));
    }

    @ApiOperation("删除一个菜单资源")
    @DeleteMapping("/resource/{id}")
    public Message delete(@PathVariable Long id) {
        return RESTful.success(RestTypeEnum.DELETE, resourceService.delete(id));
    }
}
