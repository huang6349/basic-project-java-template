package org.hyl.auth.repository;

import org.hyl.auth.domain.Authority;
import org.hyl.auth.domain.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, Long>, JpaSpecificationExecutor<Permissions> {

    Optional<Permissions> findByNameIgnoreCase(String name);

    Optional<Permissions> findByNameIgnoreCaseAndIdNot(String name, Long id);

    List<Permissions> findByLevelIgnoreCaseStartingWith(String level);

    List<Permissions> findByAuthoritiesInOrderBySeqDesc(Collection<Authority> authorities);
}
