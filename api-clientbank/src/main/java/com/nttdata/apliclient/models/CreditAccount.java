package com.nttdata.apliclient.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Unwrapped.Nullable;

import java.util.Date;

@Getter
@Setter

public class CreditAccount {
	
	@Id
	private String accountNumber;
	private String codeClient;
	private TypeCredit typeCredit;
	private Currency currency;
	@Nullable
	private Date membershipDate;
	private double balance;
	private double creditLimit;
	private Card card;

}
