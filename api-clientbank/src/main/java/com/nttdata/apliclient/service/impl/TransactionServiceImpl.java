package com.nttdata.apliclient.service.impl;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import com.nttdata.apliclient.models.Response;
import com.nttdata.apliclient.models.Transaction;
import com.nttdata.apliclient.service.ITransactionService;
import com.nttdata.apliclient.util.Constants;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class TransactionServiceImpl implements ITransactionService {

	@Override
	public Flux<Transaction> listTransactionClientReact(String codeClient, String codeTransaction) {
		// TODO Auto-generated method stub
		return WebClient.create(Constants.PATH_SERVICE_TRANSACTION).get().uri(Constants.PATH_SERVICE_TRANSACTION_URI+codeClient+"/"+codeTransaction).retrieve().bodyToFlux(Transaction.class);
	}

	@Override
	public Mono<Transaction> findAll() {
		// TODO Auto-generated method stub
		return WebClient.create(Constants.PATH_SERVICE_TRANSACTION).get().retrieve().bodyToMono(Transaction.class);
	}

	@Override
	public  Mono<Response> save(Mono<Transaction> transaction) {
		// TODO Auto-generated method stub
		return WebClient.create(Constants.PATH_SERVICE_TRANSACTION)
				.post()
				.uri(Constants.PATH_SERVICE_TRANSACTION_URI)
				.body(transaction,Transaction.class)
				.retrieve().bodyToMono(Response.class );
	}

}
