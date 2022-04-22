package com.nttdata.apliclient.models;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Unwrapped.Nullable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankAccount {
	
	private String accountNumber;
	private String typeClient;
	private String codeClient;
	private TypeAccount typeAccount;
	private Currency currency;
	@Nullable
	private Date membershipDate;
	private double balance;
	private Card card;

}
