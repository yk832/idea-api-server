package com.ideate.ideaapiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
@ConfigurationPropertiesScan("com.ideate.ideaapiserver")
public class IdeaApiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdeaApiServerApplication.class, args);
	}

}
