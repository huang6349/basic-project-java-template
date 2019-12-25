package org.hyl.repository;

import org.hyl.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, String>, JpaSpecificationExecutor {

    Optional<Authority> findByIdIgnoreCase(String id);

    Optional<Authority> findByNameIgnoreCase(String name);

    Optional<Authority> findByNameIgnoreCaseAndIdNot(String name, String id);
}
