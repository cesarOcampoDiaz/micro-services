package com.nttdata.transaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.var;
import com.nttdata.transaction.controller.TransactionController;
import com.nttdata.transaction.document.Account;
import com.nttdata.transaction.document.Currency;
import com.nttdata.transaction.document.Transaction;
import com.nttdata.transaction.document.TypeOperation;
import com.nttdata.transaction.models.Response;
import com.nttdata.transaction.service.ITransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(TransactionController.class)
class ApiTransactionApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private ITransactionService transactionService;

	@Test
	public void save() throws JsonProcessingException, WebExchangeBindException {
		var currency = new Currency(1,"SOL");
		var typeOperation = new TypeOperation(1,"INGRESOS");
		var accountOrigen = new Account("123456789","Carol");
		var accountDestination = new Account("123456789","Carol");
		var transaction = new Transaction("76",5,"1234567891234567",1,"70707070","123-12345678-123",currency,typeOperation,accountOrigen,accountDestination,300.0,null);
		var response = new Response(transaction, "Transacion guardada con exito", "2022-05-05T16:57:24.103+00:00",null,0);

		when(transactionService.save(any())).thenReturn(Mono.just(transaction));

		webTestClient.post().uri("/transaction")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(response), Response.class)
				.exchange()
				.expectStatus().isCreated();
	}

}
