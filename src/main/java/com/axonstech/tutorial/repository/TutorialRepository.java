package com.axonstech.tutorial.repository;

import com.axonstech.tutorial.entity.Tutorial;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long> , JpaSpecificationExecutor<Tutorial> {
    List<Tutorial> findByTitleContains(String title, Pageable pageable);

    List<Tutorial> findByDescriptionContains(String description, Pageable pageable);

    List<Tutorial> findByTitleContainsAndDescriptionContains(String title, String description, Pageable pageable);


}