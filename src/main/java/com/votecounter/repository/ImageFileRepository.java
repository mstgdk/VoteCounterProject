package com.votecounter.repository;

import com.votecounter.domain.ImageFile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ImageFileRepository extends JpaRepository<ImageFile,String> {
    @EntityGraph(attributePaths = "id")
    Optional<ImageFile> findImageById(String id);

    @Query(value = "select * from t_imagefile where t_imagefile.party_id=:id", nativeQuery = true)
    ImageFile findImageByPartyId(@Param("id") Long id);
}
