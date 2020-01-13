package org.hyl.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import org.hibernate.annotations.Where;
import org.hyl.data.auditing.AbstractIdAuditingEntity;

import javax.persistence.*;
import java.util.Set;

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
    private Set<MyUser> users = Sets.newHashSet();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "TB_AUTHORITY_PERMISSIONS",
            joinColumns = @JoinColumn(name = "AUTHORITY_ID"),
            inverseJoinColumns = @JoinColumn(name = "PERMISSIONS_ID"))
    @Where(clause = "DATA_STATE <> 0")
    private Set<Permissions> permissions = Sets.newHashSet();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Set<MyUser> getUsers() {
        return users;
    }

    public void setUsers(Set<MyUser> users) {
        this.users = users;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permissions> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", users=" + users +
                ", permissions=" + permissions +
                '}';
    }
}
