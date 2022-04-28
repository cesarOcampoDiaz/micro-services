package com.nttdata.api.creditaccount.service;

import com.nttdata.api.creditaccount.document.CreditAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditAccountService {
	
	public Flux<CreditAccount> findAll();
	
	public Mono<CreditAccount> findById(String id);
	
	public Mono<CreditAccount> save(CreditAccount creditAccount);
	
	public Mono<Void> delete(CreditAccount creditAccount);
	
	public Flux<CreditAccount> findByCodeClient(String codeClient);

}
