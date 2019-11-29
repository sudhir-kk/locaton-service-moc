package com.acp.location.service;

/**
 * @author s.a.kumar.kushwaha
 *
 */
import com.acp.common.business.response.CommonBaseResponse;
import com.acp.location.exception.LocationServiceException;
import com.acp.location.request.Location;
import com.acp.location.response.LocationResponse;

public interface LocationService {

    LocationResponse retrieveLocations(Boolean flag) throws LocationServiceException;

    CommonBaseResponse retrieveActiveLocationNames() throws LocationServiceException;

    LocationResponse retrieveByLocationId(Integer locationId);

    LocationResponse retrieveByLocationName(String locationName);

    LocationResponse createLocation(Location location) throws LocationServiceException;

    LocationResponse deleteLocation(Integer locationId, String loggedInUser) throws LocationServiceException;

    LocationResponse updateLocation(Location location, Integer locationId) throws LocationServiceException;

}
