package com.axonstech.tutorial;

import com.axonstech.tutorial.entity.Tutorial;
import com.axonstech.tutorial.repository.TutorialRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class TutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutorialApplication.class, args);
	}

	@Bean
	CommandLineRunner init(TutorialRepository tutorialRepository) {
		return args -> {
			Tutorial tutorial = new Tutorial();
			tutorial.setTitle("test");
			tutorial.setDescription("test description");
			tutorial.setPublished(false);
			tutorialRepository.save(tutorial);

			List<Tutorial> tuts = IntStream.rangeClosed(1, 100).boxed().map(i -> {
				Tutorial t = new Tutorial();
				t.setTitle("test " + i);
				t.setDescription("test " + i + " description");
				t.setPublished(false);
				return t;
			}).collect(Collectors.toList());
			tutorialRepository.saveAll(tuts);
		};
	}
}
