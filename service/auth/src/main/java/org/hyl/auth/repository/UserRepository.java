package org.hyl.auth.repository;

import org.hyl.auth.domain.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long>, JpaSpecificationExecutor<MyUser> {

    Optional<MyUser> findByUsernameIgnoreCase(String username);
}
