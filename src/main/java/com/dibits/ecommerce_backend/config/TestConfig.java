package com.dibits.ecommerce_backend.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.dibits.ecommerce_backend.services.DBService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dbservice;
	
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		dbservice.InstantiateTestDatabase();
		return true;
	}

}
