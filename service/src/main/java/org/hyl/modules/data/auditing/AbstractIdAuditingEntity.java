package org.hyl.modules.data.auditing;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@Getter
@Setter
public class AbstractIdAuditingEntity extends AbstractAuditingEntity {

    private static final long serialVersionUID = -7518028686982558521L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @NotNull(groups = Update.class)
    private Long id;

    public @interface Update {
    }
}
