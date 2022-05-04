package com.nttdata.transaction.service.impl;

import com.nttdata.transaction.models.BankAccount;
import com.nttdata.transaction.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.transaction.dao.ITransactionDao;
import com.nttdata.transaction.document.Transaction;
import com.nttdata.transaction.service.ITransactionService;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@Service
public class TransactionServiceImpl implements ITransactionService {

	@Autowired
	ITransactionDao transactionDao;

	@Override
	public Flux<Transaction> findAll() {
		// TODO Auto-generated method stub
		return transactionDao.findAll();
	}

	@Override
	public Mono<Transaction> findById(String id) {
		// TODO Auto-generated method stub
		return transactionDao.findById(id);
	}

	@Override
	public Mono<Transaction> save(Transaction transaction) {
		Mono<Transaction> mt = Mono.just(transaction);

		return mt.filter(x -> transaction.getIdTypeAccount() == 1 || transaction.getIdTypeAccount() == 2 || transaction.getIdTypeAccount() == 3)
			.flatMap(ba -> {
				return WebClient.create(Constants.PATH_SERVICE_BANKACCOUNT)
						.get().uri(Constants.PATH_SERVICE_BANKACCOUNT_URI.concat("/main/")
								.concat(transaction.getCodeClient()).concat("/").concat(transaction.getNumberCard()))
						.retrieve().bodyToMono(BankAccount.class)
						.flatMap(x -> {
							transaction.setNumberAccount(x.getAccountNumber());
							return transactionDao.save(transaction);
						});
			}).switchIfEmpty(transactionDao.save(transaction));

	}

	@Override
	public  Mono<Void> delete(Transaction transaction) {
		// TODO Auto-generated method stub
		return transactionDao.delete(transaction);
	}
    
	// se filtra solo las transacciones por tipo (prestamos,tarjetas de cretidos, cuentas)
	@Override

	public Flux<Transaction> findByCodeClientAndIdTypeAccount(String codeClient,Integer idTypeAccount){
		// TODO Auto-generated method stub
		return transactionDao.findByCodeClientAndIdTypeAccount(codeClient,idTypeAccount);
	}

	@Override
	public Flux<Transaction> findByCodeClientAndIdTypeAccountAndNumberAccount(String codeClient, Integer idTypeAccount, String numberAccount) {
		return transactionDao.findByCodeClientAndIdTypeAccountAndNumberAccount(codeClient,idTypeAccount,numberAccount);
	}
	@Override
	public Flux<Transaction> findByCodeClientAndIdTypeAccountAndNumberCard(String codeClient,Integer idTypeAccount,String numberCard){
		return transactionDao.findByCodeClientAndIdTypeAccountAndNumberCard(codeClient,idTypeAccount,numberCard).limitRate(10);
	}


}
