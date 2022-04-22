package com.nttdata.api.bankcredit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.api.bankcredit.dao.IBankCreditDAO;
import com.nttdata.api.bankcredit.document.BankCredit;
import com.nttdata.api.bankcredit.service.IBankCreditService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankCreditServiceImpl implements IBankCreditService {

	@Autowired
	private IBankCreditDAO bankCreditDAO;

	@Override
	public Flux<BankCredit> findAll() {
		return bankCreditDAO.findAll();
	}

	@Override
	public Mono<BankCredit> findById(String id) {
		return bankCreditDAO.findById(id);
	}

	@Override
	public Mono<BankCredit> save(BankCredit bankCredit) {
		return bankCreditDAO.save(bankCredit);
	}

	@Override
	public Mono<Void> delete(BankCredit bankCredit) {
		return bankCreditDAO.delete(bankCredit);
	}

	@Override
	public Flux<BankCredit> findByCodeClient(String codeClient) {
		// TODO Auto-generated method stub
		return bankCreditDAO.findByCodeClient(codeClient);
	}
	
}
