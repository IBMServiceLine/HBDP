package com.service;

import com.data.BodyLabsRequest;
import com.data.BodyLabsResponse;
import com.exception.BodyLabsServiceException;

public interface BodyLabsService {

	BodyLabsResponse retrieveMeasurements(BodyLabsRequest bodyLabsRequest) throws BodyLabsServiceException;

}
