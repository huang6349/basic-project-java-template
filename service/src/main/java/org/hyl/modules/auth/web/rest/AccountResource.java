package org.hyl.modules.auth.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hyl.modules.auth.service.PermissionService;
import org.hyl.modules.auth.service.UserService;
import org.hyl.modules.auth.domain.vm.ChangePasswordVM;
import org.hyl.modules.commons.exception.BadRequestException;
import org.hyl.modules.commons.result.Message;
import org.hyl.modules.commons.result.RESTful;
import org.hyl.modules.commons.result.enums.RestTypeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final UserService userService;

    private final PermissionService permissionService;

    public AccountResource(UserService userService, PermissionService permissionService) {
        this.userService = userService;
        this.permissionService = permissionService;
    }

    @ApiOperation("获取当前用户信息")
    @GetMapping("/account")
    public Message account() {
        return userService.getUserWithAuthorities()
                .map(RESTful::success)
                .orElseThrow(() -> new BadRequestException("获取当前用户信息失败，请稍后再试", HttpStatus.NOT_FOUND));
    }

    @ApiOperation("获取当前用户权限")
    @GetMapping("/authorities")
    public Message authorities() {
        return permissionService.getUserPermissions()
                .map(RESTful::success)
                .orElseThrow(() -> new BadRequestException("获取当前用户权限失败，请稍后再试", HttpStatus.NOT_FOUND));
    }

    @ApiOperation("获取当前用户权限（树形数据）")
    @GetMapping("/authorities/tree")
    public Message authoritiesToTree() {
        return permissionService.getUserPermissionsToTree()
                .map(RESTful::success)
                .orElseThrow(() -> new BadRequestException("获取当前用户权限失败，请稍后再试", HttpStatus.NOT_FOUND));
    }

    @ApiOperation("更改当前用户密码")
    @PutMapping("/password/change")
    public Message changePassword(@Valid @RequestBody ChangePasswordVM vm) {
        return RESTful.success("更改当前用户密码成功", RestTypeEnum.PUT, userService.changePassword(vm));
    }
}
