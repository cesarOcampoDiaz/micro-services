package com.nttdata.apliclient.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {

	private String cardNumber;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date expiryDate;
	private TypeCard typeCard;
	
}
