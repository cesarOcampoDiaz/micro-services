package com.nttdata.api.bankaccount.service.impl;

import com.nttdata.api.bankaccount.models.BankCredit;
import com.nttdata.api.bankaccount.models.ClientCredits;
import com.nttdata.api.bankaccount.models.CreditAccount;
import com.nttdata.api.bankaccount.models.Response;
import com.nttdata.api.bankaccount.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.api.bankaccount.dao.IBankAccountDAO;
import com.nttdata.api.bankaccount.document.BankAccount;
import com.nttdata.api.bankaccount.service.IBankAccountService;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class BankAccountServiceImpl implements IBankAccountService {
	
	private static final Logger LOGGER = LogManager.getLogger(BankAccountServiceImpl.class);

	@Autowired
	private IBankAccountDAO bankAccountDAO;

	@Override
	public Flux<BankAccount> findAll() {
		return bankAccountDAO.findAll();
	}

	@Override
	public Mono<BankAccount> findById(String id) {
		return bankAccountDAO.findById(id);
	}

	@Override
	public Mono<Response> save(BankAccount bankAccount) {
		return WebClient.create(Constants.PATH_SERVICE_BANKCREDIT)
				.get().uri(Constants.PATH_SERVICE_BANKCREDIT_URI.concat(Constants.PATH_CLIENT).concat("/").concat(bankAccount.getCodeClient()))
				.retrieve().bodyToFlux(BankCredit.class)
				.collectList().flatMap(listaBankCredit -> {

					return WebClient.create(Constants.PATH_SERVICE_CREDIACCOUNT)
							.get().uri(Constants.PATH_SERVICE_CREDIACCOUNT_URI.concat(Constants.PATH_CLIENT).concat("/").concat(bankAccount.getCodeClient()))
							.retrieve().bodyToFlux(CreditAccount.class)
							.collectList().flatMap(listaCreditAccount -> {

								return Mono.just(new ClientCredits(listaBankCredit, listaCreditAccount));

							});
				}).filter(cc -> cc.getListBankCredit().stream().anyMatch(bk -> bk.getFeeDue()==1) ||
						cc.getListCreditAccount().stream().anyMatch(ca -> ca.getFeeDue()==1))
				.map(r -> new Response(null, "El cliente tiene deudas pendientes.",new Date()))
				.switchIfEmpty(bankAccountDAO.save(bankAccount)
						.map(x -> new Response(x, "Guardado correctamente.", new Date())));

	}

	@Override
	public Mono<Void> delete(BankAccount bankAccount) {
		return bankAccountDAO.delete(bankAccount);
	}

	@Override
	public Flux<BankAccount> findByCodeClientAndTypeClient(String codeClient, Integer typeClient) {
		// TODO Auto-generated method stub
		return bankAccountDAO.findByCodeClientAndTypeClient(codeClient, typeClient);
	}

	@Override
	public Flux<BankAccount> findByCodeClientAndTypeAccountId(String codeClient, Integer typeAccountId) {

		return bankAccountDAO.findByCodeClientAndTypeAccountId(codeClient, typeAccountId);
	}

	@Override
	public Mono<BankAccount> findByCodeClientAndTypeClientAndTypeAccountId(String codeClient, Integer typeClient,
			Integer typeAccountId) {
	
		
		// TODO Auto-generated method stub
		return bankAccountDAO.findByCodeClientAndTypeClientAndTypeAccountId(codeClient, typeClient, typeAccountId);
	}

	@Override
	public Flux<BankAccount> findByCodeClient(String codeClient) {
		return bankAccountDAO.findByCodeClient(codeClient);
	}

	@Override
	public Mono<BankAccount> findByCodeClientAndAccountNumber(String codeClient, String accountNumber){

	return bankAccountDAO.findByCodeClientAndAccountNumber(codeClient,accountNumber);
	}

	@Override
	public Mono<BankAccount> saveCard(BankAccount bankAccount) {

		return bankAccountDAO.save(bankAccount);


	}

}
