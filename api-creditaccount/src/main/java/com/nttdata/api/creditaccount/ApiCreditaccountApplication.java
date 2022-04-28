package com.nttdata.api.creditaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ApiCreditaccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCreditaccountApplication.class, args);
	}

}
