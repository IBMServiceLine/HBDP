package com.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public final class BodyLabsServiceHandler implements
		ExceptionMapper<MeasurementServiceException> {

	@Override
	public Response toResponse(final MeasurementServiceException exception) {
		return Response.status(Status.BAD_REQUEST)
				.entity(new ErrorMessage(exception.getMessage()))
				.type(MediaType.APPLICATION_JSON).build();
	}

	public class ErrorMessage {
		private String error;

		public ErrorMessage(String error) {
			this.error = error;
		}

		public String getError() {
			return error;
		}
	}
}
