package com.nttdata.api.bankaccount.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nttdata.api.bankaccount.document.Currency;
import com.nttdata.api.bankaccount.document.TypeAccount;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Unwrapped.Nullable;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankCredit {
	
	@Id
	private String id;
	private String codeClient;
	private Currency currency;
	private TypeAccount typeAccount;
	@Nullable
	private Date requestDate;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date startDate;
	private Integer payDays;
	private Integer feeDue;
	private double amount;
	private Integer fee;
}
