package org.myframework.es.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.dromara.easyes.annotation.IndexId;
import org.dromara.easyes.annotation.rely.IdType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class SuperEntity<E extends SuperEntity<E>> extends Model<E> {

    @IndexId(type = IdType.NONE)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "数据主键")
    protected String id;
}
