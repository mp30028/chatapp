package com.zonesoft.chatapp.controllers;

import static com.zonesoft.tryouts.conversation_api.data_generators.GeneratorCore.generateFirstName;
import static com.zonesoft.tryouts.conversation_api.data_generators.GeneratorCore.generateGender;
import static com.zonesoft.tryouts.conversation_api.data_generators.GeneratorCore.generateLastName;
import static com.zonesoft.tryouts.conversation_api.data_generators.GeneratorCore.generateNickname;
import static com.zonesoft.tryouts.conversation_api.data_generators.GeneratorCore.generateRandomInt;
import static com.zonesoft.tryouts.conversation_api.data_generators.GeneratorCore.generateUUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.zonesoft.chatapp.models.Person;

import com.zonesoft.chatapp.services.PersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class PersonControllerTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonControllerTest.class);
	
	private static final int MIN_PERSONS = 2;
	private static final int MAX_PERSONS = 10;

	private static Person PERSON_1;
	private static Person PERSON_2;
	private static List<Person> PERSONS;

	private PersonService service ;
	private WebTestClient client;
	
	private static void createTestData() {
		PERSON_1 = new Person(generateUUID(), generateNickname(), generateFirstName(generateGender()),
				generateLastName());
		PERSON_2 = new Person(generateUUID(), generateNickname(), generateFirstName(generateGender()),
				generateLastName());
		PERSONS = new ArrayList<Person>();
		PERSONS.add(PERSON_1);
		PERSONS.add(PERSON_2);
		int noOfPersons = generateRandomInt(MIN_PERSONS, MAX_PERSONS);
		for (int j = MIN_PERSONS; j < noOfPersons; j++) {
			PERSONS.add(new Person(generateUUID(), generateNickname(), generateFirstName(generateGender()),
					generateLastName()));
		}
	}
	
	private int selectARandomPersonsIndex() {
		return generateRandomInt(0, PERSONS.size()-1);
	}

	@BeforeAll
	static void setUpBeforeAll() {
		createTestData();
	}

	@AfterAll
	static void cleanUpAfterAll() {
		PERSONS = null;
		PERSON_1 = null;
		PERSON_2 = null;
	}

	@BeforeEach
	void setUpBeforeEach() {
		service = mock(PersonService.class);
		PersonController controller = new PersonController(service);
		client = WebTestClient.bindToController(controller).build();
	}
	
	@Test
	void testInsert() {
		Person newPerson =  new Person(generateUUID(), generateNickname(), generateFirstName(generateGender()),generateLastName());
		LOGGER.debug("testInsert.newPerson = {}", newPerson);
		when(service.insert(any())).thenReturn(Mono.just(newPerson));
		client
			.post()
			.uri(uriBuilder -> uriBuilder.path("/api/persons").build())
			.header("Content-Type", "application/json")
			.body(Mono.just(newPerson),Person.class)
			.exchange()
			.expectStatus()
			.isCreated()
			.expectBody()
			.jsonPath("$.id")
			.isEqualTo(newPerson.getId())
			.consumeWith(r -> LOGGER.debug("testInsert: response = {}",new String(r.getResponseBody(), StandardCharsets.UTF_8)));		
		
	}

	@Test
	void testFindAll() {
		when(service.findAll()).thenReturn(Flux.fromIterable(PERSONS));
		client
			.get()
			.uri(uriBuilder -> uriBuilder.path("/api/persons").build())
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.jsonPath("$[0].id").isEqualTo(PERSON_1.getId())
			.jsonPath("$.length()").isEqualTo(PERSONS.size())
			.consumeWith(r -> LOGGER.debug("testFindAll: response = {}",new String(r.getResponseBody(), StandardCharsets.UTF_8)));
	}

	@Test
	void testFindById() {
		int selectedPersonIndex = selectARandomPersonsIndex();
		Person selectedPerson = PERSONS.get(selectedPersonIndex);
		LOGGER.debug("testFindById.selectedPerson = {}", selectedPerson);
		when(service.findById(selectedPerson.getId())).thenReturn(Mono.just(selectedPerson));
		client
			.get()
			.uri(uriBuilder -> uriBuilder.path("/api/persons/{id}").build(selectedPerson.getId()))
			.header("Content-Type", "application/json")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.jsonPath("$.id")
			.isEqualTo(selectedPerson.getId())
			.consumeWith(r -> LOGGER.debug("testFindById: response = {}",new String(r.getResponseBody(), StandardCharsets.UTF_8)));
	}

	@Test
	void testUpdateUser() {
		int selectedPersonIndex = selectARandomPersonsIndex();
		Person selectedPerson = PERSONS.get(selectedPersonIndex);
		LOGGER.debug("FROM testUpdateUser: selectedPerson = {}", selectedPerson);
		String newMoniker = generateNickname();
		selectedPerson.setMoniker(newMoniker);
		when(service.update(any())).thenReturn(Mono.just(selectedPerson));
		client
			.put()
			.uri(uriBuilder -> uriBuilder.path("/api/persons/{id}").build(selectedPerson.getId()))
			.header("Content-Type", "application/json")
			.body(Mono.just(selectedPerson),Person.class)
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.consumeWith(r -> LOGGER.debug("testUpdateUser: response = {}",new String(r.getResponseBody(), StandardCharsets.UTF_8)))
			.jsonPath("$.id").isEqualTo(selectedPerson.getId())
			.jsonPath("$.moniker").isEqualTo(newMoniker);
	}

	@Test
	void testDeleteUserById() {
		int selectedPersonIndex = selectARandomPersonsIndex();
		Person selectedPerson = PERSONS.get(selectedPersonIndex);
		LOGGER.debug("FROM testDeleteUserById: selectedPerson = {}", selectedPerson);
		Mono<Void> voidReturn  = Mono.empty();
		when(service.deleteById(selectedPerson.getId())).thenReturn(voidReturn);
		client
			.delete()
			.uri(uriBuilder -> uriBuilder.path("/api/persons/{id}").build(selectedPerson.getId()))
			.header("Content-Type", "application/json")
			.exchange()
			.expectStatus()
			.isOk();
	}

}
