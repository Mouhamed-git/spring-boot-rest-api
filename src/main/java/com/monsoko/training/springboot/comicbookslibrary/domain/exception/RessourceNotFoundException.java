package com.monsoko.training.springboot.comicbookslibrary.domain.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class RessourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7082444789010185978L;

	public RessourceNotFoundException(String message) {
        super(message);
    }
    
    public RessourceNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
