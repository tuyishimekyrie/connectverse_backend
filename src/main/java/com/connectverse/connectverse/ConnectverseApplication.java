package com.connectverse.connectverse;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConnectverseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectverseApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner (String[] args) {
		return runner -> {
			System.out.println("-------------" +
					"----------------------------" +
					"💯💯💯💯💯💯✔✔✔✔✔✔Server running on port 8080..., Happy Coding");
		};
	}
}
