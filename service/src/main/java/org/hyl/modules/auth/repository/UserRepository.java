package org.hyl.modules.auth.repository;

import org.hyl.modules.auth.domain.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser, Long>, JpaSpecificationExecutor<MyUser> {

    Optional<MyUser> findByUsernameIgnoreCase(String username);
}
