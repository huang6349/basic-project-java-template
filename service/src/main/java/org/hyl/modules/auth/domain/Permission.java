package org.hyl.modules.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.hyl.modules.data.auditing.AbstractLevelAuditingEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "TB_PERMISSION")
@Where(clause = "DATA_STATE <> 0")
public class Permission extends AbstractLevelAuditingEntity {

    private static final long serialVersionUID = 6746359187625456971L;

    @Column(name = "PERMISSION_NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "PERMISSION_PATH")
    private String path;

    @Column(name = "PERMISSION_ICON")
    private String icon;

    @Column(name = "PERMISSION_SEQ", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer seq = 0;

    @Column(name = "PERMISSION_DESC")
    private String desc;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissions")
    @Where(clause = "DATA_STATE <> 0")
    private Set<Authority> authorities = Sets.newHashSet();

    @JsonIgnore
    @OneToMany(mappedBy = "permission")
    @Where(clause = "DATA_STATE <> 0")
    private Set<Resource> resources = Sets.newHashSet();
}
