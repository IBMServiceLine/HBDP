package com.client;

import java.util.logging.Logger;

import com.data.BodyLabsRequest;
import com.data.BodyLabsResponse;
import com.data.Measurement;
import com.exception.BodyLabsServiceException;
import com.google.gson.Gson;
import com.service.BodyLabsService;
import com.service.BodyLabsServiceImpl;

public class BodyLabsClient {

	private static Logger LOGGER = Logger.getLogger(BodyLabsClient.class
			.getName());

	public static void main(String args[]) {
		try {
			testResponse();
		} catch (BodyLabsServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static BodyLabsResponse testResponse()
			throws BodyLabsServiceException {
		BodyLabsRequest user = new BodyLabsRequest();
		user.setGender("male");
		user.setScheme("flexible");
		user.setUnitSystem("metric");
		// user.setSize("US 8");

		Measurement measurements = new Measurement();

		measurements.setHeight(Double.valueOf(170));
		measurements.setWeight(Double.valueOf(95));
		measurements.setBust_girth(Double.valueOf(136));
		measurements.setWaist_girth(Double.valueOf(90));
		measurements.setLow_hip_girth(Double.valueOf(110));

		user.setMeasurements(measurements);
		BodyLabsService bodyLabsService = new BodyLabsServiceImpl();
		BodyLabsResponse bodyLabsResponse = bodyLabsService
				.retrieveMeasurements(user);

		Gson gson = new Gson();

		// Java object to JSON, and assign to a String
		String jsonData = gson.toJson(bodyLabsResponse);

		LOGGER.info(jsonData);
		return bodyLabsResponse;
	}

}
