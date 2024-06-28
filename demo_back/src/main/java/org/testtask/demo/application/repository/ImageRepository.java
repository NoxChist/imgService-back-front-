package org.testtask.demo.application.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.testtask.demo.application.entity.ImageEntity;
import org.testtask.demo.application.entity.ImageId;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, ImageId> {
    Page<ImageEntity> findAllByIdAuthor(String author, Pageable pageable);
}
