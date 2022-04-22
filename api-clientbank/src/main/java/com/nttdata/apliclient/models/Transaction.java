package com.nttdata.apliclient.models;


import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Transaction {

	private String id;
    private TypeTransaction transaction;
    private String numberCard;
    private Integer typeCard;
    private String codeClient;
    private String numberAccount;
    private TypeOperation operation;
    private DestinationAccount destinationAccount;
    private Double amount;
    private LocalDateTime dateTransaction;
    


    
}
