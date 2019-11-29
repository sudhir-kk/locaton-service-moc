package com.acp.location.requesttransformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.acp.location.entities.LocationEntity;
import com.acp.location.request.Location;

@Component
public class LocationRequestTransformer {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationRequestTransformer.class);

    /*
     * 
     * Transform the input json params to entity params
     */
    public LocationEntity transformLocationRequestParam(Location locationInput) {
	final LocationEntity locationInfo = new LocationEntity();
	BeanUtils.copyProperties(locationInput, locationInfo);
	LOGGER.info("Transformed the input json params of Location to entity params");
	return locationInfo;
    }
}
