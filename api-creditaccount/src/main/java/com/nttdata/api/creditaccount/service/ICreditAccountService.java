package com.nttdata.api.creditaccount.service;

import com.nttdata.api.creditaccount.document.CreditAccount;

import com.nttdata.api.creditaccount.models.Response;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditAccountService {
	
	public Flux<CreditAccount> findAll();
	
	public Mono<CreditAccount> findById(String id);
	
	public Mono<Response> save(CreditAccount creditAccount);
	
	public Mono<Void> delete(CreditAccount creditAccount);
	
	public Flux<CreditAccount> findByCodeClient(String codeClient);

	public Flux<CreditAccount> findByCodeClientAndTypeAccountId(String codeClient, Integer typeAccountId);

}
