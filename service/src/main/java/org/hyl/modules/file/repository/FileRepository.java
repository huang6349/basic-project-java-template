package org.hyl.modules.file.repository;

import org.hyl.modules.file.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FileRepository extends JpaRepository<File, Long>, JpaSpecificationExecutor<File> {
}
