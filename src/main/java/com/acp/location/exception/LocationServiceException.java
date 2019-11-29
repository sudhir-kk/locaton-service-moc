package com.acp.location.exception;

import org.springframework.http.HttpStatus;

import com.acp.common.business.exception.BaseBusinessServiceException;

public class LocationServiceException extends BaseBusinessServiceException {
    private static final long serialVersionUID = 1L;

    public LocationServiceException(int applicationCode, int code, HttpStatus status, String exceptionMessage) {
	super(applicationCode, code, status, exceptionMessage);
    }

    public LocationServiceException(int applicationCode, int code, HttpStatus status, String exceptionMessage,
	    Throwable cause) {
	super(applicationCode, code, status, exceptionMessage, cause);
    }
}
