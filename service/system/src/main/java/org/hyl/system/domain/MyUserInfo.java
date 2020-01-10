package org.hyl.system.domain;

import org.hibernate.annotations.Where;
import org.hyl.data.auditing.AbstractIdAuditingEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TB_USER_INFO")
@Where(clause = "DATA_STATE <> 0")
public class MyUserInfo extends AbstractIdAuditingEntity {

    private static final long serialVersionUID = -995875514792405433L;

    @Column(length = 50, nullable = false)
    private String nickname;

    private String realname;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SEX_ID", foreignKey = @ForeignKey(name = "SEX_ID_FK"))
    private Dict sex;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    private String idCard;

    private String email;

    private String mobilePhone;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Dict getSex() {
        return sex;
    }

    public void setSex(Dict sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Override
    public String toString() {
        return "MyUserInfo{" +
                "nickname='" + nickname + '\'' +
                ", realname='" + realname + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", idCard='" + idCard + '\'' +
                ", email='" + email + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                '}';
    }
}
