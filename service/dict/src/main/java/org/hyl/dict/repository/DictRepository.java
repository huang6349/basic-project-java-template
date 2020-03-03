package org.hyl.dict.repository;

import org.hyl.dict.domain.Dict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DictRepository extends JpaRepository<Dict, Long>, JpaSpecificationExecutor<Dict> {

    Optional<Dict> findByCodeIgnoreCase(String code);

    Optional<Dict> findByCodeIgnoreCaseAndIdNot(String code, Long id);

    List<Dict> findByLevelIgnoreCaseStartingWith(String level);
}
