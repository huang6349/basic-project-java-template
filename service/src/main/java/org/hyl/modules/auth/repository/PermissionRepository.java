package org.hyl.modules.auth.repository;

import org.hyl.modules.auth.domain.Authority;
import org.hyl.modules.auth.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {

    Optional<Permission> findByNameIgnoreCase(String name);

    Optional<Permission> findByNameIgnoreCaseAndIdNot(String name, Long id);

    List<Permission> findByLevelIgnoreCaseStartingWith(String level);

    List<Permission> findByAuthoritiesInOrderBySeqDesc(Collection<Authority> authorities);
}
