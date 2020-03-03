package org.hyl.dict.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;
import org.hyl.data.auditing.AbstractLevelAuditingEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "TB_DICT")
@Where(clause = "DATA_STATE <> 0")
public class Dict extends AbstractLevelAuditingEntity {

    private static final long serialVersionUID = -1204440807210016841L;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "CODE", length = 50)
    private String code;

    @Column(name = "DATA", length = 50)
    private String data;

    @Column(name = "DATA_DESC")
    private String desc;
}
