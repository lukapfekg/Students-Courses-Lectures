package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableCaching
public class StudentsCoursesLecturesApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentsCoursesLecturesApplication.class, args);
	}


}
