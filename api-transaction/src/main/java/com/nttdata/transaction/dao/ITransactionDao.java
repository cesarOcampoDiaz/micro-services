package com.nttdata.transaction.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.transaction.document.Transaction;

import reactor.core.publisher.Flux;
@Repository
public interface ITransactionDao extends  ReactiveMongoRepository<Transaction,String> {

	Flux<Transaction> findByCodeClientAndIdTypeAccount(String codeClient,Integer idTypeAccount);
	Flux<Transaction> findByCodeClientAndIdTypeAccountAndNumberAccount(String codeClient,Integer idTypeAccount,String numberAccount);
	Flux<Transaction> findByCodeClientAndIdTypeAccountAndNumberCard(String codeClient,Integer idTypeAccount,String numberCard);

}
