package com.nttdata.apliclient;

import com.nttdata.apliclient.controller.ClientController;
import com.nttdata.apliclient.models.*;
import com.nttdata.apliclient.service.IClientService;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;


import reactor.test.StepVerifier;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import  static  org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import  static  org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ClientController.class)
class ApiClientbankApplicationTests {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private IClientService clientService;


    @Test
    public void findByCodeClientProducts() {

        int uno = 1;
        var codeClient = "43462534";
        //llenado de datos
        Currency currency = new Currency(1, "SOL");
        TypeAccount typeAccount = new TypeAccount(2, "CORRIENTE");
        TypeOperation typeOperation = new TypeOperation(2,"GASTOS");
        Card card = new Card("1282112128282921", new Date("15/05/2030"), new TypeCard(1, "VISA DEBITO CLASICA"));
        Transaction transaction= new Transaction("001111",1,"1282112128282920",1,"43462534","197-2658546-04",currency,typeOperation,null,null,null, LocalDateTime.now() );
        List<Transaction> listTransaction = new ArrayList<>();
        //listTransaction.add(transaction);
        BankAccount bankAccount = new BankAccount("197-2658546-13","2",codeClient,typeAccount,currency,new Date(),0.0,card, listTransaction );
        BankCredit bankCredit = new BankCredit("01",codeClient,currency,typeAccount,new Date(),new Date(),null,null,40000.0,14,listTransaction );
        CreditAccount creditAccount = new CreditAccount("123-1645655675-03",codeClient,typeAccount,currency, new Date(),null,null,0.0,5000.0,card,listTransaction);
        List<BankAccount> listBankAccount = new ArrayList<>();
        listBankAccount.add(bankAccount);
        List<BankCredit> listBankCredit = new ArrayList<>();
        //listBankCredit.add(bankCredit);
        List<CreditAccount> listCreditAccount = new ArrayList<>();
        //listCreditAccount.add(creditAccount);

        var clientProductsMono = Mono.just(new ClientProducts(listBankAccount,listBankCredit,listCreditAccount));
        when(clientService.findByCodeClientProducts(codeClient)).thenReturn(clientProductsMono);

        var responseBody = webTestClient.get().uri("/client/report/43462534")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ClientProducts.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(e->e.getListBankAccount().get(0).getAccountNumber().equals("197-2658546-13"))
                .verifyComplete();


    }
}
