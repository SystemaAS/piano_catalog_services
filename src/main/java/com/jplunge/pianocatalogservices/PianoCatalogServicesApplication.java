package com.jplunge.pianocatalogservices;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jplunge.pianocatalogservices.config.OwnConfigurator;
import com.jplunge.pianocatalogservices.config.WebConfig;

@SpringBootApplication
public class PianoCatalogServicesApplication {

	@Autowired //to load some own configuration (just as an example ...)
	OwnConfigurator config;
	
	@Autowired //to load CORS (CrossOrigin in a global manner) ... NOT WORKING
	WebConfig webConfig;
	
	
	public static void main(String[] args) {
		SpringApplication.run(PianoCatalogServicesApplication.class, args);
	}

}
