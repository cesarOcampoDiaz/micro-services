package com.nttdata.apliclient.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.nttdata.apliclient.models.BankAccount;
import com.nttdata.apliclient.models.Response;
import com.nttdata.apliclient.service.IBankAccountService;
import com.nttdata.apliclient.util.Constants;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Transactional
@Service
public class BankAccountServiceImpl implements IBankAccountService {
	
	@Override
	public Flux<BankAccount> findAll() {
	
		return WebClient.create(Constants.PATH_SERVICE_BANKACCOUNT).get()
				.uri(Constants.PATH_SERVICE_BANKACCOUNT_URI)
				.retrieve()
				.bodyToFlux(BankAccount.class);
	}

	@Override
	public Mono<Response> saveBankAccount(BankAccount bankAccount) {
		
		return WebClient.create(Constants.PATH_SERVICE_BANKACCOUNT).post()
				.uri(Constants.PATH_SERVICE_BANKACCOUNT_URI)
				.body(Mono.just(bankAccount), BankAccount.class)
				.retrieve()
				.bodyToMono(Response.class);
	}
	
}
