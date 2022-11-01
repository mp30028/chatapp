package com.zonesoft.chatapp.repositories;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.zonesoft.chatapp.models.Conversation;

@Repository
public interface ConversationRepository extends ReactiveMongoRepository<Conversation, String> {

}
