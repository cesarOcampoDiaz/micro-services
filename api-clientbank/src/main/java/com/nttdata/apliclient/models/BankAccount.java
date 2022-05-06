package com.nttdata.apliclient.models;


import java.util.Date;
import java.util.List;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
	
	private String accountNumber;
	private String typeClient;
	private String codeClient;
	private TypeAccount typeAccount;
	private Currency currency;

	private Date membershipDate;
	private double balance;
	private Card card;
	private List<Transaction> listTransaction;

}
