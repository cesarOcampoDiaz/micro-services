package com.nttdata.apliclient.service.impl;

import com.nttdata.apliclient.models.*;
import com.nttdata.apliclient.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.nttdata.apliclient.dao.IClientDao;
import com.nttdata.apliclient.document.Client;
import com.nttdata.apliclient.service.IClientService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements IClientService {

    @Autowired
    private IClientDao clientDao;


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
                .collectList().flatMap(listBankAccount -> {

                            return WebClient.create(Constants.PATH_SERVICE_BANKCREDIT)
                                    .get().uri(Constants.PATH_SERVICE_BANKCREDIT_URI.concat(Constants.PATH_CLIENT).concat("/").concat(codeClient))
                                    .retrieve().bodyToFlux(BankCredit.class)
                                    .collectList().flatMap(listaBankCredit -> {

                                                return WebClient.create(Constants.PATH_SERVICE_CREDIACCOUNT)
                                                        .get().uri(Constants.PATH_SERVICE_CREDIACCOUNT_URI.concat(Constants.PATH_CLIENT).concat("/").concat(codeClient))
                                                        .retrieve().bodyToFlux(CreditAccount.class)
                                                        .collectList().flatMap(listaCreditAccount -> {

                                                            return Mono.just(new ClientProducts(listBankAccount, listaBankCredit, listaCreditAccount));

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

}
