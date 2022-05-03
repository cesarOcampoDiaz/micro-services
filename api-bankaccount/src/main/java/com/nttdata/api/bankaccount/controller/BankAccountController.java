package com.nttdata.api.bankaccount.controller;

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

import com.nttdata.api.bankaccount.document.BankAccount;
import com.nttdata.api.bankaccount.service.IBankAccountService;
import com.nttdata.api.bankaccount.util.Constants;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Clase controlador bankaccount donde se encargara de interactuar
 * con el front end, en esta clase tiene los metodos CRUD.
 */
@RestController
@RequestMapping("/bankaccount")
public class BankAccountController {

    /**
     * Se declara el tipo LOGGER para el control del la clase.
     */
    private static final Logger LOGGER = LogManager.getLogger(BankAccountController.class);

    /**
     * Se declara el servicio de bankAccountService.
     */
    @Autowired
    private IBankAccountService bankAccountService;

    /**
     * @return retorna todas las cuentas bancarias.
     */
    @GetMapping
    public Mono<ResponseEntity<Flux<BankAccount>>> findAll() {
        return Mono
                .just(ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(bankAccountService.findAll()));
    }


    /**
     * @param id recibe como parametro el id de BankAccount
     * @return retarna el objeto buscado BankAccount.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<BankAccount>> findById(@PathVariable String id) {
        return bankAccountService
				.findById(id)
                .map(ba -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(ba))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * @param codeClient
     * @param typeClient
     * @return
     */
    @GetMapping("/typeClient/{codeClient}/{typeClient}")
    public Mono<ResponseEntity<Flux<BankAccount>>> findByCodeClientAndTypeClientTypeClient(
            @PathVariable String codeClient, @PathVariable Integer typeClient) {
        return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(bankAccountService.findByCodeClientAndTypeClient(codeClient, typeClient)));
    }


    @GetMapping("/client/{codeClient}")
    public Mono<ResponseEntity<Flux<BankAccount>>> findByCodeClient(
            @PathVariable String codeClient) {
        return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(bankAccountService.findByCodeClient(codeClient)));
    }

    /**
     * @param monoBankAccount
     * @return
     */
    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> addBankAccount(
            @Valid @RequestBody Mono<BankAccount> monoBankAccount) {
        Map<String, Object> response = new HashMap<>();

        return monoBankAccount.flatMap(bankAccount -> {
            bankAccount.setMembershipDate(new Date());

            return bankAccountService.findByCodeClientAndTypeClientAndTypeAccountId(bankAccount.getCodeClient(),
                    bankAccount.getTypeClient(), bankAccount.getTypeAccount().getId()).map(ba -> {
                // Un cliente personal solo puede tener un máximo de una cuenta de ahorro, una
                // cuenta corriente o cuentas a plazo fijo.
                if (ba.getTypeClient() == Constants.TIPE_CLIENT_NATURAL) {

                    response.put("message", "El cliente ya tiene una cuenta de este tipo.");
                    response.put("timestamp", new Date());

                } else {
                    // Un cliente empresarial no puede tener una cuenta de ahorro o de plazo fijo
                    // pero sí múltiples cuentas corrientes.
                    if (ba.getTypeAccount().getId() != Constants.TYPE_ACCOUNT_CORRIENTE) {
                        response.put("message", "El cliente solo puede tener multiples cuentas CORRIENTES.");
                        response.put("timestamp", new Date());

                    } else {
                        bankAccountService.save(bankAccount).map(x -> {
                            response.put("obj", x.getObj());
                            response.put("message", x.getMessage());
                            response.put("timestamp", new Date());

                            LOGGER.info("metodo addBankAccount: Agrega una cuenta bancaria");
                            return ResponseEntity.created(URI.create("/bankaccount/".concat(ba.getAccountNumber())))
                                    .contentType(MediaType.APPLICATION_JSON).body(response);
                        });
                    }
                }
                return ResponseEntity.created(URI.create("/bankaccount/".concat(ba.getAccountNumber())))
                        .contentType(MediaType.APPLICATION_JSON).body(response);

            }).switchIfEmpty(bankAccountService.save(bankAccount).map(ba -> {
                response.put("obj", ba.getObj());
                response.put("message", ba.getMessage());
                response.put("timestamp", new Date());

                LOGGER.info("metodo addBankAccount: Agrega una cuenta bancaria");

                return ResponseEntity.created(URI.create("/bankaccount/".concat(bankAccount.getAccountNumber())))

                        .contentType(MediaType.APPLICATION_JSON).body(response);

            })).doOnSuccess(e -> LOGGER.info("OK"));

        }).onErrorResume(t -> {
            return Mono.just(t).cast(WebExchangeBindException.class).flatMap(e -> Mono.just(e.getFieldErrors()))
                    .flatMapMany(Flux::fromIterable)

                    .map(fieldError -> "Field: " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                    .doOnError(e -> LOGGER.error(e.getMessage())).collectList().flatMap(list -> {
                        response.put("errors", list);
                        response.put("timestamp", new Date());
                        response.put("status", HttpStatus.BAD_REQUEST.value());

                        return Mono.just(ResponseEntity.badRequest().body(response));
                    });
        });
    }

	/**
	 * @param bankAccount
	 * @param id
	 * @return
	 */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Object>> editBankAccount(@RequestBody BankAccount bankAccount,
                                                             @PathVariable String id) {
        return bankAccountService.findById(id).flatMap(ba -> {
            ba.setBalance(bankAccount.getBalance());
            return bankAccountService.save(ba);
        }).map(ba -> ResponseEntity.created(URI.create("/bankaccount/".concat(bankAccount.getAccountNumber())))
                .contentType(MediaType.APPLICATION_JSON).body(ba.getObj())).defaultIfEmpty(ResponseEntity.notFound().build());
    }

	/**
	 * @param id
	 * @return
	 */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteBankAccount(@PathVariable String id) {
        return bankAccountService.findById(id).flatMap(ba -> {
            return bankAccountService.delete(ba).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));

        }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

}
