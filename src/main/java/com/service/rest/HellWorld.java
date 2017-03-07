package com.service.rest;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.data.BodyLabsResponse;
import com.exception.MeasurementServiceException;

@Path("/hello")
public class HellWorld {
	private static Logger LOGGER = Logger.getLogger(HellWorld.class.getName());

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {

		LOGGER.info("called getMsg ==> " + msg);
//		Database database = CloudantClientManager.getDB();
//		String id = "";
//		try {
//			id = database.post(BodyLabsClient.testResponse()).getId();
//		} catch (BodyLabsServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		String output = "Add document with ID ";

		return Response.status(200).entity(output).build();

	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/test")
	public Response getMsg() {

		String output = "working !!";

		return Response.status(200).entity(output).build();

	}

	// http://localhost:8080/Jersey-JSON-IO/rest/bookservice/getbook/10001
	@GET
	@Path("/getresponse")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public BodyLabsResponse getResponse() throws MeasurementServiceException {

		throw new MeasurementServiceException("User does not exist with id ");

		// return BodyLabsClient.testResponse();
	}

}