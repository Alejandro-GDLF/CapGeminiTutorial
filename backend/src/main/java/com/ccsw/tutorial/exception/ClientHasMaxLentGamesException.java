package com.ccsw.tutorial.exception;

public class ClientHasMaxLentGamesException extends Exception{
	public ClientHasMaxLentGamesException () {
		super("Clients can not have two games lent at once.");
	}
}
