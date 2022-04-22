package com.nttdata.apliclient.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.nttdata.apliclient.dao.IClientDao;
import com.nttdata.apliclient.document.Client;
import com.nttdata.apliclient.models.Transaction;
import com.nttdata.apliclient.service.IClientService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

	@Override
	public Flux<Transaction> listTransactionClientReact(String codeClient, String codeTransaction) {

		
		// TODO Auto-generated method stub
		return WebClient.create("http://localhost:8085").get().uri("/api/transaction/client/"+codeClient+"/"+codeTransaction).retrieve().bodyToFlux(Transaction.class);
	}

}
