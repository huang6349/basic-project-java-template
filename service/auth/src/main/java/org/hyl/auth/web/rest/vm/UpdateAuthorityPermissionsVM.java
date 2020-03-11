package org.hyl.auth.web.rest.vm;

import com.google.common.collect.Sets;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("修改角色菜单视图模型")
public class UpdateAuthorityPermissionsVM {

    @ApiModelProperty("数据编号")
    private Long id;

    @ApiModelProperty("菜单编号列表")
    private Set<Long> permissions = Sets.newHashSet();
}
