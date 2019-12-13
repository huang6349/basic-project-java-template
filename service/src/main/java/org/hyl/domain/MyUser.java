package org.hyl.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hyl.config.Constants;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TB_USER")
public class MyUser implements Serializable {

    private static final long serialVersionUID = -5873184873496716826L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(length = 50, nullable = false)
    private String username;

    @JsonIgnore
    @Column(length = 60, nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private Byte state = Constants.DATA_NORMAL_STATE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
