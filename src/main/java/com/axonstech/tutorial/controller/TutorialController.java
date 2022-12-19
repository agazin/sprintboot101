package com.axonstech.tutorial.controller;

import com.axonstech.tutorial.entity.Tutorial;
import com.axonstech.tutorial.repository.TutorialRepository;
import com.axonstech.tutorial.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tutorial")
public class TutorialController {
    @Autowired
    private TutorialRepository tutorialRepository;

    @GetMapping
    public List<Tutorial> getTutorial() {
        return tutorialRepository.findAll();
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
    public void deleteTutorail(@PathVariable Long id){
        tutorialRepository.deleteById(id);
    }
}
