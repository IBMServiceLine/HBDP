package com.service.rest;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

	private static Logger LOGGER = Logger.getLogger(MeasurementService.class.getName());

	/**
	 * Restful web service method to create user in the HBDP application.
	 * 
	 * @param newUser
	 *            the application user details.
	 * @return
	 * @throws MeasurementServiceException
	 * @throws BodyLabsServiceException
	 */
	@POST
	@Path("createUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User createUser(User newUser) throws MeasurementServiceException, BodyLabsServiceException {
		LOGGER.info("START: createUser(user id = " + newUser.get_id() + ")");

		// Get measurements from body lab
		BodyLabsResponse bodyLabsResponse = labsService.retrieveMeasurements(newUser.getDetails().getInput());
		newUser.setDetails(bodyLabsResponse);

		LOGGER.info("END: createUser() ==> user id = " + newUser.get_id());

		// Create user in the app with the details retrieved from body lab.
		return userDao.createUser(newUser);

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
	public User updateUser(User user) throws MeasurementServiceException, BodyLabsServiceException {

		// Get measurements from body lab
		BodyLabsResponse bodyLabsResponse = labsService.retrieveMeasurements(user.getDetails().getInput());
		user.setDetails(bodyLabsResponse);

		// Create user in the app with the details retrieved from body lab.
		// return userDao.updateUser(user);
		return user;
	}

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws MeasurementServiceException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("findUser/{apiUserId}")
	public User findUser(@PathParam("apiUserId") String apiUserId) throws MeasurementServiceException {

		// Get user details stored in the app.
		return userDao.getUserDetails(apiUserId);

	}
}