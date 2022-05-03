package com.nttdata.api.bankcredit.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Card {

	private String cardNumber;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date expiryDate;
	private TypeCard typeCard;
	
}
