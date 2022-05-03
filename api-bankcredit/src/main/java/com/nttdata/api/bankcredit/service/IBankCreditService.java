package com.nttdata.api.bankcredit.service;

import com.nttdata.api.bankcredit.document.BankCredit;

import com.nttdata.api.bankcredit.models.ClientCredits;
import com.nttdata.api.bankcredit.models.Response;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBankCreditService {
	
	public Flux<BankCredit> findAll();
	
	public Mono<BankCredit> findById(String id);
	
	public Mono<Response> save(BankCredit bankCredit);
	
	public Mono<Void> delete(BankCredit bankCredit);
	
	public Flux<BankCredit> findByCodeClient(String codeClient);


}
