package com.nezztech.apuesta.websocket;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@SpringBootApplication(scanBasePackages = "com.nezztech.apuesta")
@EnableSwagger2
@EnableScheduling
@EnableJpaRepositories
public class NezztechApplication {

	public static void main(String[] args) {
		SpringApplication.run(NezztechApplication.class, args);
	}
 
}
