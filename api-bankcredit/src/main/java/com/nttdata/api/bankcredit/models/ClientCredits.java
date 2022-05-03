package com.nttdata.api.bankcredit.models;

import com.nttdata.api.bankcredit.document.BankCredit;
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
