package com.nttdata.api.bankaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ApiBankaccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiBankaccountApplication.class, args);
	}

}
