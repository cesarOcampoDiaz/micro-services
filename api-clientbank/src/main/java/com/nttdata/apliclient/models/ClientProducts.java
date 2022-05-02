package com.nttdata.apliclient.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientProducts {

    private List<BankAccount> listBankAccount;

    private List<BankCredit> listBankCredit;

    private List<CreditAccount> listCreditAccount;


}
