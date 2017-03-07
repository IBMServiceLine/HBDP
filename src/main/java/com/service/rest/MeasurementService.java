package com.service.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cloudant.client.api.model.Response;
import com.dao.UserDao;
import com.dao.UserDaoImpl;
import com.data.BodyLabsResponse;
import com.data.User;
import com.exception.BodyLabsServiceException;
import com.exception.MeasurementServiceException;
import com.service.BodyLabsService;
import com.service.BodyLabsServiceImpl;

@Path("/MeasurementService")
public class MeasurementService {

	BodyLabsService labsService = new BodyLabsServiceImpl();

	UserDao userDao = new UserDaoImpl();

	/**
	 * Restful web service method to create user in the HBDP application.
	 * 
	 * @param user
	 *            the application user details.
	 * @return
	 * @throws MeasurementServiceException
	 * @throws BodyLabsServiceException
	 */
	@POST
	@Path("createUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(User user)
			throws MeasurementServiceException, BodyLabsServiceException {

		// Get measurements from body lab
		BodyLabsResponse bodyLabsResponse = labsService
				.retrieveMeasurements(user.getDetails().getInput());

		// Create user in the app with the details retrieved from body lab.
		return userDao.createUser(bodyLabsResponse);

	}

	/**
	 * Restful web service method to update user details in the HBDP
	 * application.
	 * 
	 * @param user
	 * @return
	 * @throws MeasurementServiceException
	 * @throws BodyLabsServiceException
	 */
	@POST
	@Path("updateUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BodyLabsResponse updateUser(User user)
			throws MeasurementServiceException, BodyLabsServiceException {

		// Check if the user exists.
		userDao.getUserDetails(user.getUserId());

		// Get measurements from body lab
		BodyLabsResponse bodyLabsResponse = labsService
				.retrieveMeasurements(user.getDetails().getInput());

		// Create user in the app with the details retrieved from body lab.
		return userDao.updateUser(user.getUserId(), bodyLabsResponse)
				.getDetails();
	}

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws MeasurementServiceException
	 */
	@POST
	@Path("retrieveMeasurements")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BodyLabsResponse retrieveMeasurements(Long userId)
			throws MeasurementServiceException {

		// Get user details stored in the app.
		return userDao.getUserDetails(userId).getDetails();

	}
}