package org.hyl.modules.auth.service.mapper;

import org.hyl.modules.auth.domain.Permission;
import org.hyl.modules.auth.domain.vm.PermissionVM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PermissionMapper {

    public List<PermissionVM> adapt(List<Permission> permissions) {
        return permissions.stream().filter(Objects::nonNull).map(this::adapt).collect(Collectors.toList());
    }

    public PermissionVM adapt(Permission permission) {
        return PermissionVM.adapt(permission);
    }
}
