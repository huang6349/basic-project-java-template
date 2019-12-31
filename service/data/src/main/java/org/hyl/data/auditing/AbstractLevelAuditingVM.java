package org.hyl.data.auditing;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

import java.util.List;

public class AbstractLevelAuditingVM<E> extends AbstractIdAuditingVM {

    private Long pid = 0L;

    @JsonIgnore
    private String level;

    private List<E> child = Lists.newArrayList();

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

    public List<E> getChild() {
        return child;
    }

    public void setChild(List<E> child) {
        this.child = child;
    }
}
