package com.acp.location.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "LOCATIONS")
public class LocationEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCATIONID")
    private Integer locationId;

    @Column(name = "LOCATIONNAME")
    private String locationName;

    @Column(name = "LOCATIONDESC")
    private String locationDesc;

    @Column(name = "LOCATIONREMARKS")
    private String locationRemarks;

    @Column(name = "LOCATIONISACTIVE")
    private Boolean locationIsActive;

    @Column(name = "CREATEDBY", length = 100, nullable = false, updatable = false)
    private String createdBy;

    @Column(name = "CREATEDON", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar createdOn;

    @Column(name = "LASTUPDATEDBY", length = 100, nullable = false)
    private String lastUpdatedBy;

    @Column(name = "LASTUPDATEDON", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar lastUpdatedOn;

    public LocationEntity() {
	super();
    }

    public LocationEntity(Integer locationId, String locationName, String locationDesc, String locationRemarks,
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

    public void setLocationId(Integer locationId) {
	this.locationId = locationId;
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

    public String getCreatedBy() {
	return createdBy;
    }

    public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
    }

    public Calendar getCreatedOn() {
	return createdOn;
    }

    public void setCreatedOn(Calendar createdOn) {
	this.createdOn = createdOn;
    }

    public String getLastUpdatedBy() {
	return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
	this.lastUpdatedBy = lastUpdatedBy;
    }

    public Calendar getLastUpdatedOn() {
	return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Calendar lastUpdatedOn) {
	this.lastUpdatedOn = lastUpdatedOn;
    }

    public Calendar getUTCDate() {
	TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
	return Calendar.getInstance();
    }

    @PrePersist
    protected void prepersist() {
	if (StringUtils.isEmpty(this.createdBy)) {
	    setCreatedBy("SYSTEM");
	}
	if (StringUtils.isEmpty(this.lastUpdatedBy)) {
	    setLastUpdatedBy("SYSTEM");
	}
	setCreatedOn(getUTCDate());
	setLastUpdatedOn(getUTCDate());

    }

    @PreUpdate
    protected void preupdate() {
	if (StringUtils.isEmpty(this.lastUpdatedBy)) {
	    setLastUpdatedBy("SYSTEM");
	}
	setLastUpdatedOn(getUTCDate());
    }

    @Override
    public String toString() {
	return "LocationEntity [locationId=" + locationId + ", locationName=" + locationName + ", locationDesc="
		+ locationDesc + ", locationRemarks=" + locationRemarks + ", locationIsActive=" + locationIsActive
		+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", lastUpdatedBy=" + lastUpdatedBy
		+ ", lastUpdatedOn=" + lastUpdatedOn + "]";
    }

}
