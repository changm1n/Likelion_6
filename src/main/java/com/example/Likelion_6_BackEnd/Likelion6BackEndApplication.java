package com.example.Likelion_6_BackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Likelion6BackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(Likelion6BackEndApplication.class, args);
	}

}
