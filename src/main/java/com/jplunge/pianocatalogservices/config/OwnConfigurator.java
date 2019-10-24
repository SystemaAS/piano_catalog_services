package com.jplunge.pianocatalogservices.config;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//@Component
@Configuration
public class OwnConfigurator {
	
    @PostConstruct
    public void setProperty() {
       System.setProperty("app.type", "test");
    }
    
    
    
}