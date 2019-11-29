package com.acp.location.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.acp.location.constants.LocationConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement(name = "locations")
@JsonInclude(Include.NON_EMPTY)
public class Location implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = LocationConstants.LOCATION_NAME_BLANK_MSG)
    @Size(max = 100, message = LocationConstants.LOCATION_NAME_SIZE_MSG)
    private String locationName;

    private String locationDesc;
    private String locationRemarks;

    @NotNull(message = LocationConstants.LOCATION_IS_ACTIVE)
    private Boolean locationIsActive;

    @NotBlank(message = LocationConstants.LOGGEDUSER_INFO_BLANK_MSG)
    @Size(max = 100, message = LocationConstants.LOGGEDUSER_SIZE_MSG)
    private String loggedInUser;

    public Location() {
	super();

    }

    public Location(String locationName, String locationDesc, String locationRemarks, Boolean locationIsActive,
	    String loggedInUser) {
	super();
	this.locationName = locationName;
	this.locationDesc = locationDesc;
	this.locationRemarks = locationRemarks;
	this.locationIsActive = locationIsActive;
	this.loggedInUser = loggedInUser;
    }

    public String getLocationName() {
	return locationName;
    }

    public void setLocationName(String locationName) {
	this.locationName = locationName;
    }

    public String getLocationDesc() {
	return locationDesc;
    }

    public void setLocationDesc(String locationDesc) {
	this.locationDesc = locationDesc;
    }

    public String getLocationRemarks() {
	return locationRemarks;
    }

    public void setLocationRemarks(String locationRemarks) {
	this.locationRemarks = locationRemarks;
    }

    public Boolean getLocationIsActive() {
	return locationIsActive;
    }

    public void setLocationIsActive(Boolean locationIsActive) {
	this.locationIsActive = locationIsActive;
    }

    public String getLoggedInUser() {
	return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
	this.loggedInUser = loggedInUser;
    }

    @Override
    public String toString() {
	return "Location [locationName=" + locationName + ", locationDesc=" + locationDesc + ", locationRemarks="
		+ locationRemarks + ", locationIsActive=" + locationIsActive + ", loggedInUser=" + loggedInUser + "]";
    }

}
