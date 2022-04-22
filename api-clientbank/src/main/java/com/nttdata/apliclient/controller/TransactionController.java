package com.nttdata.apliclient.controller;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.nttdata.apliclient.models.Response;
import com.nttdata.apliclient.models.Transaction;
import com.nttdata.apliclient.service.ITransactionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
	
	
	@Autowired
	ITransactionService service;
	
	private static final Logger LOGGER = LogManager.getLogger(TransactionController.class);
	
	@GetMapping("/client/{codeClient}/{codeTransaction}")
	public Mono<ResponseEntity<Flux<Transaction>>>  listTransactionClient(@PathVariable("codeClient") String codeClient,@PathVariable("codeTransaction") String codeTransaction) {
		LOGGER.info("metodo listTransactionClient: metodo de comunicacion al servicio name api-transaction");
				
		return  Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(
				service.listTransactionClientReact(codeClient,codeTransaction))).defaultIfEmpty(ResponseEntity.notFound().build());
		     } 
	
	
	@GetMapping
	public Mono<ResponseEntity<Mono<Transaction>>> findAll() {
		LOGGER.info("metodo listarTransaction");
		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(service.findAll()));
	}
	
	@PostMapping
	public Mono<ResponseEntity<Mono<Response>>>  saveTransaction(@Valid @RequestBody Mono<Transaction> monoTransaction) {
	
		return  Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(
				service.save(monoTransaction))).defaultIfEmpty(ResponseEntity.notFound().build());

	
	}
	
	
	
	

}
