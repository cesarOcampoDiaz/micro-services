package com.nttdata.apliclient.document;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "clients")
public class Client extends Person {

	@Id
	private String id;
	private String codeClient;
	private String ruc;
	private String corporation;

	
	private TypeClient typeClient;
	private List<Person> holders;
	private List<Person> signatory;

	
}
