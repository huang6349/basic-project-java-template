package org.hyl.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;
import org.hyl.data.auditing.AbstractIdAuditingEntity;
import org.hyl.dict.domain.Dict;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true, exclude = {"sex"})
@Data
@Entity
@Table(name = "TB_USER_INFO")
@Where(clause = "DATA_STATE <> 0")
public class MyUserInfo extends AbstractIdAuditingEntity {

    private static final long serialVersionUID = -995875514792405433L;

    @Column(length = 50, nullable = false)
    private String nickname;

    private String realname;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SEX_ID", foreignKey = @ForeignKey(name = "FK_USERINFO_SEX_ID"))
    @Where(clause = "DATA_STATE <> 0")
    private Dict sex;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    private String idCard;

    private String email;

    private String mobilePhone;
}
