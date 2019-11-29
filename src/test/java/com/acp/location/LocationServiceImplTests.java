package com.acp.location;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.acp.common.business.response.CommonBaseResponse;
import com.acp.location.entities.LocationEntity;
import com.acp.location.exception.LocationServiceException;
import com.acp.location.repositories.LocationRepository;
import com.acp.location.request.Location;
import com.acp.location.requesttransformer.LocationRequestTransformer;
import com.acp.location.response.LocationResponse;
import com.acp.location.responsetransformer.LocationResponseTransformer;
import com.acp.location.service.impl.LocationServiceImpl;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
public class LocationServiceImplTests {

	@Mock
	private LocationRepository locationRepository;
	@Mock
	private LocationResponseTransformer locationResponseTransformer;
	@Mock
	private LocationRequestTransformer locationRequestTransformer;

	@InjectMocks
	private LocationServiceImpl locationServiceImpl;

	private LocationResponse locationResponse;
	private CommonBaseResponse commonBaseResponse;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		locationResponse = new LocationResponse();
		commonBaseResponse = new CommonBaseResponse();
	}

	@Test
	public void retrieveAllLocations() throws Exception {
		List<LocationEntity> locationList = new ArrayList<>();
		LocationEntity locationEntity = new LocationEntity(1, "BLR", "BLR", "BLR", true);
		locationList.add(locationEntity);
		when(locationRepository.findAll()).thenReturn(locationList);
		locationResponse = locationServiceImpl.retrieveLocations(true);
	}

	@Test
	public void retrieveActiveLocations() throws Exception {
		List<LocationEntity> locationList = new ArrayList<>();
		LocationEntity locationEntity = new LocationEntity(1, "BLR", "BLR", "BLR", true);
		locationList.add(locationEntity);
		when(locationRepository.findByLocationIsActiveTrue()).thenReturn(locationList);
		locationResponse = locationServiceImpl.retrieveLocations(false);
	}

	@Test
	public void retrieveAllLocationsBlank() throws Exception {
		List<LocationEntity> locationList = new ArrayList<>();
		when(locationRepository.findAll()).thenReturn(locationList);
		locationResponse = locationServiceImpl.retrieveLocations(true);
	}

	@Test(expected = Exception.class)
	public void retrieveLocationsException() throws LocationServiceException {
		List<LocationEntity> locationList = null;
		when(locationRepository.findAll()).thenReturn(locationList);
		locationResponse = locationServiceImpl.retrieveLocations(Mockito.any(Boolean.class));
	}

	@Test
	public void retrieveActiveLocationNamesBlank() throws Exception {
		List<Object[]> locationList = new ArrayList<>();
		when(locationRepository.findAllLocationNames(true)).thenReturn(locationList);
		commonBaseResponse = locationServiceImpl.retrieveActiveLocationNames();
	}
  
	@Test
	public void retrieveActiveLocationNames() throws Exception {
		List<Object[]> locationList = new ArrayList<>();
		Object[] arr= {1, "BLR", "BLR", "BLR", true};
		locationList.add(arr);
		when(locationRepository.findAllLocationNames(true)).thenReturn(locationList);
		commonBaseResponse = locationServiceImpl.retrieveActiveLocationNames();
	}
	@Test(expected = Exception.class)
    public void retrieveProviderNamesExp() throws LocationServiceException {

          List<Object[]> providerList = new ArrayList<>();
          Object[] arr = { new LocationEntity() };
          providerList.add(arr);
    Mockito.when(locationRepository.findAllLocationNames(Mockito.any(Boolean.class))).thenReturn(providerList);
    commonBaseResponse = locationServiceImpl.retrieveActiveLocationNames();
    }

	@Test
	public void retrieveByLocationId() throws Exception {
		LocationEntity locationEntity = new LocationEntity(1, "BLR", "BLR", "BLR", true);
		when(locationRepository.findOne(locationEntity.getLocationId())).thenReturn(locationEntity);
		locationResponse = locationServiceImpl.retrieveByLocationId(1);
	}
	@Test
	public void retrieveByLocationIdBlank() throws Exception {
		LocationEntity locationEntity = new LocationEntity(1, "BLR", "BLR", "BLR", true);
		when(locationRepository.findOne(locationEntity.getLocationId())).thenReturn(locationEntity);
		locationResponse = locationServiceImpl.retrieveByLocationId(null);
	}
	
	@Test
	public void retrieveByLocationName() throws Exception {
		LocationEntity locationEntity = new LocationEntity(1, "BLR", "BLR", "BLR", true);
		when(locationRepository.findByLocationName(locationEntity.getLocationName())).thenReturn(locationEntity);
		locationResponse = locationServiceImpl.retrieveByLocationName("BLR");
	}
	@Test
	public void retrieveByLocationNameBlank() throws Exception {
		LocationEntity locationEntity = new LocationEntity(1, "BLR", "BLR", "BLR", true);
		when(locationRepository.findByLocationName(locationEntity.getLocationName())).thenReturn(locationEntity);
		locationResponse = locationServiceImpl.retrieveByLocationName(null);
	}
	
	
	@Test
	public void createLocation() throws Exception {
		Location request = new Location("BLR", "BLR", "BLR", true, "skk");
		LocationEntity locationEntity = new LocationEntity(1, "BLR", "BLR", "BLR", true);
		when(locationRequestTransformer.transformLocationRequestParam(request)).thenReturn(locationEntity);
		when(locationRepository.findByLocationName(locationEntity.getLocationName())).thenReturn(locationEntity);
		locationResponse = locationServiceImpl.createLocation(request);
	}

	@Test
	public void createLocationBlank() throws Exception {
		Location request = new Location("BLR", "BLR", "BLR", true, "skk");
		LocationEntity locationEntity = new LocationEntity(1, "BLR", "BLR", "BLR", true);
		when(locationRequestTransformer.transformLocationRequestParam(request)).thenReturn(locationEntity);
		when(locationRepository.saveAndFlush(locationEntity)).thenReturn(locationEntity);
		locationResponse = locationServiceImpl.createLocation(request);
	}

	@Test(expected = Exception.class)
	public void createLocationException() throws Exception {
		Location request = new Location("BLR", "BLR", "BLR", true, "skk");
		LocationEntity locationEntity = new LocationEntity(1, "BLR", "BLR", "BLR", true);
		when(locationRepository.findByLocationName(locationEntity.getLocationName())).thenReturn(locationEntity);
		locationResponse = locationServiceImpl.createLocation(request);
	}
 
	@Test
	public void deleteLocationIdBlank() throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		when(locationResponseTransformer.transformList(response)).thenReturn(locationResponse);
		locationResponse = locationServiceImpl.deleteLocation(null, "skk");
	}

	@Test
	public void deleteLocationNoExistingAcccount() throws Exception {
		LocationEntity locationEntity = new LocationEntity(1, "BLR", "BLR", "BLR", true);
		when(locationRepository.findOne(locationEntity.getLocationId())).thenReturn(null);
		locationResponse = locationServiceImpl.deleteLocation(1, "skk");
	}

	@Test
	public void deleteLocation() throws Exception {
		LocationEntity locationEntity = new LocationEntity(1, "BLR", "BLR", "BLR", true);
		when(locationRepository.findOne(locationEntity.getLocationId())).thenReturn(locationEntity);
		locationResponse = locationServiceImpl.deleteLocation(1, "skk");
	}

	@Test(expected = Exception.class)
	public void deleteLocationException() throws Exception {
		LocationEntity locationEntity = new LocationEntity(1, "BLR", "BLR", "BLR", true);
		when(locationRepository.findOne(Mockito.any(Integer.class))).thenReturn(locationEntity);
		locationResponse = locationServiceImpl.deleteLocation(Mockito.any(Integer.class), Mockito.any(String.class));

	}

	@Test
	public void updateLocation() throws Exception {
		Location request = new Location("BLR", "BLR", "BLR", true, "skk");
		LocationEntity locationEntity = new LocationEntity(1, "BLR", "BLR", "BLR", true);
		when(locationRepository.findOne(locationEntity.getLocationId())).thenReturn(locationEntity);
		when(locationRequestTransformer.transformLocationRequestParam(request)).thenReturn(locationEntity);
		locationResponse = locationServiceImpl.updateLocation(request, 1);
	}

	@Test
	public void updateLocationIdBlank() throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		Location request = new Location("BLR", "BLR", "BLR", true, "skk");
		when(locationResponseTransformer.transformList(response)).thenReturn(locationResponse);
		locationResponse = locationServiceImpl.updateLocation(request, null);
	}

	@Test
	public void updateLocationNoExistingAcccount() throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		Location request = new Location("BLR", "BLR", "BLR", true, "skk");
		LocationEntity locationEntity = new LocationEntity(1, "BLR", "BLR", "BLR", true);
		when(locationRepository.findOne(locationEntity.getLocationId())).thenReturn(null);
		when(locationResponseTransformer.transformList(response)).thenReturn(locationResponse);
		locationResponse = locationServiceImpl.updateLocation(request, 1);
	}

	@Test(expected = Exception.class)
	public void updateLocationException() throws Exception {
		Location request = new Location("BLR", "BLR", "BLR", true, "skk");
		LocationEntity locationEntity = new LocationEntity(1, "BLR", "BLR", "BLR", true);
		when(locationRepository.findOne(locationEntity.getLocationId())).thenReturn(locationEntity);
		locationResponse = locationServiceImpl.updateLocation(request, 1); 
	}

}
