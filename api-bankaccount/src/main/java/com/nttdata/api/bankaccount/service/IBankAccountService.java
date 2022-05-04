package com.nttdata.api.bankaccount.service;

import com.nttdata.api.bankaccount.document.BankAccount;

import com.nttdata.api.bankaccount.models.Response;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBankAccountService {
	
	public Flux<BankAccount> findAll();
	
	public Mono<BankAccount> findById(String id);
	
	public Mono<Response> save(BankAccount bankAccount);
	
	public Mono<Void> delete(BankAccount bankAccount);
	
	public Flux<BankAccount> findByCodeClientAndTypeClient(String codeClient, Integer typeClient);

	public Flux<BankAccount> findByCodeClientAndTypeAccountId(String codeClient, Integer typeAccountId);
	
	public Mono<BankAccount> findByCodeClientAndTypeClientAndTypeAccountId(String codeClient, Integer typeClient,Integer typeAccountId);

	public Flux<BankAccount> findByCodeClient(String codeClient);

	public Mono<BankAccount> findByCodeClientAndAccountNumber(String codeClient, String accountNumber);

	public Mono<BankAccount> mainAccount(String codeClient, String cardNumber);

	public Mono<BankAccount> saveCard(BankAccount bankAccount);
}
