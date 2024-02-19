package com.ccsw.tutorial.exception;

public class GameLentClientContraintException extends Exception{
	public GameLentClientContraintException() {
		super("Game can not be lent to two clients at once.");
	}
}
