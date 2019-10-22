package com.jplunge.pianocatalogservices.util;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class OwnConfigurator {
    @PostConstruct
    public void setProperty() {
       System.setProperty("app.type", "test");
    }
}