package com.zonesoft.chatapp.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zonesoft.chatapp.models.Person;
import com.zonesoft.chatapp.services.PersonService;

//import java.time.Duration;
//import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
//import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    
	private final PersonService service;
    
    @Autowired
    public PersonController(PersonService service) {
    	super();
    	this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Person> insert(@RequestBody Person person){
        return service.insert(person);
    }

    @GetMapping
    public Flux<Person> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Person>> findById(@PathVariable String id){
        Mono<Person> personMono = service.findById(id);
        return personMono.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Person>> updateUser(@PathVariable String id, @RequestBody Person person){
        return service.update(person)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUserById(@PathVariable String id){
        return service.deleteById(id)
                .map( r -> ResponseEntity.ok().<Void>build());
//                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

//    @GetMapping("/search")
//    public Flux<Person> searchUsers(@RequestParam("name") String name) {
//        return service.fetchUsers(name);
//    }

//    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<Person> streamAllUsers() {
//        return service
//                .findAll()
//                .flatMap(person -> Flux
//                        .zip(Flux.interval(Duration.ofSeconds(2)),
//                                Flux.fromStream(Stream.generate(() -> person))
//                        )
//                        .map(Tuple2::getT2)
//                );
//    }
}
