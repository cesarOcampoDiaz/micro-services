package com.nttdata.api.creditaccount.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.api.creditaccount.document.CreditAccount;

import reactor.core.publisher.Flux;

@Repository
public interface ICreditAccountDAO extends ReactiveMongoRepository<CreditAccount, String> {
	
	public Flux<CreditAccount> findByCodeClient(String codeClient);

}
