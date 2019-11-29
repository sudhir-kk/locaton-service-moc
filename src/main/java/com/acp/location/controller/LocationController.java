package com.acp.location.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acp.common.business.response.CommonBaseResponse;
import com.acp.location.exception.LocationServiceException;
import com.acp.location.request.Location;
import com.acp.location.response.LocationResponse;
import com.acp.location.service.LocationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "Locations", produces = "application/json")
@RequestMapping("/locations")
@ComponentScan(basePackages = { "com.acp.location.*" })
public class LocationController {
	
    @Autowired
    private LocationService locationServce;

    @RequestMapping(value = "/activeLocations", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "retrieves all the active Locations")
    public LocationResponse retrieveAllActiveLocations() throws LocationServiceException {
	return locationServce.retrieveLocations(Boolean.FALSE);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "retrieves all Locations (Both Active and InActive)")
    public LocationResponse retrieveAllLocations() throws LocationServiceException {
	return locationServce.retrieveLocations(Boolean.TRUE);
    }

    @RequestMapping(value = "/locationNames", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "retrieves all active LocationId, LocationName")
    public CommonBaseResponse retrieveActiveLocationNames() throws LocationServiceException {
	return locationServce.retrieveActiveLocationNames();
    }

    @RequestMapping(value = "/{locationId}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "retrieves Location based on LocationId")
    public LocationResponse getLocationById(
	    @ApiParam(value = "locationId", required = true) @PathVariable(value = "locationId") Integer locationId) {
	return locationServce.retrieveByLocationId(locationId);
    }

    @RequestMapping(value = "/locationNames/{locationName}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "retrieves LocationInfo based on LocationName")
    public LocationResponse getLocationByName(
	    @ApiParam(value = "LocationName", required = true) @PathVariable(value = "locationName") String locationName) {
	return locationServce.retrieveByLocationName(locationName);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "adds a new location info into DB")
    public LocationResponse createLocation(
	    @ApiParam(name = "Location :", value = "A JSON value representing a transaction.", required = true) @Valid @RequestBody Location location)
	    throws LocationServiceException {
	return locationServce.createLocation(location);
    }

    @RequestMapping(value = "/{locationId}", method = RequestMethod.DELETE, produces = "application/json")
    @ApiOperation(value = "de-activate the Location")
    public LocationResponse deleteLocation(
	    @ApiParam(value = "LocationId", required = true) @PathVariable(value = "locationId") Integer locationId,
	    @ApiParam(value = "LoggedInUser", required = true) @RequestParam(value = "loggedInUser") String loggedInUser)
	    throws LocationServiceException {
	return locationServce.deleteLocation(locationId, loggedInUser);
    }

    @RequestMapping(value = "/{locationId}", method = RequestMethod.PUT, produces = "application/json")
    @ApiOperation(value = "Updates the Location based on LocationId")
    public LocationResponse updateLocation(
	    @ApiParam(name = "Location  :", value = "A JSON value representing a transaction.", required = true) @Valid @RequestBody Location location,
	    @ApiParam(value = "LocationId", required = true) @PathVariable(value = "locationId") Integer locationId)
	    throws LocationServiceException {
	return locationServce.updateLocation(location, locationId);
    }
}
