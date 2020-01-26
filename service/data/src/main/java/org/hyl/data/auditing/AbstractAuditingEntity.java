package org.hyl.data.auditing;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hyl.data.config.DataConstants;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = -3714209040689559502L;

    @CreatedBy
    @Column(name = "CREATED_BY", nullable = false, length = 50, updatable = false)
    @JsonIgnore
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE", updatable = false)
    @JsonIgnore
    private Instant createdDate = Instant.now();

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY", length = 50)
    @JsonIgnore
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    @JsonIgnore
    private Instant lastModifiedDate = Instant.now();

    @Column(name = "DATA_STATE", nullable = false, columnDefinition = "TINYINT DEFAULT 1")
    private Byte state = DataConstants.DATA_NORMAL_STATE;
}
