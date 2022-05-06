package com.nttdata.api.bankaccount.models;

import com.nttdata.api.bankaccount.document.Card;
import com.nttdata.api.bankaccount.document.Currency;
import com.nttdata.api.bankaccount.document.TypeAccount;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Unwrapped.Nullable;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
