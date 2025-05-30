package org.myframework.core.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.myframework.extra.dict.Dict;
import org.myframework.extra.dict.EnumDict;

import static org.myframework.extra.dict.IsDefault.NO;
import static org.myframework.extra.dict.IsDefault.YES;
import static org.myframework.extra.dict.Style.ERROR;
import static org.myframework.extra.dict.Style.PRIMARY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Dict(name = "是否删除")
public enum IsDeleted implements EnumDict<String> {
    TYPE0("0", "未删", 0, YES.getValue(), PRIMARY.getValue()),
    TYPE1("1", "已删", 0, NO.getValue(), ERROR.getValue());

    @EnumValue
    @JsonValue
    private String value;

    private String label;

    private Integer sort;

    private Integer isDefault;

    private Integer style;
}
