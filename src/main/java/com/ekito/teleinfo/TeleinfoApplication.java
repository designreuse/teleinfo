package com.ekito.teleinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.Mongo;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class TeleinfoApplication {

	/**
	 * entry point
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(TeleinfoApplication.class, args);
	}
	
	// spring configuration
    public @Bean MongoTemplate mongoTemplate(Mongo mongo) throws Exception {
        return new MongoTemplate(mongo, "teleinfo");
    }
}
