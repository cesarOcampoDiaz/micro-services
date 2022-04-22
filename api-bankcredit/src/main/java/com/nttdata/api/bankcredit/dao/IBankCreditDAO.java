package com.nttdata.api.bankcredit.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.api.bankcredit.document.BankCredit;

import reactor.core.publisher.Flux;

@Repository
public interface IBankCreditDAO extends ReactiveMongoRepository<BankCredit, String> {
	
	public Flux<BankCredit> findByCodeClient(String codeClient);

}
