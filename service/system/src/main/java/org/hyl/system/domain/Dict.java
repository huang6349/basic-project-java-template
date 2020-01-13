package org.hyl.system.domain;

import org.hibernate.annotations.Where;
import org.hyl.data.auditing.AbstractLevelAuditingEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TB_DICT")
@Where(clause = "DATA_STATE <> 0")
public class Dict extends AbstractLevelAuditingEntity {

    private static final long serialVersionUID = -1204440807210016841L;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "CODE", length = 50)
    private String code;

    @Column(name = "DATA", length = 50)
    private String data;

    @Column(name = "DATA_DESC")
    private String desc;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Dict{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", data='" + data + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
