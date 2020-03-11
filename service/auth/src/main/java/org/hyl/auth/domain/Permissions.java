package org.hyl.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;
import org.hyl.data.auditing.AbstractLevelAuditingEntity;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = {"authorities", "resources"})
@Data
@Entity
@Table(name = "TB_PERMISSIONS")
@Where(clause = "DATA_STATE <> 0")
public class Permissions extends AbstractLevelAuditingEntity {

    private static final long serialVersionUID = 6746359187625456971L;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    private String path;

    private String icon;

    @Column(name = "SEQ", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer seq = 0;

    @Column(name = "DATA_DESC")
    private String desc;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissions")
    @Where(clause = "DATA_STATE <> 0")
    private Set<Authority> authorities = Sets.newHashSet();

    @JsonIgnore
    @OneToMany(mappedBy = "permissions")
    @Where(clause = "DATA_STATE <> 0")
    private Set<Resource> resources = Sets.newHashSet();
}
