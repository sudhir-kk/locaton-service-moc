package com.acp.location.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.acp.location.entities.LocationEntity;

@Repository
public interface LocationRepository
	extends JpaRepository<LocationEntity, Integer>, JpaSpecificationExecutor<LocationEntity> {
    public List<LocationEntity> findByLocationIsActiveTrue();

    @Query(value = "SELECT s.LOCATIONID, s.LOCATIONNAME FROM {h-domain}locations s WHERE s.LOCATIONISACTIVE = ?1", nativeQuery = true)
    public List<Object[]> findAllLocationNames(Boolean locationIsActive);

    @Query(value = "SELECT * FROM {h-domain}locations s WHERE s.LOCATIONID = ?1", nativeQuery = true)
    LocationEntity findOne(Integer locationId);

    public LocationEntity findByLocationName(String locationName);
}
