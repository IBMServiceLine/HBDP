package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.data.BodyLabsRequest;
import com.data.BodyLabsResponse;
import com.data.Measurement;
import com.data.User;
import com.exception.BodyLabsServiceException;
import com.google.gson.Gson;

public class Testclient {

	private static String BODY_LABS_MEASUMENTS_URL = "http://localhost:8080/rest/MeasurementService/createUser";

	private static Logger LOGGER = Logger.getLogger(Testclient.class.getName());

	public static void main(String[] args) {
		Testclient client = new Testclient();

		try {
			client.testCreateUser();
		} catch (BodyLabsServiceException e) {
			e.printStackTrace();
		}
	}

	public BodyLabsResponse testCreateUser() throws BodyLabsServiceException {

		BODY_LABS_MEASUMENTS_URL = "http://localhost:8080/rest/MeasurementService/createUser";

		HttpPost httpPost = connect();

		BodyLabsRequest input = new BodyLabsRequest();
		input.setGender("male");
		input.setScheme("flexible");
		input.setUnitSystem("metric");
		// user.setSize("US 8");

		Measurement measurements = new Measurement();

		measurements.setHeight(Double.valueOf(170));
		measurements.setWeight(Double.valueOf(95));
		measurements.setBust_girth(Double.valueOf(136));
		measurements.setWaist_girth(Double.valueOf(90));
		measurements.setLow_hip_girth(Double.valueOf(110));

		input.setMeasurements(measurements);

		BodyLabsResponse response = new BodyLabsResponse();

		response.setInput(input);

		User user = new User();

		user.setDetails(response);

		return execute(user, httpPost);
	}

	/**
	 * 
	 * @return
	 */
	private HttpPost connect() {
		HttpPost post = new HttpPost(BODY_LABS_MEASUMENTS_URL);
		// String auth = new StringBuffer("SecretPair ").append("accesskey=")
		// .append(ACCESS_KEY).append(",").append("secret=")
		// .append(SECRET).toString();
		// post.setHeader("Authorization", auth);
		post.setHeader("Content-Type", "application/json");
		post.setHeader("Accept", "application/json");
		post.setHeader("X-Stream", "true");
		return post;
	}

	/**
	 * 
	 * @param bodyDetails
	 * @param httpPost
	 * @return
	 * @throws BodyLabsServiceException
	 */
	private BodyLabsResponse execute(User user, HttpPost httpPost) {
		try {
			return executeHttpRequest(user, httpPost);

		} catch (UnsupportedEncodingException e) {
			LOGGER.info("Error while encoding api url : " + e);
		} catch (IOException e) {
			LOGGER.info("ioException occured while sending http request : " + e);
		} catch (Exception e) {
			LOGGER.info("Exception occured while sending http request : " + e);
		} finally {
			httpPost.releaseConnection();
		}

		return null;
	}

	/**
	 * 
	 * @param bodyDetails
	 * @param httpPost
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private BodyLabsResponse executeHttpRequest(User user, HttpPost httpPost)
			throws UnsupportedEncodingException, IOException {

		Gson gson = new Gson();

		// Java object to JSON, and assign to a String
		String jsonData = gson.toJson(user);

		HttpResponse response = null;
		String line = "";
		StringBuffer result = new StringBuffer();
		httpPost.setEntity(new StringEntity(jsonData));
		HttpClient client = HttpClientBuilder.create().build();
		response = client.execute(httpPost);
		LOGGER.info("Post parameters : " + jsonData);
		LOGGER.info("Response Code : "
				+ response.getStatusLine().getStatusCode());

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				response.getEntity().getContent()));
		while ((line = reader.readLine()) != null) {
			result.append(line);
		}

		BodyLabsResponse responseData = gson.fromJson(result.toString(),
				BodyLabsResponse.class);

		return responseData;
	}
}
