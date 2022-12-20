package com.axonstech.tutorial.controller;

import com.axonstech.tutorial.dao.TutorialDAO;
import com.axonstech.tutorial.entity.Tutorial;
import com.axonstech.tutorial.repository.TutorialRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tutorial")
public class TutorialController {
    @Autowired
    private TutorialRepository tutorialRepository;

    @Autowired
    private TutorialDAO tutorialDAO;

    @GetMapping
    public List<Tutorial> getTutorial() {
        return tutorialRepository.findAll();
    }

    @GetMapping("/q")
    public List<Tutorial> getTutorialByTitle(@RequestParam("title") String title,
                                             @RequestParam(value = "page", defaultValue = "1") int page,
                                             @RequestParam(value = "size", defaultValue = "10") int size) {
        return tutorialRepository.findByTitleContains(title, PageRequest.of(page - 1, size));
    }

    @PostMapping("/c")
    public List<Tutorial> getTutorialByCondition(@RequestBody Tutorial condition) {
        if (!"".equals(condition.getTitle()) && "".equals(condition.getDescription())) {
            return tutorialRepository.findByTitleContains(condition.getTitle(), PageRequest.of(0, 10));
        }
        if ("".equals(condition.getTitle()) && !"".equals(condition.getDescription())) {
            return tutorialRepository.findByDescriptionContains(condition.getTitle(), PageRequest.of(0, 10));
        }
        if ("".equals(condition.getTitle()) && !"".equals(condition.getDescription())) {
            return tutorialRepository.findByTitleContainsAndDescriptionContains(condition.getTitle(), condition.getDescription(), PageRequest.of(0, 10));
        }
        return tutorialRepository.findAll(PageRequest.of(0, 10)).getContent();
    }

    @PostMapping("/dao")
    public Page<Tutorial> getTutorialDAOByCondition(@RequestBody Tutorial condition) {
        return tutorialDAO.findTutorialByPage(PageRequest.of(0, 10));
    }

    @PostMapping("/spec")
    public Page<Tutorial> getTutorialBySpecification(@RequestBody Tutorial condition) {
        Specification<Tutorial> specification =
                (root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (condition.getTitle() != null) {
                        predicates.add(criteriaBuilder.like(root.get("title"), "%"+ condition.getTitle()+"%"));
                    }
                    if (condition.getDescription() != null) {
                        predicates.add(criteriaBuilder.like(root.get("description"),"%"+ condition.getDescription()+"%"));
                    }
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                };

        return tutorialRepository.findAll( specification, PageRequest.of(0, 10));
    }

    @GetMapping("/{id}")
    public Tutorial getTutorialById(@PathVariable Long id) {
        return tutorialRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public Tutorial saveTutorial(@RequestBody Tutorial tutorial) {
        return tutorialRepository.save(tutorial);
    }

    @PutMapping("/{id}")
    public Tutorial updateTutorial(@PathVariable Long id, @RequestBody Tutorial tutorial) {
        Optional<Tutorial> oTutorial = tutorialRepository.findById(id);
        if (oTutorial.isPresent()) {
            Tutorial entity = oTutorial.get();
            entity.setTitle(tutorial.getTitle());
            entity.setDescription(tutorial.getDescription());
            entity.setPublished(tutorial.getPublished());
            return tutorialRepository.save(entity);
        }

        throw new RuntimeException("Not found Exception");
    }

    @DeleteMapping("/{id}")
    public void deleteTutorial(@PathVariable Long id) {
        tutorialRepository.deleteById(id);
    }
}
