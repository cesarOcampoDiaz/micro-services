package com.nttdata.transaction.models;

import com.nttdata.transaction.document.Currency;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Unwrapped.Nullable;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {

    @Id
    private String accountNumber;
    private Integer typeClient;
    private String codeClient;
    private TypeAccount typeAccount;
    private Currency currency;
    @Nullable
    private Date membershipDate;
    @Nullable
    private Boolean main;
    private double balance;
    private Card card;

}
