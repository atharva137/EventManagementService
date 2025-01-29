package com.victor.ray.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com")
public class RayEventServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RayEventServiceApplication.class, args);
	}

}
