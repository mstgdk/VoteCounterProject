package com.votecounter.repository;

import com.votecounter.domain.ImageFile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageFileRepository extends JpaRepository<ImageFile,String> {
    @EntityGraph(attributePaths = "id")
    Optional<ImageFile> findImageById(String id);

}
