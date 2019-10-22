package com.jplunge.pianocatalogservices.service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Service
public class Consumer {
	private final Logger logger = LoggerFactory.getLogger(Consumer.class);
	
	@KafkaListener(topics = "pianos", groupId = "piano_group_id")
	public void consume(String message){
		logger.info(String.format("$$ -> Consumed Message -> %s",message));
	}
	
	//to test another topic (if needed)
	@KafkaListener(topics = "other", groupId = "piano_group_id")
	public void consumeOther(String message){
		logger.info(String.format("$$ -> Consumed (other) Message -> %s",message));
	}
}
