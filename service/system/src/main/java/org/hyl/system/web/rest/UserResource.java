package org.hyl.system.web.rest;

import com.github.wenhao.jpa.Specifications;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.hyl.commons.result.Message;
import org.hyl.commons.result.PaginationUtil;
import org.hyl.commons.result.RESTful;
import org.hyl.commons.result.enums.RestTypeEnum;
import org.hyl.system.domain.MyUser;
import org.hyl.commons.exception.DataNotAlreadyIDException;
import org.hyl.system.repository.UserRepository;
import org.hyl.system.service.UserService;
import org.hyl.system.web.rest.vm.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/api")
public class UserResource {

    private final UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @ApiOperation("新增一个用户")
    @PostMapping("/users")
    public Message create(@Valid @RequestBody UserVM vm) {
        if (vm.getId() != null) {
            throw new DataNotAlreadyIDException();
        }
        return RESTful.success(RestTypeEnum.POST, userService.create(vm));
    }

    @ApiOperation("查询所有的用户")
    @GetMapping("/users")
    public Message query() {
        return RESTful.success(RestTypeEnum.GET, userRepository.findAll().stream().map(UserVM::adapt));
    }

    @ApiOperation("查询一个用户")
    @GetMapping("/users/{id}")
    public Message query(@PathVariable Long id) {
        Optional<MyUser> optional = userRepository.findById(id);
        return RESTful.success(RestTypeEnum.GET, optional.map(UserVM::adapt).orElse(null));
    }

    @ApiOperation("分页查询用户")
    @GetMapping("/users/pageable")
    public Message query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, String username) {
        Specification<MyUser> specification = Specifications.<MyUser>and()
                .like(StringUtils.isNotBlank(username), "username", "%" + StringUtils.trim(username) + "%")
                .build();
        return PaginationUtil.execute(userRepository.findAll(specification, pageable).map(UserVM::adapt));
    }

    @ApiOperation("修改一个用户")
    @PutMapping("/users")
    public Message update(@Valid @RequestBody UserVM vm) {
        return RESTful.success(RestTypeEnum.PUT, userService.update(vm));
    }

    @ApiOperation("删除一个用户")
    @DeleteMapping("/users/{id}")
    public Message delete(@PathVariable Long id) {
        return RESTful.success(RestTypeEnum.DELETE, userService.delete(id));
    }

    @ApiOperation("启用一个用户")
    @PutMapping("/users/enable/{id}")
    public Message enable(@PathVariable Long id) {
        return RESTful.success(RestTypeEnum.PUT, userService.enable(id));
    }

    @ApiOperation("禁用一个用户")
    @PutMapping("/users/disable/{id}")
    public Message disable(@PathVariable Long id) {
        return RESTful.success(RestTypeEnum.PUT, userService.disable(id));
    }

    @ApiOperation("重置一个用户的密码")
    @PutMapping("/users/password/reset/{id}")
    public Message resetPassword(@PathVariable Long id) {
        return RESTful.success(RestTypeEnum.PUT, userService.resetPassword(id));
    }
}
