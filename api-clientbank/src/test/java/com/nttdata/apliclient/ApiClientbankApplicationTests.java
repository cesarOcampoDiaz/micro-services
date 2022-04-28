package com.nttdata.apliclient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nttdata.apliclient.document.Client;
import com.nttdata.apliclient.service.IClientService;


import reactor.core.publisher.Mono;

@SpringBootTest
class ApiClientbankApplicationTests {
	
	@Autowired
	IClientService clientService;

	@Test
	void contextLoads() {
		
		Mono<Client> client =clientService.findByHoldersDni("43462534");
		int a=1;
	}

}
