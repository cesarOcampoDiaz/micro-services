package com.nttdata.api.creditaccount.document;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Unwrapped.Nullable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "creditaccount")
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
