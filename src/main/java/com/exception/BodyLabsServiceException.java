/**
 * 
 */
package com.exception;

import java.io.Serializable;

/**
 * @author pshandil
 *
 */
public class BodyLabsServiceException extends Exception implements Serializable {

	private static final long serialVersionUID = 1169426381288170661L;

	public BodyLabsServiceException() {
		super();
	}

	public BodyLabsServiceException(String msg) {
		super(msg);
	}

	public BodyLabsServiceException(String msg, Exception e) {
		super(msg, e);
	}

}