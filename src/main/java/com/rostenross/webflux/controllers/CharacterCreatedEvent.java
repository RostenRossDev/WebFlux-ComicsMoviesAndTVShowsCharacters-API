package com.rostenross.webflux.controllers;

import org.springframework.context.ApplicationEvent;

import com.rostenross.webflux.model.Character;

public class CharacterCreatedEvent extends ApplicationEvent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6683950424504917286L;

	public CharacterCreatedEvent(Character charac) {
		super(charac);
	}
}
