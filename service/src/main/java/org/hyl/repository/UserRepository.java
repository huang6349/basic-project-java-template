package org.hyl.repository;

import org.hyl.domain.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long>, JpaSpecificationExecutor {

    Optional<MyUser> findByUsername(String username);
}
