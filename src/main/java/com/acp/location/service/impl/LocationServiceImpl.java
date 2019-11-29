package com.acp.location.service.impl;

/**
 * @author s.a.kumar.kushwaha
 *
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.acp.common.business.constants.ResponseConstants;
import com.acp.common.business.response.CommonBaseResponse;
import com.acp.common.business.service.BaseService;
import com.acp.location.constants.LocationConstants;
import com.acp.location.entities.LocationEntity;
import com.acp.location.exception.LocationServiceException;
import com.acp.location.repositories.LocationRepository;
import com.acp.location.request.Location;
import com.acp.location.requesttransformer.LocationRequestTransformer;
import com.acp.location.response.LocationResponse;
import com.acp.location.responsetransformer.LocationResponseTransformer;
import com.acp.location.service.LocationService;

@Service
public class LocationServiceImpl extends BaseService implements LocationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocationServiceImpl.class);
	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private LocationResponseTransformer locationResponseTransformer;
	@Autowired
	private LocationRequestTransformer locationRequestTransformer;

	@Override
	public LocationResponse retrieveLocations(Boolean flag) throws LocationServiceException {
		LOGGER.info("Entered into retrieveLocations() method");
		final Map<String, Object> response = new HashMap<String, Object>();

		final List<LocationEntity> locationList;

		try {
			if (flag) {

				locationList = locationRepository.findAll();
			} else {

				locationList = locationRepository.findByLocationIsActiveTrue();
			}
			if (locationList == null || locationList.isEmpty()) {
				setReturnStatusAndMessage(HttpStatus.FAILED_DEPENDENCY, LocationConstants.NO_ACTIVE_LOCATION_MSG,
						response);
				return locationResponseTransformer.transformList(response);
			}
			setReturnStatusAndMessage(HttpStatus.OK, locationList, response);
		} catch (final Exception e) {

			throw new LocationServiceException(ResponseConstants.APPLICATION_CODE, HttpStatus.FAILED_DEPENDENCY.value(),
					HttpStatus.FAILED_DEPENDENCY, LocationConstants.FINDALL_FAILURE_MSG, e);
		}

		return locationResponseTransformer.transformList(response);
	}
  
	@Override
	public CommonBaseResponse retrieveActiveLocationNames() throws LocationServiceException {
		final List<Object[]> locationList;
		Map<Integer, String> result = null;
		CommonBaseResponse response = new CommonBaseResponse();
		try {
			locationList = locationRepository.findAllLocationNames(Boolean.TRUE);
			if (locationList == null || locationList.isEmpty()) {
				response.setApplicationCode(ResponseConstants.APPLICATION_CODE);
				response.setCode(HttpStatus.FAILED_DEPENDENCY.value());
				response.setStatus(HttpStatus.FAILED_DEPENDENCY);
				response.setMessage(LocationConstants.NO_ACTIVE_LOCATION_MSG);
				return response;
			}
			result = new HashMap<Integer, String>();
 
			response.setApplicationCode(ResponseConstants.APPLICATION_CODE);
			response.setStatus(HttpStatus.OK);
			response.setCode(HttpStatus.OK.value());
			if (!CollectionUtils.isEmpty(locationList)) {
				for (final Object[] location : locationList) {
					result.put((Integer) location[0], (String) location[1]);
				}
			} 
		} catch (final Exception e) {
			throw new LocationServiceException(ResponseConstants.APPLICATION_CODE, HttpStatus.FAILED_DEPENDENCY.value(),
					HttpStatus.FAILED_DEPENDENCY, LocationConstants.FINDALL_FAILURE_MSG, e);
		}

		response.setData(result);
		return response;

	}

	@Override
	public LocationResponse retrieveByLocationId(Integer locationId) {
		final Map<String, Object> response = new HashMap<String, Object>();
		final LocationEntity location = locationRepository.findOne(locationId);
		if (location == null) {
			setReturnStatusAndMessage(HttpStatus.PRECONDITION_FAILED, LocationConstants.PRECONDITION_FAILURE_MSG,
					response);
			return locationResponseTransformer.transformList(response);
		}
		final List<LocationEntity> locationList = new ArrayList<LocationEntity>();
		locationList.add(location);
		setReturnStatusAndMessage(HttpStatus.OK, locationList, response);
		return locationResponseTransformer.transformList(response);
	}

	@Override
	public LocationResponse retrieveByLocationName(String locationName) {

		final Map<String, Object> response = new HashMap<String, Object>();
		final LocationEntity location = locationRepository.findByLocationName(locationName);
		if (location == null) {
			setReturnStatusAndMessage(HttpStatus.PRECONDITION_FAILED, LocationConstants.PRECONDITION_FAILURE_MSG,
					response);
			return locationResponseTransformer.transformList(response);
		}
		final List<LocationEntity> locationList = new ArrayList<LocationEntity>();
		locationList.add(location);
		setReturnStatusAndMessage(HttpStatus.OK, locationList, response);
		return locationResponseTransformer.transformList(response);

	}

	@Override
	public LocationResponse createLocation(Location locationRequest) throws LocationServiceException {
		LOGGER.debug("Entered create() method - In LocationServiceImpl");

		final Map<String, Object> response = new HashMap<String, Object>();
		final LocationEntity location = locationRequestTransformer.transformLocationRequestParam(locationRequest);
		try {
			location.setCreatedBy(locationRequest.getLoggedInUser());
			location.setLastUpdatedBy(locationRequest.getLoggedInUser());
			final LocationEntity existingLocation = locationRepository.findByLocationName(location.getLocationName());

			if (existingLocation != null) {
				setReturnStatusAndMessage(HttpStatus.PRECONDITION_FAILED, LocationConstants.LOCATION_EXISTS_MSG,
						response);
				return locationResponseTransformer.transformList(response);
			}

			locationRepository.saveAndFlush(location);
		} catch (final Exception e) {
			throw new LocationServiceException(ResponseConstants.APPLICATION_CODE, HttpStatus.FAILED_DEPENDENCY.value(),
					HttpStatus.FAILED_DEPENDENCY, LocationConstants.SAVE_FLUSH_FAILURE_MSG, e);
		}
		final List<LocationEntity> locationList = new ArrayList<LocationEntity>();
		locationList.add(location);
		setReturnStatusAndMessage(HttpStatus.CREATED, locationList, response);
		return locationResponseTransformer.transformList(response);

	}

	@Override
	public LocationResponse deleteLocation(Integer locationId, String loggedInUser) throws LocationServiceException {

		final Map<String, Object> response = new HashMap<String, Object>();
		if (locationId == null) {
			setReturnStatusAndMessage(HttpStatus.PRECONDITION_FAILED, LocationConstants.LOCATION_ID_EMPTY_MSG,
					response);
			return locationResponseTransformer.transformList(response);
		}
		try {
		final LocationEntity existingLocation = locationRepository.findOne(locationId);
		if (existingLocation == null) {
			setReturnStatusAndMessage(HttpStatus.PRECONDITION_FAILED, LocationConstants.PRECONDITION_FAILURE_MSG,
					response);
			return locationResponseTransformer.transformList(response);
		}
		existingLocation.setLocationIsActive(false);
		existingLocation.setLastUpdatedBy(loggedInUser);
		locationRepository.saveAndFlush(existingLocation);
		final List<LocationEntity> locationList = new ArrayList<LocationEntity>();
		locationList.add(existingLocation);
		setReturnStatusAndMessage(HttpStatus.OK, locationList, response);
		
		} catch (final Exception e) {
			throw new LocationServiceException(ResponseConstants.APPLICATION_CODE, HttpStatus.FAILED_DEPENDENCY.value(),
					HttpStatus.FAILED_DEPENDENCY, LocationConstants.SAVE_FLUSH_FAILURE_MSG, e);
		}
		
		return locationResponseTransformer.transformList(response);

	}

	@Override
	public LocationResponse updateLocation(Location locationRequest, Integer locationId)throws LocationServiceException {
		LOGGER.info("From LocationServiceImpl.Update() - execution started ");
		final Map<String, Object> response = new HashMap<String, Object>();

		if (locationId == null) {
			setReturnStatusAndMessage(HttpStatus.PRECONDITION_FAILED, LocationConstants.LOCATION_ID_EMPTY_MSG,
					response);
			return locationResponseTransformer.transformList(response);
		}

		try {
			final LocationEntity existingLocation = locationRepository.findOne(locationId);
			if (existingLocation == null) {
				setReturnStatusAndMessage(HttpStatus.PRECONDITION_FAILED, LocationConstants.PRECONDITION_FAILURE_MSG,
						response);
				return locationResponseTransformer.transformList(response);
			}

			final LocationEntity location = locationRequestTransformer.transformLocationRequestParam(locationRequest);

			location.setLocationId(locationId);
			BeanUtils.copyProperties(location, existingLocation);
			existingLocation.setLastUpdatedBy(locationRequest.getLoggedInUser());
			locationRepository.saveAndFlush(existingLocation);
			final List<LocationEntity> locationList = new ArrayList<LocationEntity>();
			locationList.add(existingLocation);
			setReturnStatusAndMessage(HttpStatus.OK, locationList, response);
		} catch (final Exception e) {
			throw new LocationServiceException(ResponseConstants.APPLICATION_CODE, HttpStatus.FAILED_DEPENDENCY.value(),
					HttpStatus.FAILED_DEPENDENCY, LocationConstants.SAVE_FLUSH_FAILURE_MSG, e);
		}
		return locationResponseTransformer.transformList(response);

	}
}
