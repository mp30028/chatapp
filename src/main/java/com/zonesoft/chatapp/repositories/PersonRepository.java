package com.zonesoft.chatapp.repositories;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.zonesoft.chatapp.models.Person;

@Repository
public interface PersonRepository extends ReactiveMongoRepository<Person, String> {

}
