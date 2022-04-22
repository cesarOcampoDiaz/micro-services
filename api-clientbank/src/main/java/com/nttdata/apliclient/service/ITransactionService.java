package com.nttdata.apliclient.service;



import com.nttdata.apliclient.models.Response;
import com.nttdata.apliclient.models.Transaction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITransactionService {
	
	public Flux<Transaction> listTransactionClientReact(String codeClient, String codeTransaction);
	public Mono<Transaction>  findAll();
	public  Mono<Response>save(Mono<Transaction> transaction);

}
