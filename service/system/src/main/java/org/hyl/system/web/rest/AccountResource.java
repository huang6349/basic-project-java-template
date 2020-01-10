package org.hyl.system.web.rest;

import org.hyl.system.commons.result.Message;
import org.hyl.system.commons.result.RESTful;
import org.hyl.system.errors.BadRequestException;
import org.hyl.system.repository.UserRepository;
import org.hyl.system.service.PermissionsService;
import org.hyl.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountResource {

    private final UserService userService;

    private final PermissionsService permissionsService;

    @Autowired
    public AccountResource(UserService userService, PermissionsService permissionsService) {
        this.userService = userService;
        this.permissionsService = permissionsService;
    }

    @GetMapping("/account")
    public Message account() {
        return userService.getUserWithAuthorities()
                .map(RESTful::success)
                .orElseThrow(() -> new BadRequestException("获取当前用户信息失败，请稍后再试", HttpStatus.NOT_FOUND));
    }

    @GetMapping("/authorities")
    public Message authorities() {
        return permissionsService.getUserPermissions()
                .map(RESTful::success)
                .orElseThrow(() -> new BadRequestException("获取当前用户权限失败，请稍后再试", HttpStatus.NOT_FOUND));
    }

    @GetMapping("/authorities/tree")
    public Message authoritiesToTree() {
        return permissionsService.getUserPermissionsToTree()
                .map(RESTful::success)
                .orElseThrow(() -> new BadRequestException("获取当前用户权限失败，请稍后再试", HttpStatus.NOT_FOUND));
    }
}
