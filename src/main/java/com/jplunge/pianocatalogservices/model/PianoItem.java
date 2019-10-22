package com.jplunge.pianocatalogservices.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

//ResourceSupport must be extended for supporting HATEOAS. This collides with lombok unfortunately
public class PianoItem extends ResourceSupport {
	@Id
	@GeneratedValue
	private int pianoId;
	private String name;
	private String text;
	private String model;
	
	public int getPianoId() {
		return pianoId;
	}
	public void setPianoId(int pianoId) {
		this.pianoId = pianoId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	
}
