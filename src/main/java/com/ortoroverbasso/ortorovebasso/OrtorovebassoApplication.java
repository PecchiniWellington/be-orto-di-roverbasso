package com.ortoroverbasso.ortorovebasso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.ortoroverbasso.ortorovebasso")
@EnableJpaRepositories("com.ortoroverbasso.ortorovebasso.repository")
@EntityScan("com.ortoroverbasso.ortorovebasso.entity")
public class OrtorovebassoApplication {

	public static void main(String[] args) {

		SpringApplication.run(OrtorovebassoApplication.class, args);
	}

}
