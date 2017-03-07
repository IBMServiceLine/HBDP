package com.dao;

import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.data.BodyLabsResponse;
import com.data.User;
import com.exception.MeasurementServiceException;
import com.util.CloudantClientManager;

public class UserDaoImpl implements UserDao {

	@Override
	public User getUserDetails(Long userId) throws MeasurementServiceException {

		throw new MeasurementServiceException("not implemented yet");

		// return null;

	}

	@Override
	public Response createUser(BodyLabsResponse bodyLabsResponse)
			throws MeasurementServiceException {
		
		
		Database database = CloudantClientManager.getDB();
		
		return database.post(bodyLabsResponse);

	}

	@Override
	public User updateUser(Long userId, BodyLabsResponse bodyLabsResponse)
			throws MeasurementServiceException {
		throw new MeasurementServiceException("not implemented yet");

		// return null;
	}

}
