package org.hyl.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;
import org.hyl.data.auditing.AbstractIdAuditingEntity;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = {"users", "permissions"})
@Data
@Entity
@Table(name = "TB_AUTHORITY")
@Where(clause = "DATA_STATE <> 0")
public class Authority extends AbstractIdAuditingEntity {

    private static final long serialVersionUID = 7584372570299432038L;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "CODE", length = 50, nullable = false)
    private String code;

    @Column(name = "DATA_DESC")
    private String desc;

    @JsonIgnore
    @ManyToMany(mappedBy = "authorities")
    @Where(clause = "DATA_STATE <> 0")
    private Set<MyUser> users = Sets.newHashSet();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "TB_AUTHORITY_PERMISSIONS",
            joinColumns = @JoinColumn(name = "AUTHORITY_ID"),
            inverseJoinColumns = @JoinColumn(name = "PERMISSIONS_ID"))
    @Where(clause = "DATA_STATE <> 0")
    private Set<Permissions> permissions = Sets.newHashSet();
}
