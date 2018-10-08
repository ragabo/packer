package com.mobiquityinc.exception;

public class APIException extends Exception {
	
	private static final long serialVersionUID = 5199629890017767754L;

	public APIException(String message){
		super(message);
	}
	
	public APIException(String errorMessage, Throwable err){
		super(errorMessage, err);
	}
}
