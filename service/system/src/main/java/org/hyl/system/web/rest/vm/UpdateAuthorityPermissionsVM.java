package org.hyl.system.web.rest.vm;

import com.google.common.collect.Sets;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

@ApiModel("修改角色菜单视图模型")
public class UpdateAuthorityPermissionsVM {

    @ApiModelProperty("数据编号")
    private Long id;

    @ApiModelProperty("菜单编号列表")
    private Set<Long> permissions = Sets.newHashSet();

    public UpdateAuthorityPermissionsVM() {
        // Empty constructor needed for Jackson.
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Long> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Long> permissions) {
        this.permissions = permissions;
    }
}
