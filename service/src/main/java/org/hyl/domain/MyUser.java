package org.hyl.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hyl.auditing.AbstractIdAuditingEntity;
import org.hyl.config.Constants;

import javax.persistence.*;

@Entity
@Table(name = "TB_USER")
public class MyUser extends AbstractIdAuditingEntity {

    private static final long serialVersionUID = -5873184873496716826L;

    @Column(length = 50, nullable = false)
    private String username;

    @JsonIgnore
    @Column(length = 60, nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private Byte state = Constants.DATA_NORMAL_STATE;

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

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", state=" + state +
                '}';
    }
}
