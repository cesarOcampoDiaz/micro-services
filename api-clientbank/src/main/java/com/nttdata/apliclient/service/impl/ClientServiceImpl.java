package com.nttdata.apliclient.service.impl;

import com.nttdata.apliclient.models.*;
import com.nttdata.apliclient.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.nttdata.apliclient.dao.IClientDao;
import com.nttdata.apliclient.document.Client;
import com.nttdata.apliclient.service.IClientService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.util.stream.Collectors;

@Service
@Transactional
public class ClientServiceImpl implements IClientService {

    @Autowired
    private IClientDao clientDao;

    @Autowired
    private ReactiveCircuitBreakerFactory circuitBreakerFactory;

    private static final Logger LOGGER = LogManager.getLogger(ClientServiceImpl.class);

    @Override
    public Flux<Client> findAll() {
        // TODO Auto-generated method stub
        return clientDao.findAll();
    }

    @Override
    public Mono<Client> findById(String id) {
        // TODO Auto-generated method stub
        return clientDao.findById(id);
    }

    @Override
    public Mono<Client> save(Client client) {
        // TODO Auto-generated method stub
        return clientDao.save(client);
    }

    @Override
    public Mono<Void> delete(Client client) {
        // TODO Auto-generated method stub
        return clientDao.delete(client);
    }

    /**
     * Permitir elaborar un resumen consolidado de un cliente
     * con todos los productos que pueda tener en el banco.
     *
     * @param codeClient
     * @return
     */
    @Override
    public Mono<ClientProducts> findByCodeClientProducts(String codeClient) {
        return WebClient.create(Constants.PATH_SERVICE_BANKACCOUNT)
                .get().uri(Constants.PATH_SERVICE_BANKACCOUNT_URI.concat(Constants.PATH_CLIENT).concat("/").concat(codeClient))
                .retrieve().bodyToFlux(BankAccount.class)
                .transformDeferred(it -> {
                    ReactiveCircuitBreaker rcb = circuitBreakerFactory.create("services");
                    return rcb.run(it, throwable -> Flux.empty());
                }).collectList().flatMap(listBankAccount -> {

                            return WebClient.create(Constants.PATH_SERVICE_BANKCREDIT)
                                    .get().uri(Constants.PATH_SERVICE_BANKCREDIT_URI.concat(Constants.PATH_CLIENT).concat("/").concat(codeClient))
                                    .retrieve().bodyToFlux(BankCredit.class)
                                    .transformDeferred(it -> {
                                        ReactiveCircuitBreaker rcb = circuitBreakerFactory.create("services");
                                        return rcb.run(it, throwable -> Flux.empty());
                                    }).collectList().flatMap(listaBankCredit -> {

                                                return WebClient.create(Constants.PATH_SERVICE_CREDIACCOUNT)
                                                        .get().uri(Constants.PATH_SERVICE_CREDIACCOUNT_URI.concat(Constants.PATH_CLIENT).concat("/").concat(codeClient))
                                                        .retrieve().bodyToFlux(CreditAccount.class)
                                                        .transformDeferred(it -> {
                                                            ReactiveCircuitBreaker rcb = circuitBreakerFactory.create("services");
                                                            return rcb.run(it, throwable -> Flux.empty());
                                                        }).collectList().flatMap(listaCreditAccount -> {

                                                            return Mono.just(new ClientProducts(listBankAccount, listaBankCredit, listaCreditAccount))
                                                                    .switchIfEmpty(Mono.empty());
                                                        });
                                            }
                                    );
                        }
                );
    }


    // llamado de microservicio

    @Override
    public Mono<Client> findByCodeClient(String codeClient) {
        // TODO Auto-generated method stub
        return clientDao.findByCodeClient(codeClient);
    }

    @Override
    public Mono<Client> findByHoldersDni(String dni) {
        // TODO Auto-generated method stub
        return clientDao.findByHoldersDni(dni);
    }

    @Override
    public Mono<Client> findByHoldersDniAndHoldersPhone(String dni, String phone) {
        // TODO Auto-generated method stub
        return clientDao.findByHoldersDniAndHoldersPhone(dni, phone);
    }


    /**
     * Generar un reporte completo y general por producto del banco en intervalo de tiempo especificado por el usuario
     * @param codeClient
     * @param typeAccount
     * @return
     */
    @Override
    public Mono<ClientReports> findByReportGeneralClient(String codeClient,Integer typeAccount,String period){

        return clientDao.findByCodeClient(codeClient)
                .flatMap(client -> {
                    return WebClient.create(Constants.PATH_SERVICE_BANKACCOUNT)
                            .get().uri(Constants.PATH_SERVICE_BANKACCOUNT_URI.concat(Constants.PATH_CLIENT).concat("/").concat(codeClient).concat("/").concat(typeAccount.toString()))
                            .retrieve().bodyToFlux(BankAccount.class)
                            .transformDeferred(it -> {
                                ReactiveCircuitBreaker rcb = circuitBreakerFactory.create("services");
                                return rcb.run(it, throwable -> Flux.empty());
                            })
                            .filter(f->((f.getMembershipDate().getYear()+1900)+String.format("%02d",(f.getMembershipDate().getMonth()+1))).equals(period))
                            .collectList()
                            .flatMap(bankAccount -> {
                                        return WebClient.create(Constants.PATH_SERVICE_TRANSACTION)
                                                .get().uri(Constants.PATH_SERVICE_TRANSACTION_URI.concat("/").concat(codeClient).concat("/").concat(typeAccount.toString()))
                                                .retrieve().bodyToFlux(Transaction.class)
                                                .transformDeferred(it -> {
                                                    ReactiveCircuitBreaker rcb = circuitBreakerFactory.create("services");
                                                    return rcb.run(it, throwable -> Flux.empty());
                                                })
                                                .collectList()
                                                .flatMap(transactionAccount -> {
                                                    bankAccount
                                                            .stream()
                                                            .forEach(p-> p.setListTransaction(transactionAccount
                                                                    .stream()
                                                                    .filter(t->p.getAccountNumber().equals(t.getNumberAccount()))
                                                                    .collect(Collectors.toList())));


                                                    return WebClient.create(Constants.PATH_SERVICE_BANKCREDIT)
                                                            .get().uri(Constants.PATH_SERVICE_BANKCREDIT_URI.concat(Constants.PATH_CLIENT).concat("/").concat(codeClient).concat("/").concat(typeAccount.toString()))
                                                            .retrieve().bodyToFlux(BankCredit.class)
                                                            .transformDeferred(it -> {
                                                                ReactiveCircuitBreaker rcb = circuitBreakerFactory.create("services");
                                                                return rcb.run(it, throwable -> Flux.empty());
                                                            })
                                                            .filter(f->((f.getRequestDate().getYear()+1900)+String.format("%02d",(f.getRequestDate().getMonth()+1))).equals(period))
                                                            .collectList()
                                                            .flatMap(bankCredit -> {
                                                                        return WebClient.create(Constants.PATH_SERVICE_TRANSACTION)
                                                                                .get().uri(Constants.PATH_SERVICE_TRANSACTION_URI.concat("/").concat(codeClient).concat("/").concat(typeAccount.toString()))
                                                                                .retrieve().bodyToFlux(Transaction.class)
                                                                                .transformDeferred(it -> {
                                                                                    ReactiveCircuitBreaker rcb = circuitBreakerFactory.create("services");
                                                                                    return rcb.run(it, throwable -> Flux.empty());
                                                                                })
                                                                                .collectList()
                                                                                .flatMap(transactionBankCredit -> {
                                                                                    bankCredit
                                                                                            .stream()
                                                                                            .forEach(p-> p.setListTransaction(transactionBankCredit
                                                                                                    .stream()
                                                                                                    .filter(t->p.getTypeAccount().getId()==t.getIdTypeAccount())
                                                                                                    .collect(Collectors.toList())));


                                                                                    return WebClient.create(Constants.PATH_SERVICE_CREDIACCOUNT)
                                                                                            .get().uri(Constants.PATH_SERVICE_CREDIACCOUNT_URI.concat(Constants.PATH_CLIENT).concat("/").concat(codeClient).concat("/").concat(typeAccount.toString()))
                                                                                            .retrieve().bodyToFlux(CreditAccount.class)
                                                                                            .transformDeferred(it -> {
                                                                                                ReactiveCircuitBreaker rcb = circuitBreakerFactory.create("services");
                                                                                                return rcb.run(it, throwable -> Flux.empty());
                                                                                            })
                                                                                            .filter(f->((f.getMembershipDate().getYear()+1900)+String.format("%02d",(f.getMembershipDate().getMonth()+1))).equals(period))
                                                                                            .collectList()
                                                                                            .flatMap(creditAccount -> {
                                                                                                        return WebClient.create(Constants.PATH_SERVICE_TRANSACTION)
                                                                                                                .get().uri(Constants.PATH_SERVICE_TRANSACTION_URI.concat("/").concat(codeClient).concat("/").concat(typeAccount.toString()))
                                                                                                                .retrieve().bodyToFlux(Transaction.class)
                                                                                                                .transformDeferred(it -> {
                                                                                                                    ReactiveCircuitBreaker rcb = circuitBreakerFactory.create("services");
                                                                                                                    return rcb.run(it, throwable -> Flux.empty());
                                                                                                                })
                                                                                                                .collectList()
                                                                                                                .flatMap(transactionCreditAccount -> {
                                                                                                                    creditAccount
                                                                                                                            .stream()
                                                                                                                            .forEach(p-> p.setListTransaction(transactionCreditAccount
                                                                                                                                    .stream()
                                                                                                                                    .filter(t->p.getAccountNumber().equals(t.getNumberAccount()))
                                                                                                                                    .collect(Collectors.toList())));


                                                                                                                    return  Mono.just(new ClientReports(client,new ClientProducts(bankAccount,bankCredit,creditAccount)))
                                                                                                                            .switchIfEmpty(Mono.empty());
                                                                                                                }).switchIfEmpty(Mono.empty());
                                                                                                    }).switchIfEmpty(Mono.empty());
                                                                                }).switchIfEmpty(Mono.empty());
                                                                    }).switchIfEmpty(Mono.empty());
                                                }).switchIfEmpty(Mono.empty());
                                    }).switchIfEmpty(Mono.empty());
                }).switchIfEmpty(Mono.empty());
    }


    /**
     * Un cliente puede asociar la tarjeta de débito a todas las cuentas bancarias que posea.
     * @param card
     * @param codeClient
     * @param accountNumber
     * @return
     */
    @Override
    public Mono<BankAccount> editCard(Card card, String codeClient, String accountNumber){
        return WebClient.create(Constants.PATH_SERVICE_BANKACCOUNT)
                .put().uri(Constants.PATH_SERVICE_BANKACCOUNT_URI.concat(Constants.PATH_CLIENT).concat("/").concat(codeClient).concat("/").concat(accountNumber))
                .body(Mono.just(card), Card.class)
                .retrieve()
                .bodyToMono(BankAccount.class)
                .transformDeferred(it -> {
                    ReactiveCircuitBreaker rcb = circuitBreakerFactory.create("services");
                    return rcb.run(it, throwable -> Mono.empty());
                });

    }

    @Override
    public Flux<Transaction> reportTransactionLimit(String codeClient,Integer typeAccount,String numberCard){
        return WebClient.create(Constants.PATH_SERVICE_TRANSACTION)
                .get().uri(Constants.PATH_SERVICE_TRANSACTION_URI.concat("/report/").concat(codeClient).concat("/").concat(typeAccount.toString()).concat("/").concat(numberCard))
                .retrieve().bodyToFlux(Transaction.class)
                .transformDeferred(it -> {
                    ReactiveCircuitBreaker rcb = circuitBreakerFactory.create("services");
                    return rcb.run(it, throwable -> Flux.empty());
                });
    }



}
