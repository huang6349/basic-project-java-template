package org.hyl.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import org.hibernate.annotations.Where;
import org.hyl.auditing.AbstractLevelAuditingEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "TB_PERMISSIONS")
@Where(clause = "DATA_STATE <> 0")
public class Permissions extends AbstractLevelAuditingEntity {

    private static final long serialVersionUID = 6746359187625456971L;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    private String path;

    @Column(name = "SEQ", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer seq = 0;

    private String desc;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissions")
    private Set<Authority> authorities = Sets.newHashSet();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "Permissions{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", seq=" + seq +
                ", desc='" + desc + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
