package com.nttdata.api.bankcredit.service;

import com.nttdata.api.bankcredit.document.BankCredit;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBankCreditService {
	
	public Flux<BankCredit> findAll();
	
	public Mono<BankCredit> findById(String id);
	
	public Mono<BankCredit> save(BankCredit bankCredit);
	
	public Mono<Void> delete(BankCredit bankCredit);
	
	public Flux<BankCredit> findByCodeClient(String codeClient);


}
