package com.cju.cuhaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableJpaAuditing
@SpringBootApplication
public class CuhaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CuhaApiApplication.class, args);
	}

}
