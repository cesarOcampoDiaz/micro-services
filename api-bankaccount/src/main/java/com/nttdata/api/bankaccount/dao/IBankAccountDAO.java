package com.nttdata.api.bankaccount.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.api.bankaccount.document.BankAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface IBankAccountDAO extends ReactiveMongoRepository<BankAccount, String> {

	public Flux<BankAccount> findByCodeClientAndTypeClient(String codeClient, Integer typeClient);

	public Mono<BankAccount> findByCodeClientAndTypeAccountId(String codeClient, Integer typeAccountId);
	
	public Mono<BankAccount> findByCodeClientAndTypeClientAndTypeAccountId(String codeClient, Integer typeClient,Integer typeAccountId);
}
