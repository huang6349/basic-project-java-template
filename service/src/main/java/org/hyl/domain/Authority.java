package org.hyl.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import org.hibernate.annotations.Where;
import org.hyl.auditing.AbstractAuditingEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "TB_AUTHORITY")
@Where(clause = "DATA_STATE <> 0")
public class Authority extends AbstractAuditingEntity {

    private static final long serialVersionUID = 7584372570299432038L;

    @Id
    @Column(name = "ID", length = 50)
    private String id;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "SEQ", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer seq = 0;

    private String desc;

    @JsonIgnore
    @ManyToMany(mappedBy = "authorities")
    private Set<MyUser> users = Sets.newHashSet();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Authority{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", seq=" + seq +
                ", desc='" + desc + '\'' +
                ", users=" + users +
                '}';
    }
}
