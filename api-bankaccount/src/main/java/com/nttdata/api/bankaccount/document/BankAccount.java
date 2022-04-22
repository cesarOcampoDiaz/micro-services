package com.nttdata.api.bankaccount.document;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Unwrapped.Nullable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "bankaccount")
public class BankAccount {
	
	@Id
	private String accountNumber;
	private Integer typeClient;
	private String codeClient;
	private TypeAccount typeAccount;
	private Currency currency;
	@Nullable
	private Date membershipDate;
	private double balance;
	private Card card;

}
