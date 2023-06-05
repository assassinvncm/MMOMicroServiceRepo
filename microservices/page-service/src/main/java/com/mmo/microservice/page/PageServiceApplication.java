package com.mmo.microservice.page;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PageServiceApplication.class, args);
	}

}
