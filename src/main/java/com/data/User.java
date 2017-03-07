/**
 * 
 */
package com.data;

/**
 * @author pshandil
 *
 */
public class User {

	String _id;

	BodyLabsResponse details;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public BodyLabsResponse getDetails() {
		return details;
	}

	public void setDetails(BodyLabsResponse details) {
		this.details = details;
	}

}