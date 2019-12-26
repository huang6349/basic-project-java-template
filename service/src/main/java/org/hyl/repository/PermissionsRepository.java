package org.hyl.repository;

import org.hyl.domain.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, Long>, JpaSpecificationExecutor {

    Optional<Permissions> findByNameIgnoreCase(String name);

    Optional<Permissions> findByNameIgnoreCaseAndIdNot(String name, Long id);

    List<Permissions> findByLevelIgnoreCaseStartingWith(String level);
}
