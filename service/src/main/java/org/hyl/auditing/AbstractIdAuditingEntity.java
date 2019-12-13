package org.hyl.auditing;

import javax.persistence.*;

@MappedSuperclass
public class AbstractIdAuditingEntity extends AbstractAuditingEntity {

    private static final long serialVersionUID = -7518028686982558521L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
