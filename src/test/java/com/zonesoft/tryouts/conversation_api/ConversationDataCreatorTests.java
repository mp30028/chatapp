package com.zonesoft.tryouts.conversation_api;


import static com.zonesoft.tryouts.conversation_api.data_generators.GeneratorCore.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zonesoft.chatapp.ChatApplication;
import com.zonesoft.chatapp.models.Conversation;
import com.zonesoft.chatapp.models.Person;
import com.zonesoft.chatapp.repositories.ConversationRepository;
import com.zonesoft.chatapp.repositories.PersonRepository;

import reactor.core.publisher.Mono;

@Disabled
@SpringBootTest(classes = ChatApplication.class)
class ConversationDataCreatorTests {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConversationDataCreatorTests.class);
	
	@Autowired ConversationRepository repository;
	@Autowired PersonRepository personRepository;
	
	private Person createPersonData() {
		Person person = new Person(generateNickname(), generateFirstName(generateGender()), generateLastName());
		Mono<Person> createdPerson =  personRepository.save(person);
		return createdPerson.block();
	}
	

	private void createConversationData() {
		final int MAX_PARTICIPANTS = 7;
		final int MIN_PARTICIPANTS = 2;
		final int MIN_MESSAGES = 5;
		final int MAX_MESSAGES = 10;
		Conversation conversation = new Conversation();
		int numberOfParticipants = generateRandomInt(MIN_PARTICIPANTS, MAX_PARTICIPANTS);
		for (int j=0; j < numberOfParticipants; j++) {
			conversation.getParticipants().add(conversation.new Participant(createPersonData()));
		}
		
		int numberOfMessages = generateRandomInt(MIN_MESSAGES, MAX_MESSAGES);
		for(int j=0; j < numberOfMessages; j++) {
			int senderIndex = generateRandomInt(0, numberOfParticipants-1);
			conversation.getMessages().add(conversation.new Message(conversation.getParticipants().get(senderIndex),generatePhrase()));
		}
		Conversation savedConversation = repository.save(conversation).block();
		LOGGER.info("Conversation = {}", savedConversation);
	}
	
	@Test
	void createData() {
		final int MAX_CONVERSATIONS = 5;
		for (int j=0; j < MAX_CONVERSATIONS; j++) {
			createConversationData();
		}
	}

}
