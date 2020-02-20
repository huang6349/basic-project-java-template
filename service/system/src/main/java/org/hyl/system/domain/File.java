package org.hyl.system.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;
import org.hyl.data.auditing.AbstractIdAuditingEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "TB_FILE")
@Where(clause = "DATA_STATE <> 0")
public class File extends AbstractIdAuditingEntity {

    private static final long serialVersionUID = -6875046604787541427L;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "URL", nullable = false)
    private String url;

    private String size;

    private Long num = 0L;
}
