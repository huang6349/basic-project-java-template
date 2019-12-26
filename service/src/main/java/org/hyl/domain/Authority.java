package org.hyl.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import org.hibernate.annotations.Where;
import org.hyl.auditing.AbstractIdAuditingEntity;

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

    @Column(name = "SEQ", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer seq = 0;

    private String desc;

    @JsonIgnore
    @ManyToMany(mappedBy = "authorities")
    private Set<MyUser> users = Sets.newHashSet();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
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
                ", seq=" + seq +
                ", desc='" + desc + '\'' +
                ", users=" + users +
                ", permissions=" + permissions +
                '}';
    }
}
