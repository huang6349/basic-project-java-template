package org.hyl.system.web.rest;

import org.hyl.system.commons.result.Message;
import org.hyl.system.commons.result.RESTful;
import org.hyl.system.errors.InternalServerErrorException;
import org.hyl.system.service.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountResource {

    private final PermissionsService permissionsService;

    @Autowired
    public AccountResource(PermissionsService permissionsService) {
        this.permissionsService = permissionsService;
    }

    @GetMapping("/authorities/tree")
    public Message queryAuthorities() {
        return permissionsService.getUserPermissions()
                .map(RESTful::success)
                .orElseThrow(() -> new InternalServerErrorException("获取当前用户权限失败，请稍后再试", 404));
    }
}
