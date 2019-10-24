package com.jplunge.pianocatalogservices.service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaService {
	/*
	private final Producer producer;
	
	@Autowired
	public KafkaService(Producer producer) {
	this.producer = producer;
	}
	
	@GetMapping(value = "/publish")
	public void sendMessageToKafkaTopic(@RequestParam("message") String message){
	this.producer.sendMessage(message);
	}
	*/
}




