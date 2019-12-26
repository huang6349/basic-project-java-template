package org.hyl.auditing;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractLevelAuditingEntity extends AbstractIdAuditingEntity {

    private static final long serialVersionUID = -6410062413913316283L;

    @Column(name = "P_ID", nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long pid = 0L;

    @Column(name = "LEVEL", nullable = false)
    private String level;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
