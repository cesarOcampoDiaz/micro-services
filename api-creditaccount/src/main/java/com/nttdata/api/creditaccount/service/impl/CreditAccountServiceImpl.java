package com.nttdata.api.creditaccount.service.impl;

import com.nttdata.api.creditaccount.models.BankCredit;
import com.nttdata.api.creditaccount.models.ClientCredits;
import com.nttdata.api.creditaccount.models.Response;
import com.nttdata.api.creditaccount.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.api.creditaccount.dao.ICreditAccountDAO;
import com.nttdata.api.creditaccount.document.CreditAccount;
import com.nttdata.api.creditaccount.service.ICreditAccountService;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

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
	public Mono<Response> save(CreditAccount creditAccount) {
		return creditAccountDAO.findByCodeClient(creditAccount.getCodeClient())
				.collectList().flatMap(listCreditAccount -> {

					return WebClient.create(Constants.PATH_SERVICE_BANKCREDIT)
							.get().uri(Constants.PATH_SERVICE_BANKCREDIT_URI.concat(Constants.PATH_CLIENT).concat("/").concat(creditAccount.getCodeClient()))
							.retrieve().bodyToFlux(BankCredit.class)
							.collectList().flatMap(listBankCredit -> {

								return Mono.just(new ClientCredits(listBankCredit, listCreditAccount));
							});
				}).filter(cc -> cc.getListBankCredit().stream().anyMatch(bk -> bk.getFeeDue()==1) ||
						cc.getListCreditAccount().stream().anyMatch(ca -> ca.getFeeDue()==1))
				.map(r -> new Response(null, "El cliente tiene deudas pendientes.",new Date()))
				.switchIfEmpty(creditAccountDAO.save(creditAccount)
						.map(x -> new Response(x, "Guardado correctamente.", new Date())));

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
