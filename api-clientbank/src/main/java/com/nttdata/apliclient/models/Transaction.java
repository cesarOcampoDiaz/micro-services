package com.nttdata.apliclient.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
	private String id;
    private Integer idTypeAccount;
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
