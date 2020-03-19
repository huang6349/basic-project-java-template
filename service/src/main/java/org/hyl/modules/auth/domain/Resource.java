package org.hyl.modules.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.hyl.modules.data.auditing.AbstractIdAuditingEntity;
import org.hyl.modules.dict.domain.Dict;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "TB_RESOURCE")
@Where(clause = "DATA_STATE <> 0")
public class Resource extends AbstractIdAuditingEntity {

    private static final long serialVersionUID = 7896445883698741826L;

    @Column(name = "RESOURCE_PATTERN", nullable = false)
    private String pattern;

    @Column(name = "RESOURCE_DESC")
    private String desc;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "METHOD_ID")
    @Where(clause = "DATA_STATE <> 0")
    private Dict method;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PERMISSION_ID")
    @Where(clause = "DATA_STATE <> 0")
    private Permission permission;
}
