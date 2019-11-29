package com.acp.location.responsetransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.acp.common.business.constants.ResponseConstants;
import com.acp.common.business.responsetransformer.ResponseBaseTransformer;
import com.acp.location.domain.Locations;
import com.acp.location.entities.LocationEntity;
import com.acp.location.response.LocationResponse;

@Component
public class LocationResponseTransformer extends ResponseBaseTransformer {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationResponseTransformer.class);

    @SuppressWarnings("unchecked")
    public LocationResponse transformList(Map<String, Object> result) {
	LOGGER.debug("Entered location response transform method");
	final LocationResponse locationResponse = new LocationResponse();
	if (result != null) {
	    fillResponseStatusMsgAndCode(result, locationResponse);
	    if ((result.get(ResponseConstants.CODE).equals(HttpStatus.OK.value())
		    || result.get(ResponseConstants.CODE).equals(HttpStatus.CREATED.value())
		    || result.get(ResponseConstants.CODE).equals(HttpStatus.NO_CONTENT.value()))
		    && result.get(ResponseConstants.PAYLOAD) != null) {
		final List<LocationEntity> locationList = (List<LocationEntity>) result.get(ResponseConstants.PAYLOAD);
		locationResponse.setLocation(constructResponseList(locationList));
	    } else if (result.get(ResponseConstants.CODE).equals(HttpStatus.PRECONDITION_FAILED.value())
		    || result.get(ResponseConstants.CODE).equals(HttpStatus.FAILED_DEPENDENCY.value())
			    && result.get(ResponseConstants.PAYLOAD) != null) {
		locationResponse.setMessage((String) result.get(ResponseConstants.PAYLOAD));
	    }
	}
	return locationResponse;
    }

    private List<Locations> constructResponseList(List<LocationEntity> locations) {
	List<Locations> response = null;

	if (!CollectionUtils.isEmpty(locations)) {
	    response = new ArrayList<Locations>();
	    for (final LocationEntity location : locations) {
		final Locations responseObj = copyLocationProperties(location);
		response.add(responseObj);
	    }
	}
	return response;
    }

    protected Locations copyLocationProperties(final LocationEntity location) {
	final Locations responseObj = new Locations();
	BeanUtils.copyProperties(location, responseObj);
	return responseObj;
    }

}