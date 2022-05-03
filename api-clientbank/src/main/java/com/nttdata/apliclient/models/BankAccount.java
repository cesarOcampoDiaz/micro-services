package com.nttdata.apliclient.models;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Unwrapped.Nullable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
	
	private String accountNumber;
	private String typeClient;
	private String codeClient;
	private TypeAccount typeAccount;
	private Currency currency;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date membershipDate;
	private double balance;
	private Card card;
	private List<Transaction> listTransaction;

}
