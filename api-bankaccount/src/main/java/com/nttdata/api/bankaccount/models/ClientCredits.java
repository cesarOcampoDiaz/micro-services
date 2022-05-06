package com.nttdata.api.bankaccount.models;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientCredits {

    private List<BankCredit> listBankCredit;
    private List<CreditAccount> listCreditAccount;

}
