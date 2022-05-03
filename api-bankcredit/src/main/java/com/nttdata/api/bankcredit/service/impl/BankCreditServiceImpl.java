package com.nttdata.api.bankcredit.service.impl;

import com.nttdata.api.bankcredit.models.ClientCredits;
import com.nttdata.api.bankcredit.models.CreditAccount;
import com.nttdata.api.bankcredit.models.Response;
import com.nttdata.api.bankcredit.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.nttdata.api.bankcredit.dao.IBankCreditDAO;
import com.nttdata.api.bankcredit.document.BankCredit;
import com.nttdata.api.bankcredit.service.IBankCreditService;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

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
	public Mono<Response> save(BankCredit bankCredit) {
		return bankCreditDAO.findByCodeClient(bankCredit.getCodeClient())
				.collectList().flatMap(listBankCredit -> {

					return WebClient.create(Constants.PATH_SERVICE_CREDIACCOUNT)
							.get().uri(Constants.PATH_SERVICE_CREDIACCOUNT_URI.concat(Constants.PATH_CLIENT).concat("/").concat(bankCredit.getCodeClient()))
							.retrieve().bodyToFlux(CreditAccount.class)
							.collectList().flatMap(listCreditAccount -> {

								return Mono.just(new ClientCredits(listBankCredit, listCreditAccount));
							});
				}).filter(cc -> cc.getListBankCredit().stream().anyMatch(bk -> bk.getFeeDue()==1) ||
						cc.getListCreditAccount().stream().anyMatch(ca -> ca.getFeeDue()==1))
				.map(r -> new Response(null, "El cliente tiene deudas pendientes.",new Date()))
				.switchIfEmpty(bankCreditDAO.save(bankCredit)
						.map(x -> new Response(x, "Guardado correctamente.", new Date())));
	}

	@Override
	public Mono<Void> delete(BankCredit bankCredit) {
		return bankCreditDAO.delete(bankCredit);
	}

	@Override
	public Flux<BankCredit> findByCodeClient(String codeClient) {
		return bankCreditDAO.findByCodeClient(codeClient);
	}

}
