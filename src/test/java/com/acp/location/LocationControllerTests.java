package com.acp.location;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
import com.acp.location.controller.LocationController;
import com.acp.location.domain.Locations;
import com.acp.location.request.Location;
import com.acp.location.response.LocationResponse;
import com.acp.location.service.LocationService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class LocationControllerTests {

	@Mock
	private LocationService locationService;

	@InjectMocks
	private LocationController locationController;

	private LocationResponse locationResponse;
	private CommonBaseResponse commonBaseResponse;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		locationResponse = new LocationResponse();
		commonBaseResponse = new CommonBaseResponse();
	}

	@Test
	public void testRetrieveAllActiveLocations() throws Exception {
		Locations locations = new Locations(11, "BDC7", "BLR", "BLR", true);
		List<Locations> locationList = new ArrayList<>();
		locationList.add(locations);
		locationResponse.setLocation(locationList);
		when(locationService.retrieveLocations(Mockito.any(Boolean.class))).thenReturn(locationResponse);
		LocationResponse locationResponse1 = locationController.retrieveAllActiveLocations();
		assertEquals(locationResponse, locationResponse1);
	}

	@Test
	public void testRetrieveAllLocations() throws Exception {
		Locations locations = new Locations(11, "BDC7", "BLR", "BLR", true);
		List<Locations> locationList = new ArrayList<>();
		locationList.add(locations);
		locationResponse.setLocation(locationList);
		when(locationService.retrieveLocations(Mockito.any(Boolean.class))).thenReturn(locationResponse);
		LocationResponse locationResponse1 = locationController.retrieveAllLocations();
		assertEquals(locationResponse, locationResponse1);
	}

	@Test
	public void retrieveActiveLocationNames() throws Exception {
		when(locationService.retrieveActiveLocationNames()).thenReturn(commonBaseResponse);
		commonBaseResponse = locationController.retrieveActiveLocationNames();
		assertNotNull(commonBaseResponse);
	}

	@Test
	public void getLocationById() throws Exception {
		Locations locations = new Locations(11, "BDC7", "BLR", "BLR", true);
		when(locationService.retrieveByLocationId(Mockito.any(Integer.class))).thenReturn(locationResponse);
		locationResponse = locationController.getLocationById(locations.getLocationId());
		assertNotNull(locationResponse);
	}

	@Test
	public void getLocationByName() throws Exception {
		Locations locations = new Locations(11, "BDC7", "BLR", "BLR", true);
		when(locationService.retrieveByLocationName(Mockito.any(String.class))).thenReturn(locationResponse);
		locationResponse = locationController.getLocationByName(locations.getLocationName());
		assertNotNull(locationResponse);
	}

	@Test
	public void createLocation() throws Exception {
		Location request = new Location("BDC7", "BLR", "BLR", true, "skk");
		when(locationService.createLocation(Mockito.any(Location.class))).thenReturn(locationResponse);
		locationResponse = locationController.createLocation(request);
		assertNotNull(locationResponse);
	}

	@Test
	public void deleteLocation() throws Exception {
		when(locationService.deleteLocation(Mockito.any(Integer.class), Mockito.any(String.class)))
				.thenReturn(locationResponse);
		locationResponse = locationController.deleteLocation(2, "ABC");
		assertNotNull(locationResponse);
	}

	@Test
	public void updateLocation() throws Exception {
		Location request = new Location("BDC7", "BLR", "BLR", true, "skk");
		when(locationService.updateLocation(Mockito.any(Location.class), Mockito.any(Integer.class)))
				.thenReturn(locationResponse);
		locationResponse = locationController.updateLocation(request, 15);
		assertNotNull(locationResponse);
	}
}
