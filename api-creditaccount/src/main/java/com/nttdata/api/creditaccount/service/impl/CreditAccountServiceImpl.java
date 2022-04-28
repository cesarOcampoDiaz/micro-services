package com.nttdata.api.creditaccount.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.api.creditaccount.dao.ICreditAccountDAO;
import com.nttdata.api.creditaccount.document.CreditAccount;
import com.nttdata.api.creditaccount.service.ICreditAccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditAccountServiceImpl implements ICreditAccountService {

	@Autowired
	private ICreditAccountDAO creditAccountDAO;

	@Override
	public Flux<CreditAccount> findAll() {
		return creditAccountDAO.findAll();
	}

	@Override
	public Mono<CreditAccount> findById(String id) {
		return creditAccountDAO.findById(id);
	}

	@Override
	public Mono<CreditAccount> save(CreditAccount creditAccount) {
		return creditAccountDAO.save(creditAccount);
	}

	@Override
	public Mono<Void> delete(CreditAccount creditAccount) {
		return creditAccountDAO.delete(creditAccount);
	}

	@Override
	public Flux<CreditAccount> findByCodeClient(String codeClient) {
		// TODO Auto-generated method stub
		return creditAccountDAO.findByCodeClient(codeClient);
	}
	
}
