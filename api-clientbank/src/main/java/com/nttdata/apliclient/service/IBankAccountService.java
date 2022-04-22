package com.nttdata.apliclient.service;

import com.nttdata.apliclient.models.BankAccount;
import com.nttdata.apliclient.models.Response;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBankAccountService {
	
	public Flux<BankAccount> findAll();
	public Mono<Response> saveBankAccount(BankAccount bankAccount);

}
