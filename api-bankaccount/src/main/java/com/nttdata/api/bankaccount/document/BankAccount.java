package com.nttdata.api.bankaccount.document;

import java.util.Date;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Unwrapped.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
	@Nullable
	private Boolean main;
	private double balance;
	private Card card;

}
