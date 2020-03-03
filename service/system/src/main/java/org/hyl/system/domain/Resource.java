package org.hyl.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;
import org.hyl.data.auditing.AbstractIdAuditingEntity;
import org.hyl.dict.domain.Dict;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true, exclude = {"method", "permissions"})
@Data
@Entity
@Table(name = "TB_RESOURCE")
@Where(clause = "DATA_STATE <> 0")
public class Resource extends AbstractIdAuditingEntity {

    private static final long serialVersionUID = 7896445883698741826L;

    @Column(name = "PATTERN", nullable = false)
    private String pattern;

    @Column(name = "DATA_DESC")
    private String desc;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "METHOD_ID", foreignKey = @ForeignKey(name = "FK_RESOURCE_METHOD_ID"))
    @Where(clause = "DATA_STATE <> 0")
    private Dict method;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PERMISSIONS_ID", foreignKey = @ForeignKey(name = "FK_RESOURCE_PERMISSIONS_ID"))
    @Where(clause = "DATA_STATE <> 0")
    private Permissions permissions;
}
