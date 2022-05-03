package com.nttdata.transaction.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.transaction.dao.ITransactionDao;
import com.nttdata.transaction.document.Transaction;
import com.nttdata.transaction.service.ITransactionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
		// TODO Auto-generated method stub
		return transactionDao.save(transaction);
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
