package com.data;

public class BodyLabsRequest {

	private String scheme;

	private String gender;

	private String unitSystem;

	private String size;

	private Measurement measurements;

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUnitSystem() {
		return unitSystem;
	}

	public void setUnitSystem(String unitSystem) {
		this.unitSystem = unitSystem;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Measurement getMeasurements() {
		return measurements;
	}

	public void setMeasurements(Measurement measurements) {
		this.measurements = measurements;
	}

	@Override
	public String toString() {
		return "ClassPojo [scheme = " + scheme + ", gender = " + gender + ", unitSystem = " + unitSystem + ", size = "
				+ size + ", measurements = " + measurements + "]";
	}
}