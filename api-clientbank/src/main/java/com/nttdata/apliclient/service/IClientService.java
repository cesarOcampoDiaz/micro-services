package com.nttdata.apliclient.service;

import com.nttdata.apliclient.document.Client;
import com.nttdata.apliclient.models.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IClientService {
	
	//Flux y Mono son flujos de elementos, solamente que Flux te va emitir varios elementos
	
	//devueve varios elementos observable te 
	public Flux<Client> findAll();
	
	//devuelve un solo elemento obsevable
	public Mono<Client> findById(String id);
	
	//busca por codigo de cliente (DNI, RUC, otros)
	public Mono<Client> findByCodeClient(String codeClient);
	
	public Mono<Client> findByHoldersDni(String dni);
	
	public Mono<Client> findByHoldersDniAndHoldersPhone(String dni,String phone);
	
	public Mono<Client> save(Client client);
	
	public Mono<Void> delete(Client client);

	public Mono<ClientProducts> findByCodeClientProducts(String codeClient);

	public Mono<ClientReports> findByReportGeneralClient(String codeClient,Integer typeAccount, String period);

	public Mono<BankAccount> editCard(Card card, String codeClient, String accountNumber);

	public Flux<Transaction> reportTransactionLimit(String codeClient,Integer typeAccount,String numberCard);



}
