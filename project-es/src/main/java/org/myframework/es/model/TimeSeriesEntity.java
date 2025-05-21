package org.myframework.es.model;

import cn.hutool.core.lang.Opt;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.dromara.easyes.annotation.IndexField;
import org.dromara.easyes.annotation.rely.FieldType;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class TimeSeriesEntity<E extends TimeSeriesEntity<E>> extends Entity<E> {

    @IndexField(fieldType = FieldType.NESTED, nestedClass = TimeSeriesData.class)
    @JsonIgnore
    @Schema(description = "时序数据")
    protected List<TimeSeriesData> data;

    @Schema(description = "时序数据")
    public Map<String, Object> values() {
        return Opt.ofNullable(data)
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.toMap(
                        TimeSeriesData::getName,
                        TimeSeriesData::getData,
                        (oldValue, newValue) -> newValue
                ));
    }
}
