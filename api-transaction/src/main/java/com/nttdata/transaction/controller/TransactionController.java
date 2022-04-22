 package com.nttdata.transaction.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.nttdata.transaction.document.Transaction;
import com.nttdata.transaction.service.ITransactionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
	@Autowired
	private ITransactionService service;

	private static final Logger LOGGER = LogManager.getLogger(TransactionController.class);
    //lista todas las transacciones, se agrega al Mono la respuesta Ok y el contenido JSON en cabecra y en body se agrega la lista
	@GetMapping
	public Mono<ResponseEntity<Flux<Transaction>>> findAll() {
		LOGGER.info("metodo listarTransaction");
		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(service.findAll()));
	}

	
    //Busca la transacciones por codigo de cliente u codigo de transaccion y el tipo de transaccion
	
	@GetMapping("/client/{codeClient}/{codeTransaction}")
	public Mono<ResponseEntity<Flux<Transaction>>>  listTransactionClient(@PathVariable("codeClient") String codeClient,@PathVariable("codeTransaction") Integer codeTransaction) {
		LOGGER.info("metodo listarTransactionCliente cliente "+codeClient);
		return  Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(
				service.findByCodeClientAndTypeTransactionId(codeClient,codeTransaction))).defaultIfEmpty(ResponseEntity.notFound().build());
	}

	
	//busca por el codigo en el caso que el objeto es nullo nos retorna por defecto la despuesta not data found
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Transaction>> findById(@PathVariable String id) {
		LOGGER.info("metodo findById: Retorna una transaccion por su ID"+id);
		
		return service.findById(id).map(t -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(t))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	/*Se guarda la transaccion, se envia el objeto por requestBoby, 
	  se declara el HashMap, para agregar una respuesta clave valor con la fecha del sistema
	  se crea la respuesta para su envio.
	  si se genera error se envia la respuesta del error
	*/
	@PostMapping
	public Mono<ResponseEntity<Map<String, Object>>> save(@Valid @RequestBody Mono<Transaction> monoTransaction) {
		Map<String, Object> respuesta = new HashMap<>();

		return monoTransaction.flatMap(transaction -> {
			transaction.setDateTransaction(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
			return service.save(transaction).map(t -> {
				LOGGER.info("metodo save: Agrega una transaccion");
				
				respuesta.put("obj", t);
				respuesta.put("message", "Transacion guardada con exito");
				respuesta.put("timestamp", new Date());

				return ResponseEntity.created(URI.create("/api/transaction".concat(t.getId())))
						.contentType(MediaType.APPLICATION_JSON).body(respuesta);
			}).doOnSuccess(e->LOGGER.info("OK"));
		}).onErrorResume(t -> {
			return Mono.just(t).cast(WebExchangeBindException.class).flatMap(e -> Mono.just(e.getFieldErrors()))
					.flatMapMany(Flux::fromIterable)
					.map(fieldError -> "El campo: " + fieldError.getField() + " " + fieldError.getDefaultMessage())
					.doOnError(e->LOGGER.error(e.getMessage()))
					.collectList().flatMap(list -> {
						respuesta.put("errors", list);
						respuesta.put("timestamp", new Date());
						respuesta.put("status", HttpStatus.BAD_REQUEST.value());

						return Mono.just(ResponseEntity.badRequest().body(respuesta));
					});
		}).doOnError(e -> LOGGER.warn("metodo save: Hubo errores al agregar una transaccion"));
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<Transaction>> update(@RequestBody Transaction transaction, @PathVariable String id) {
		LOGGER.info("metodo update: Edita una transaccion");
		
		return service.findById(id).flatMap(c -> {
			c.setAmount(transaction.getAmount());
			return service.save(c);
		}).map(c -> ResponseEntity.created(URI.create("/api/transaction/".concat(c.getId())))
				.contentType(MediaType.APPLICATION_JSON).body(c)).defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
		LOGGER.info("metodo delete: Elimina una transaccion");
		
		return service.findById(id).flatMap(c -> {
			return service.delete(c).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));

		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}

}
