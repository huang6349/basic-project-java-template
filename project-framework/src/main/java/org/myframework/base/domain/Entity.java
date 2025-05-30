package org.myframework.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.myframework.core.enums.IsDeleted;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Entity<E extends Entity<E, T>, T> extends SuperEntity<E, T> {

    @JsonIgnore
    @Schema(description = "是否删除")
    protected IsDeleted isDeleted;
}
