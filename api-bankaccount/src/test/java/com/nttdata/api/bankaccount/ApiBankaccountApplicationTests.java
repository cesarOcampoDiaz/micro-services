package com.nttdata.api.bankaccount;

import com.nttdata.api.bankaccount.controller.BankAccountController;
import com.nttdata.api.bankaccount.document.*;
import com.nttdata.api.bankaccount.service.IBankAccountService;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;
import java.util.Date;

@ExtendWith(SpringExtension.class)
@WebFluxTest(BankAccountController.class)
class ApiBankaccountApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private IBankAccountService bankAccountService;

	@Test
	public void findMainAccount() {
		var bankAccount = new BankAccount("012-34456525243234-167",1,"91919191",
				new TypeAccount(1,"AHORRO"), new Currency(1,"SOL"), new Date(), true,300, new Card("0908887876564535",new Date("21/04/2027"),
					new TypeCard(1,"VISA DEBITO CLASICA")));

		when(bankAccountService.mainAccount("91919191","0908887876564535")).thenReturn(Mono.just(bankAccount));
		var main = webTestClient.get().uri("/bankaccount/main/91919191/0908887876564535")
				.exchange()
				.expectStatus().isOk()
				.returnResult(BankAccount.class)
				.getResponseBody();
		StepVerifier.create(Mono.just(bankAccount))
				.expectSubscription()
				.expectNextMatches(x -> x.getCodeClient().equals("91919191"))
				.verifyComplete();
	}

}
