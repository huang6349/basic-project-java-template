package org.hyl.data.auditing;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class AbstractIdAuditingEntity extends AbstractAuditingEntity {

    private static final long serialVersionUID = -7518028686982558521L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
}
