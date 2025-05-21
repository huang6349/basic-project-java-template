package org.myframework.es.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class TimeSeriesData implements Serializable {

    @Schema(description = "属性名称")
    private String name;

    @Schema(description = "属性数据")
    private Object data;
}
