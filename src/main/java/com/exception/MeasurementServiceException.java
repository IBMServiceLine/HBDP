/**
 * 
 */
package com.exception;

import java.io.Serializable;

/**
 * @author pshandil
 *
 */
public class MeasurementServiceException extends Exception implements Serializable {

	private static final long serialVersionUID = 1169426381288170661L;

	public MeasurementServiceException() {
		super();
	}

	public MeasurementServiceException(String msg) {
		super(msg);
	}

	public MeasurementServiceException(String msg, Exception e) {
		super(msg, e);
	}

}