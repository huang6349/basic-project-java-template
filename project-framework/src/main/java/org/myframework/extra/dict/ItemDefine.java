package org.myframework.extra.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@Schema(name = "字典信息")
public class ItemDefine implements Serializable {

    @Schema(description = "字典名称")
    private String label;

    @Schema(description = "字典键值")
    private Object value;

    @Schema(description = "是否缺省")
    private Boolean isDefault;
}
