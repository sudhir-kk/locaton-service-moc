package com.acp.location.domain;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement(name = "locations")
@JsonInclude(Include.NON_EMPTY)
public class Locations implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer locationId;
    private String locationName;
    private String locationDesc;
    private String locationRemarks;
    private Boolean locationIsActive;

    public Locations() {
	super();
    }

    public Locations(Integer locationId, String locationName, String locationDesc, String locationRemarks,
	    Boolean locationIsActive) {
	super();
	this.locationId = locationId;
	this.locationName = locationName;
	this.locationDesc = locationDesc;
	this.locationRemarks = locationRemarks;
	this.locationIsActive = locationIsActive;

    }

    public Integer getLocationId() {
	return locationId;
    }

    @XmlElement
    public void setLocationId(Integer locationId) {
	this.locationId = locationId;
    }

    public String getLocationName() {
	return locationName;
    }

    @XmlElement
    public void setLocationName(String locationName) {
	this.locationName = locationName;
    }

    public String getLocationDesc() {
	return locationDesc;
    }

    @XmlElement
    public void setLocationDesc(String locationDesc) {
	this.locationDesc = locationDesc;
    }

    public String getLocationRemarks() {
	return locationRemarks;
    }

    @XmlElement
    public void setLocationRemarks(String locationRemarks) {
	this.locationRemarks = locationRemarks;
    }

    public Boolean getLocationIsActive() {
	return locationIsActive;
    }

    @XmlElement
    public void setLocationIsActive(Boolean locationIsActive) {
	this.locationIsActive = locationIsActive;
    }

    @Override
    public String toString() {
	return "Locations [locationId=" + locationId + ", locationName=" + locationName + ", locationDesc="
		+ locationDesc + ", locationRemarks=" + locationRemarks + ", locationIsActive=" + locationIsActive
		+ "]";
    }

}
