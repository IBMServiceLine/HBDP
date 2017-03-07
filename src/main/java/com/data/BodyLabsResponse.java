/**
 * 
 */
package com.data;

/**
 * @author pshandil
 *
 */
public class BodyLabsResponse {
	private BodyLabsRequest input;

	private Output output;

	public BodyLabsRequest getInput() {
		return input;
	}

	public void setInput(BodyLabsRequest input) {
		this.input = input;
	}

	public Output getOutput() {
		return output;
	}

	public void setOutput(Output output) {
		this.output = output;
	}

	@Override
	public String toString() {
		return "ClassPojo [input = " + input + ", output = " + output + "]";
	}

	public class Output {
		private Measurement measurements;

		public Measurement getMeasurements() {
			return measurements;
		}

		public void setMeasurements(Measurement measurements) {
			this.measurements = measurements;
		}

		@Override
		public String toString() {
			return "ClassPojo [measurements = " + measurements + "]";
		}
	}

}
