package com.nezztech.apuesta.websocket.config;

import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class config {
	
	@Value("${websocket.url}")
     private String url;
	
	
	 @Bean
	    public URI webSocketUri() {
	        return URI.create(url);
	    }

}
