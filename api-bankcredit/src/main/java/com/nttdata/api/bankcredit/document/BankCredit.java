package com.nttdata.api.bankcredit.document;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Unwrapped.Nullable;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "bankcredit")
public class BankCredit {
	
	@Id
	private String id;
	private String codeClient;
	private Currency currency;
	private TypeCredit typeCredit;
	@Nullable
	private Date requestDate;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date startDate;
	private double amount;
	private Integer fee;
}
