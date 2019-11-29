package com.acp.location.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import com.acp.common.business.response.CommonBaseResponse;
import com.acp.location.domain.Locations;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement(name = "locationResponse")
@JsonInclude(Include.NON_EMPTY)
@XmlSeeAlso(Locations.class)
public class LocationResponse extends CommonBaseResponse {
    private List<Locations> location = new ArrayList<Locations>();

    public List<Locations> getLocation() {
	return location;
    }

    @XmlElement
    public void setLocation(List<Locations> location) {
	this.location = location;
    }
}
