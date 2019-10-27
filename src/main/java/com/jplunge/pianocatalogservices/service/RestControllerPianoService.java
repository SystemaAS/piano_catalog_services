package com.jplunge.pianocatalogservices.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
//import org.springframework.hateoas.Link;
//import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.jplunge.pianocatalogservices.repository.PianoDao;
import com.jplunge.pianocatalogservices.service.kafka.KafkaService;
import com.jplunge.pianocatalogservices.model.PianoItem;
import com.jplunge.pianocatalogservices.model.SignInDto;

//@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
//@CrossOrigin(origins = { "*" }) //React port --> Check this at a global level: WebConfig
@RestController
public class RestControllerPianoService {
	private final Logger logger = LoggerFactory.getLogger(RestControllerPianoService.class);
	
	
	@Autowired
	PianoDao pianoDao;
	
	//@Autowired
	//KafkaService kafkaService;
	
	
	//Not working since spring boot 2.2
	/*
	@RequestMapping(value = "/pianosHO", produces = "application/hal+json")
	public List<PianoItem> retrieveAllPianosHateOas() {
		List<PianoItem> list = new ArrayList<PianoItem>();
		
		
		for (PianoItem piano : pianoDao.findAll()){
			//Adding self link employee 'singular' resource
	        Link link = ControllerLinkBuilder
	                .linkTo(RestControllerPianoService.class)
	                .slash(piano.getPianoId())
	                .withSelfRel();
	 
	        //Add link to singular resource
	        piano.add(link);
            list.add(piano);
		}
		return list;
	}
	*/
	
	@RequestMapping(value = "/pianos", produces = "application/json")
	public List<PianoItem> retrieveAllPianos() {
		//test system property that we configured globally in the application start
		logger.info("Testing system property:" + System.getProperty("app.type"));
		//fetch all
		List<PianoItem> list = pianoDao.findAll();
		
		/*for (PianoItem piano: list){
			kafkaService.sendMessageToKafkaTopic("record fetched: " + piano.getName() + " " + piano.getModel());
		}*/
		return  list;
			
	}
	
	@RequestMapping(value = "/pianos", params = { "id" }, produces = "application/json")
	public PianoItem retrieveSpecificPiano(@RequestParam int id) {
		return pianoDao.findById(id);
	}
	
	@RequestMapping(value = "/pianos/add", params = { "name", "model", "text" }, produces = "application/json")
	public List<PianoItem> addPiano(@RequestParam String name, @RequestParam String model, @RequestParam String text) {
		PianoItem item = new PianoItem();
		item.setName(name);
		item.setText(text);
		item.setModel(model);
		logger.info(item.getName() + "_" + item.getText() + "_" + item.getModel());
		
		int returnValue = pianoDao.add(item);
		
		return pianoDao.findAll();
	}
	
	@RequestMapping(value = "/pianos/update", params = { "id","text","model","name" }, produces = "application/json")
	public List<PianoItem> updatePiano(@RequestParam int id, @RequestParam String text, @RequestParam String model, @RequestParam String name ) {
		PianoItem item = new PianoItem();
		item.setPianoId(id);
		item.setText(text);
		item.setModel(model);
		item.setName(name);
		
		
		int returnValue = pianoDao.update(item);
		
		return pianoDao.findAll();
	}
	
	@RequestMapping(value = "/pianos/delete", params = { "id" }, produces = "application/json")
	public List<PianoItem> deletePiano(@RequestParam int id) {
		PianoItem item = new PianoItem();
		item.setPianoId(id);
		
		int returnValue = pianoDao.delete(item);
		
		return pianoDao.findAll();
	}
}
