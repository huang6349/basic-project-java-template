package org.hyl.auditing;

public class AbstractIdAuditingVM extends AbstractAuditingVM {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
