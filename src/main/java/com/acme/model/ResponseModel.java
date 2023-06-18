package com.acme.model;

/**
 * The model defines a common response model
 */
public class ResponseModel {
	protected int statusCode;
	protected String message;
	
	public ResponseModel() {
		super();
	}
	
	public ResponseModel(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}	
	
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
