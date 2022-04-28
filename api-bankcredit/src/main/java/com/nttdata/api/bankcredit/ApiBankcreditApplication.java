package com.nttdata.api.bankcredit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ApiBankcreditApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiBankcreditApplication.class, args);
	}

}
