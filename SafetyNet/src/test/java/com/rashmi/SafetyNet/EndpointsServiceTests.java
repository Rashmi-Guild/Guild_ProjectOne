package com.rashmi.SafetyNet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rashmi.SafetyNet.controller.EndPointsController;
import com.rashmi.SafetyNet.dto.PersonWithMedicationDTO;
import com.rashmi.SafetyNet.resources.FireStation;
import com.rashmi.SafetyNet.resources.Person;
import com.rashmi.SafetyNet.service.EndpointsService;

class EndpointsServiceTests {

	@Mock
    private EndpointsService endpointsService;

    @InjectMocks
    private EndPointsController endPointsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
	
	@Test
	void testGetPeopleUnderFireStation() {
		
		List<Person> mockPeople = Arrays.asList(
	            new Person(1L,"John", "Doe", "123 Main St","City","22222","123-456-7890","test@gmail.com",25),
	            new Person(2L,"Jane", "Doe", "456 Elm St","NewYork","33333", "987-654-3210","test2@gmail.com",17)
	        );
		Map<String, Object> response = new HashMap<>();
		response.put("persons", mockPeople);
	    response.put("numberOfAdults", "1");
	    response.put("numberOfChildren", "1");
	    
//	    when(endPointsController.getPeopleUnderFireStation(1)).thenReturn(response);
//	    
//	    Map<String, Object> actualResponse =endPointsController.getPeopleUnderFireStation(1);
//	    
//	   List<Person> people = (List<Person>) actualResponse.get("persons");
//	    
//	    assertEquals("John",people.get(0).getFirstName(),"Person name shoul dbe John");
//	    assertEquals("1",actualResponse.get("numberOfAdults"),"Number of Adults count should be one");
//	    assertEquals("1",actualResponse.get("numberOfChildren"),"Number of children count should be one");
	}

	@Test
	void testGetChildrenAtAddress() {
		List<Person> mockAdults = Arrays.asList(
	            new Person(1L,"John", "Doe", "123 Main St","City","22222","123-456-7890","test@gmail.com",25),
	            new Person(2L,"Jane", "Doe", "123 Main St","City","22222", "987-654-3210","test2@gmail.com",17)
	        );
		List<Person> mockChildren = Arrays.asList(
	            new Person(3L,"Tom", "Doe", "123 Main St","City","22222","123-456-7890","test@gmail.com",25),
	            new Person(4L,"Sienna", "Doe", "123 Main St","City","22222", "987-654-3210","test2@gmail.com",17)
	        );
		Map<String, Object> response = new HashMap<>();
	    response.put("children", mockChildren);
	    response.put("adults",mockAdults);
    		   	
	    
//	    when(endPointsController.getChildrenAtAddress("123 Main St")).thenReturn(response);
//	    
//	    Map<String, Object> actualResponse =endPointsController.getChildrenAtAddress("123 Main St");
//	    
//	   List<Person> children = (List<Person>) actualResponse.get("children");
//	   List<Person> adults = (List<Person>) actualResponse.get("adults");
//	    
//	    assertEquals("John",adults.get(0).getFirstName(),"Adult name should be John");
//	    assertEquals(2,adults.size(),"Number of Adults count should be two");
//	    assertEquals("Tom",children.get(0).getFirstName(),"Child name should be Tom");
//	    assertEquals(2,children.size(),"Number of children count should be two");
	}

	@Test
	void testGetPhoneUnderFireStation() {
		List<String> phoneNumbers = new ArrayList<String>(Arrays.asList("1234567890", "9087654321", "6543217890"));
		
//		when(endPointsController.getPhoneUnderFireStation(3)).thenReturn(phoneNumbers);
//    	
//		List<String> actualPhoneNumbers =   endPointsController.getPhoneUnderFireStation(3);
//    	
//		assertEquals(true,phoneNumbers.contains("1234567890"),"Phone number 1234567890 should be present");
//	    assertEquals(3,actualPhoneNumbers.size(),"Number of Phone numbers count should be three");
//    	
	}
	
	@Test
	void testGetFireStationByAddressString() {
		/*
		 * List<Integer> fireStations = new ArrayList<Integer>(Arrays.asList(1,2,3));
		 * List<PersonWithMedicationDTO> mockPeople = Arrays.asList( new
		 * PersonWithMedicationDTO("John",
		 * "Doe","123-456-7890",25,Arrays.asList("ibupurin:200mg",
		 * "hydrapermazol:400mg"), Arrays.asList("nillacilan")), new
		 * PersonWithMedicationDTO("Jane",
		 * "Doe","123-456-7890",45,Arrays.asList("pharmacol:5000mg", "terazine:10mg",
		 * "noznazol:250mg"), Arrays.asList("peanut")) ); Map<String,Object> response =
		 * new HashMap<String, Object>(); response.put("firestationNumber",
		 * fireStations); response.put("people",mockPeople);
		 * 
		 * when(endPointsController.getPhoneUnderFireStation(3)).thenReturn(phoneNumbers
		 * );
		 * 
		 * fail("Not yet implemented");
		 */
	}

	@Test
	void testGetFireStationByAddressListOfInteger() {
		fail("Not yet implemented");
	}

	@Test
	void testGetPersonByName() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllEmailsInCity() {
		fail("Not yet implemented");
	}

}
