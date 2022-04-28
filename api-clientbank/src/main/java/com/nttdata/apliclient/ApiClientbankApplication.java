package com.nttdata.apliclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;



@SpringBootApplication
@EnableEurekaClient
public class ApiClientbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiClientbankApplication.class, args);
	}

}
