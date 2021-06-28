package com.fabio.microservices.response;

public class JsonResponse {

	public Object response;
	
	public boolean successo;
	
	public String messaggio;

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public boolean isSuccesso() {
		return successo;
	}

	public void setSuccesso(boolean successo) {
		this.successo = successo;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	
	
}
