package org.hyl.modules.auth.web.rest;

import com.github.wenhao.jpa.Specifications;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.hyl.modules.auth.domain.Resource;
import org.hyl.modules.auth.repository.ResourceRepository;
import org.hyl.modules.auth.service.ResourceService;
import org.hyl.modules.auth.domain.vm.ResourceVM;
import org.hyl.modules.auth.service.mapper.ResourceMapper;
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

@Api(tags = "菜单资源管理")
@RestController
@RequestMapping("/api")
public class ResourceResource {

    private final ResourceRepository resourceRepository;

    private final ResourceService resourceService;

    private final ResourceMapper resourceMapper;

    public ResourceResource(ResourceRepository resourceRepository, ResourceService resourceService, ResourceMapper resourceMapper) {
        this.resourceRepository = resourceRepository;
        this.resourceService = resourceService;
        this.resourceMapper = resourceMapper;
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
        return RESTful.success(RestTypeEnum.GET, resourceMapper.adapt(resourceRepository.findAll()));
    }

    @ApiOperation("查询一个菜单资源")
    @GetMapping("/resource/{id}")
    public Message query(@PathVariable Long id) {
        return RESTful.success(RestTypeEnum.GET, resourceMapper.adapt(resourceRepository.findById(id).orElse(null)));
    }

    @ApiOperation("分页查询菜单资源")
    @GetMapping("/resource/pageable")
    public Message query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Long permissionsId, String pattern) {
        Specification<Resource> specification = Specifications.<Resource>and()
                .eq(permissionsId != null, "permission.id", permissionsId)
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
