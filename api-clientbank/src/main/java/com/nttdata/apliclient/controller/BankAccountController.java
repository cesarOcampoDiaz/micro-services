package com.nttdata.apliclient.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.apliclient.models.BankAccount;
import com.nttdata.apliclient.models.Response;
import com.nttdata.apliclient.service.IBankAccountService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bankaccount")
public class BankAccountController {
	
	@Autowired
	private IBankAccountService service;
	
	private static final Logger LOGGER = LogManager.getLogger(BankAccountController.class);

	@PostMapping("/client")
	public Mono<ResponseEntity<Mono<Response>>> saveBankAccountClient(@RequestBody BankAccount bankAccount) {
		
		LOGGER.info("metodo saveBankAccountClient: metodo de comunicacion al servicio name api-bankaccount");
		
		return  Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
				.body(service.saveBankAccount(bankAccount)))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
}
