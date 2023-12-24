package com.notesforme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NotesForMeApplication {

	public static void main(String[] args) {
		System.out.println(org.hibernate.Version.getVersionString());
		SpringApplication.run(NotesForMeApplication.class, args);
	}
}
