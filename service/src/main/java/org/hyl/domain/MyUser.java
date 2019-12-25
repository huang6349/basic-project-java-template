package org.hyl.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import org.hibernate.annotations.Where;
import org.hyl.auditing.AbstractIdAuditingEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "TB_USER")
@Where(clause = "DATA_STATE <> 0")
public class MyUser extends AbstractIdAuditingEntity {

    private static final long serialVersionUID = -5873184873496716826L;

    @Column(length = 50, nullable = false)
    private String username;

    @JsonIgnore
    @Column(length = 60, nullable = false)
    private String password;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "TB_USER_AUTHORITY",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID"))
    private Set<Authority> authorities = Sets.newHashSet();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
