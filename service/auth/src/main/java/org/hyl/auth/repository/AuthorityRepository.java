package org.hyl.auth.repository;

import org.hyl.auth.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long>, JpaSpecificationExecutor<Authority> {

    Optional<Authority> findByNameIgnoreCase(String name);

    Optional<Authority> findByCodeIgnoreCase(String code);

    Optional<Authority> findByNameIgnoreCaseAndIdNot(String name, Long id);

    Optional<Authority> findByCodeIgnoreCaseAndIdNot(String name, Long id);
}
