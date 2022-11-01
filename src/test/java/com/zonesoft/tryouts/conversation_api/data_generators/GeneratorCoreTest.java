package com.zonesoft.tryouts.conversation_api.data_generators;

//import static org.junit.jupiter.api.Assertions.*;

import java.time.OffsetDateTime;

import static com.zonesoft.tryouts.conversation_api.data_generators.GeneratorCore.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class GeneratorCoreTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GeneratorCoreTest.class);
	
	@Test
	void testGenerateDate() {
		OffsetDateTime odt = generateDate();
		LOGGER.debug("odt = {}",odt);
	}

	@Test
	void testGenerateDateTime() {
		OffsetDateTime odt = generateDateTime();
		LOGGER.debug("odt = {}",odt);
	}

	void generateExampleName(Gender gender) {
		String firstname = generateFirstName(gender);
		String lastname = generateLastName();
		String[] middlenames = generateMiddleNames(gender);
		LOGGER.debug("firstname = {}, lastname = {}, middlenames = {}, gender = {}",firstname, lastname, middlenames, gender);
	}
	
	@Test
	void testGenerateBoysName() {
		generateExampleName(Gender.MALE);
	}
	
	@Test
	void testGenerateGirlsName() {
		generateExampleName(Gender.FEMALE);
	}
	
	@Test
	void testGeneratePhrase() {
		String phrase = generatePhrase();
		LOGGER.debug("phrase = {}",phrase);
	}
	
	@Test
	void testGenerateNickname() {
		String nickname = generateNickname();
		LOGGER.debug("nickname = {}",nickname);
	}
	
}
