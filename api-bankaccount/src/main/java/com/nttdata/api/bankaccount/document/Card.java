package com.nttdata.api.bankaccount.document;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card {

	private String cardNumber;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date expiryDate;
	private TypeCard typeCard;
	
}
