package com.ccsw.tutorial.exception;

public class NameAlreadyExistsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NameAlreadyExistsException() {
		super("Client name already exists");
	}

}
