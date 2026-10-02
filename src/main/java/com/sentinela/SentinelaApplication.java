package com.sentinela;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SentinelaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SentinelaApplication.class, args);
	}
}
