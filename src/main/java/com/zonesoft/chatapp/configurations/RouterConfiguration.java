package com.zonesoft.chatapp.configurations;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.zonesoft.chatapp.handlers.PersonHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class RouterConfiguration {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RouterConfiguration.class);

    @Bean
	public RouterFunction<ServerResponse> routes(PersonHandler handler) {
    	LOGGER.debug("From RouterConfiguration.routes");
        return route(GET("/router/persons").and(accept(MediaType.APPLICATION_JSON)), handler::findAll)
                .andRoute(GET("/router/persons/{id}").and(contentType(MediaType.APPLICATION_JSON)), handler::findById)
                .andRoute(POST("/router/persons").and(accept(MediaType.APPLICATION_JSON)), handler::insert)
                .andRoute(PUT("/router/persons/{id}").and(contentType(MediaType.APPLICATION_JSON)), handler::update)
                .andRoute(DELETE("/router/persons/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::deleteById);
    }
}