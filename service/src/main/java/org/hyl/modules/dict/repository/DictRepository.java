package org.hyl.modules.dict.repository;

import org.hyl.modules.dict.domain.Dict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface DictRepository extends JpaRepository<Dict, Long>, JpaSpecificationExecutor<Dict> {

    Optional<Dict> findByCodeIgnoreCase(String code);

    Optional<Dict> findByCodeIgnoreCaseAndIdNot(String code, Long id);

    List<Dict> findByLevelIgnoreCaseStartingWith(String level);
}
