package com.nttdata.transaction.document;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "transacctions")
public class Transaction {
	@Id
	private String id;
    private TypeTransaction typeTransaction;
    private String numberCard;
    private Integer typeCard;
    private String codeClient;
    private String numberAccount;
    private Currency currency;
    private TypeOperation typeOperation;
    private Account originAccount;
    private Account destinationAccount;
    private Double amount;
    private LocalDateTime dateTransaction;

    
}
