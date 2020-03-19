package org.hyl.modules.data.auditing;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public class AbstractLevelAuditingEntity extends AbstractIdAuditingEntity {

    private static final long serialVersionUID = -6410062413913316283L;

    @Column(name = "P_ID", nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long pid = 0L;

    @Column(name = "LEVEL", nullable = false)
    private String level;
}
