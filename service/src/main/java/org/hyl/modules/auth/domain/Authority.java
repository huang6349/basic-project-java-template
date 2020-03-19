package org.hyl.modules.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.hyl.modules.data.auditing.AbstractIdAuditingEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "TB_AUTHORITY")
@Where(clause = "DATA_STATE <> 0")
public class Authority extends AbstractIdAuditingEntity {

    private static final long serialVersionUID = 7584372570299432038L;

    @Column(name = "AUTHORITY_NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "AUTHORITY_CODE", length = 50, nullable = false)
    private String code;

    @Column(name = "AUTHORITY_DESC")
    private String desc;

    @JsonIgnore
    @ManyToMany(mappedBy = "authorities")
    @Where(clause = "DATA_STATE <> 0")
    private Set<MyUser> users = Sets.newHashSet();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "TB_AUTHORITY_PERMISSION",
            joinColumns = @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID", referencedColumnName = "ID"))
    @Where(clause = "DATA_STATE <> 0")
    private Set<Permission> permissions = Sets.newHashSet();
}
