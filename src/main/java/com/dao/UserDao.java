package com.dao;

import com.cloudant.client.api.model.Response;
import com.data.BodyLabsResponse;
import com.data.User;
import com.exception.MeasurementServiceException;

public interface UserDao {

	User getUserDetails(Long userId) throws MeasurementServiceException;

	Response createUser(BodyLabsResponse bodyLabsResponse) throws MeasurementServiceException;

	User updateUser(Long userId, BodyLabsResponse bodyLabsResponse) throws MeasurementServiceException;

}
