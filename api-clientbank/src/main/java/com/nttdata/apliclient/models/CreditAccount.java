package com.nttdata.apliclient.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Unwrapped.Nullable;

import java.util.Date;
import java.util.List;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor

public class CreditAccount {
	

	private String accountNumber;
	private String codeClient;
	private TypeAccount typeAccount;
	private Currency currency;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date membershipDate;
	private Integer payDays;
	private Integer feeDue;
	private double balance;
	private double creditLimit;
	private Card card;

	private List<Transaction> listTransaction;

}
