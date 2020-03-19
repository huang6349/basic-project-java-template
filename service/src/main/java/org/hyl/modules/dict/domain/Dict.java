package org.hyl.modules.dict.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.hyl.modules.data.auditing.AbstractLevelAuditingEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "TB_DICT")
@Where(clause = "DATA_STATE <> 0")
public class Dict extends AbstractLevelAuditingEntity {

    private static final long serialVersionUID = -1204440807210016841L;

    @Column(name = "DICT_NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "DICT_CODE", length = 50)
    private String code;

    @Column(name = "DICT_DATA", length = 50)
    private String data;

    @Column(name = "DICT_DESC")
    private String desc;
}
