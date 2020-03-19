package org.hyl.modules.auth.service.mapper;

import org.hyl.modules.auth.domain.Permission;
import org.hyl.modules.auth.domain.vm.PermissionLevelVM;
import org.hyl.modules.data.auditing.utils.LevelUtil;
import org.hyl.modules.data.auditing.utils.impl.DefaultLevelUtil;
import org.hyl.modules.dict.domain.Dict;
import org.hyl.modules.dict.domain.vm.DictLevelVM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PermissionLevelMapper {

    private LevelUtil<PermissionLevelVM> levelUtil = new DefaultLevelUtil<>();

    public List<PermissionLevelVM> adapt(List<Permission> permissions) {
        return levelUtil.listToTree(permissions.stream().filter(Objects::nonNull).map(PermissionLevelVM::adapt).collect(Collectors.toList()));
    }
}
