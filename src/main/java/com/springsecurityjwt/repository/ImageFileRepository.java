package com.springsecurityjwt.repository;


import com.springsecurityjwt.entity.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {

    @Query("SELECT count(i) FROM ImageFile i WHERE i.isDefault=true")
    Long countWith();

    @Query("SELECT i.isDefault FROM ImageFile i WHERE i.Id = ?1")
    boolean isDefault(Long id);


}
