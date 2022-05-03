package com.nttdata.api.bankcredit.models;

import com.nttdata.api.bankcredit.document.Currency;
import com.nttdata.api.bankcredit.document.TypeAccount;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Unwrapped.Nullable;

@Getter
@Setter
public class CreditAccount {

    @Id
    private String accountNumber;
    private String codeClient;
    private TypeAccount typeAccount;
    private Currency currency;
    @Nullable
    private Date membershipDate;
    private Integer payDays;
    private Integer feeDue;
    private double balance;
    private double creditLimit;
    private Card card;

}
