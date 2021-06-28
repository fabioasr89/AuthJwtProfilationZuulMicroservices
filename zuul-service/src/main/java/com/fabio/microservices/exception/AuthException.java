package com.fabio.microservices.exception;

public class AuthException extends RuntimeException{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4294587192746615283L;
	public String messaggio;
	
	public AuthException() {
		super();
	}
	
	public AuthException(String messaggio) {
		super();
		this.messaggio=messaggio;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	
}
