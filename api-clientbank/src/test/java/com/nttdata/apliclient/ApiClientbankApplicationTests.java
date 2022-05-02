package com.nttdata.apliclient;

import com.nttdata.apliclient.controller.ClientController;
import com.nttdata.apliclient.models.ClientProducts;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.nttdata.apliclient.document.Client;
import com.nttdata.apliclient.service.IClientService;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ClientController.class)

class ApiClientbankApplicationTests {
	@Autowired
	private WebTestClient webTestClient;
	@Autowired
	private IClientService clientService;


	@Test
	public void findByCodeClientProducts() {

		int uno=1;
		/*var codeClient ="43462534";
		var clientProductsMono = Mono.just(new ClientProducts());
		when(clientService.findByCodeClientProducts(codeClient).thenReturn(Mono.just(clientProductsMono)));

		var clientProducts  = webTestClient.get().uri("/report/43462534")
				.exchange()
				.expectStatus().isOk()
				.returnResult(ClientProducts.class)
				.getResponseBody()	;

		StepVerifier.create(clientProductsMono)
				.expectSubscription();*/



	}
}
