package org.hyl.system.web.rest.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hyl.data.auditing.AbstractLevelAuditingVM;
import org.hyl.system.config.Constants;
import org.hyl.system.domain.Dict;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("字典视图模型（树形数据）")
public class DictLevelVM extends AbstractLevelAuditingVM<DictLevelVM> {

    @ApiModelProperty("字典名称")
    private String name;

    @ApiModelProperty("字典唯一标识码")
    private String code;

    @ApiModelProperty("字典数据")
    private String data;

    @ApiModelProperty("字典描述")
    private String desc;

    @ApiModelProperty("字典信息最后修改人")
    private String lastModifiedBy;

    @ApiModelProperty("字典信息最后修改时间")
    private String lastModifiedDate_text;

    public static DictLevelVM adapt(Dict dict) {
        DictLevelVM vm = new DictLevelVM();
        BeanUtils.copyProperties(dict, vm);
        if (dict.getLastModifiedDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(dict.getLastModifiedDate(), ZoneId.systemDefault());
            vm.setLastModifiedDate_text(localDateTime.format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMATTER)));
        }
        return vm;
    }
}
