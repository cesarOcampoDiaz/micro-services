package com.nttdata.api.creditaccount.models;

import com.nttdata.api.creditaccount.document.CreditAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientCredits {

    private List<BankCredit> listBankCredit;
    private List<CreditAccount> listCreditAccount;

}
