package com.nttdata.api.bankcredit.controller;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.nttdata.api.bankcredit.document.BankCredit;
import com.nttdata.api.bankcredit.service.IBankCreditService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bankcredit")
public class BankCreditController {
	
	private static final Logger LOGGER = LogManager.getLogger(BankCreditController.class);

	@Autowired
	private IBankCreditService bankCreditService;

	@GetMapping
	public Mono<ResponseEntity<Flux<BankCredit>>> findAll() {
		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(bankCreditService.findAll()));
	}


	@GetMapping("credit/{codeClient}")
	public Mono<ResponseEntity<Flux<BankCredit>>> findByCodeClient(@PathVariable String codeClient) {
		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(bankCreditService.findByCodeClient(codeClient)));
	}

	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<BankCredit>> findById(@PathVariable String id) {
		return bankCreditService.findById(id).map(bc -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(bc))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Mono<ResponseEntity<Map<String, Object>>> addBankCredit(@Valid @RequestBody Mono<BankCredit> monoBankCredit) {
		Map<String, Object> response = new HashMap<>();

		return monoBankCredit.flatMap(bankCredit -> {
			bankCredit.setRequestDate(new Date());
			return bankCreditService.save(bankCredit).map(bc -> {
				response.put("BankCredit", bc);
				response.put("message", "Successfully saved.");
				response.put("timestamp", new Date());

				return ResponseEntity.created(URI.create("/api/bankcredit/".concat(bc.getId())))
						.contentType(MediaType.APPLICATION_JSON).body(response);
			});
		}).onErrorResume(t -> {
			return Mono.just(t).cast(WebExchangeBindException.class).flatMap(e -> Mono.just(e.getFieldErrors()))
					.flatMapMany(Flux::fromIterable)
					.map(fieldError -> "Field: " + fieldError.getField() + " " + fieldError.getDefaultMessage())
					.collectList().flatMap(list -> {
						response.put("errors", list);
						response.put("timestamp", new Date());
						response.put("status", HttpStatus.BAD_REQUEST.value());

						return Mono.just(ResponseEntity.badRequest().body(response));
					});
		});
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<BankCredit>> editBankCredit(@RequestBody BankCredit bankCredit, @PathVariable String id) {
		return bankCreditService.findById(id).flatMap(bc -> {
			bc.setAmount(bankCredit.getAmount());
			bc.setFee(bankCredit.getFee());
			return bankCreditService.save(bc);
		}).map(bc -> ResponseEntity.created(URI.create("/api/bankcredit/".concat(bc.getId())))
				.contentType(MediaType.APPLICATION_JSON).body(bc)).defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteBankCredit(@PathVariable String id) {
		return bankCreditService.findById(id).flatMap(bc -> {
			return bankCreditService.delete(bc).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));

		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}

}
