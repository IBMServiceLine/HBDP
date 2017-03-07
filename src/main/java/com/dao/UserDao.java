package com.dao;

import com.data.User;
import com.exception.MeasurementServiceException;

public interface UserDao {

	User createUser(User newUser) throws MeasurementServiceException;

	User updateUser(User user) throws MeasurementServiceException;

	User getUserDetails(String apiUserId) throws MeasurementServiceException;

}