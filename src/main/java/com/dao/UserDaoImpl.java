package com.dao;

import java.util.logging.Logger;

import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.data.User;
import com.exception.MeasurementServiceException;
import com.util.CloudantClientManager;

public class UserDaoImpl implements UserDao {

	private static Logger LOGGER = Logger.getLogger(UserDaoImpl.class.getName());

	@Override
	public User getUserDetails(String apiUserId) throws MeasurementServiceException {

		Database database = CloudantClientManager.getDB();

		if (database.contains(apiUserId)) {
			return database.find(User.class, apiUserId);
		} else {
			String msg = "Error while finding user --> User does not exists with ID: " + apiUserId;
			LOGGER.info(msg);
			throw new MeasurementServiceException(msg);
		}

	}

	@Override
	public User createUser(User newUser) throws MeasurementServiceException {

		Database database = CloudantClientManager.getDB();

		Response response = database.save(newUser);

		LOGGER.info("CREATE USER --> RESPONSE CODE =  " + response.getStatusCode());
		LOGGER.info("CREATE USER --> ERROR =  " + response.getError());
		LOGGER.info("CREATE USER --> REASON =  " + response.getReason());
		LOGGER.info("CREATE USER --> ID =  " + response.getId());

		if (response.getStatusCode() != 201) {
			String msg = "Error while creating api user ==> " + response.getStatusCode() + " : " + response.getReason();
			throw new MeasurementServiceException(msg);
		}

		return getUserDetails(response.getId());

	}

	@Override
	public User updateUser(User user) throws MeasurementServiceException {
		Database database = CloudantClientManager.getDB();

		if (database.contains(user.get_id())) {
			Response response = database.update(user);

			LOGGER.info("UPDATE USER --> RESPONSE CODE =  " + response.getStatusCode());
			LOGGER.info("UPDATE USER --> ERROR =  " + response.getError());
			LOGGER.info("UPDATE USER --> REASON =  " + response.getReason());
			LOGGER.info("UPDATE USER --> ID =  " + response.getId());

			if (response.getStatusCode() != 201) {
				String msg = "Error while updating api user ==> " + response.getStatusCode() + " : "
						+ response.getReason();
				throw new MeasurementServiceException(msg);
			}

			return getUserDetails(user.get_id());
		} else {
			String msg = "Error while finding user --> User does not exists with ID: " + user.get_id();
			LOGGER.info(msg);
			throw new MeasurementServiceException(msg);
		}

	}
}