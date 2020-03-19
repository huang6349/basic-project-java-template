package org.hyl.modules.file.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.hyl.modules.data.auditing.AbstractIdAuditingEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "TB_FILE")
@Where(clause = "DATA_STATE <> 0")
public class File extends AbstractIdAuditingEntity {

    private static final long serialVersionUID = -6875046604787541427L;

    @Column(name = "FILE_NAME", nullable = false)
    private String name;

    @Column(name = "FILE_URL", nullable = false)
    private String url;

    @Column(name = "FILE_SIZE")
    private String size;

    @Column(name = "FILE_DOWNLOADS")
    private Long num = 0L;
}
