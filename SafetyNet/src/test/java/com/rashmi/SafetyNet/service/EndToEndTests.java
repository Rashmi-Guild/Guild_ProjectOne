package com.rashmi.SafetyNet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import com.rashmi.SafetyNet.dto.PersonWithAddressDTO;
import com.rashmi.SafetyNet.dto.PersonWithAddressEmailDTO;
import com.rashmi.SafetyNet.dto.PersonWithMedicationDTO;
import com.rashmi.SafetyNet.resources.Person;

class EndToEndTests {

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
	    
	    when(endPointsController.getPeopleUnderFireStation(1)).thenReturn(response);
	    
	    Map<String, Object> actualResponse =endPointsController.getPeopleUnderFireStation(1);
	    
	   List<Person> people = (List<Person>) actualResponse.get("persons");
	    
	    assertEquals("John",people.get(0).getFirstName(),"Person name should be John");
	    assertEquals("1",actualResponse.get("numberOfAdults"),"Number of Adults count should be one");
	    assertEquals("1",actualResponse.get("numberOfChildren"),"Number of children count should be one");
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
    		   	
	    
	    when(endPointsController.getChildrenAtAddress("123 Main St")).thenReturn(response);
	    
	    Map<String, Object> actualResponse =endPointsController.getChildrenAtAddress("123 Main St");
	    
	   List<Person> children = (List<Person>) actualResponse.get("children");
	   List<Person> adults = (List<Person>) actualResponse.get("adults");
	    
	    assertEquals("John",adults.get(0).getFirstName(),"Adult name should be John");
	    assertEquals(2,adults.size(),"Number of Adults count should be two");
	    assertEquals("Tom",children.get(0).getFirstName(),"Child name should be Tom");
	    assertEquals(2,children.size(),"Number of children count should be two");
	}

	@Test
	void testGetPhoneUnderFireStation() {
		List<String> phoneNumbers = new ArrayList<String>(Arrays.asList("1234567890", "9087654321", "6543217890"));
		
		when(endPointsController.getPhoneUnderFireStation(3)).thenReturn(phoneNumbers);
    	
		List<String> actualPhoneNumbers =   endPointsController.getPhoneUnderFireStation(3);
    	
		assertEquals(true,phoneNumbers.contains("1234567890"),"Phone number 1234567890 should be present");
	    assertEquals(3,actualPhoneNumbers.size(),"Number of Phone numbers count should be three");
    	
	}
	
	@Test
	void testGetFireStationByAddress() {
		
		  int fireStationNumber = 1;
		  List<PersonWithMedicationDTO> mockPeople = Arrays.asList( new PersonWithMedicationDTO("John","Doe","123-456-7890",25,Arrays.asList("ibupurin:200mg",
		  "hydrapermazol:400mg"), Arrays.asList("nillacilan")), new  PersonWithMedicationDTO("Jane","Doe","123-456-7890",45,Arrays.asList("pharmacol:5000mg", "terazine:10mg",
		  "noznazol:250mg"), Arrays.asList("peanut")) ); 
		  
		  Map<String,Object> response =  new HashMap<String, Object>(); 
		  
		  response.put("firestationNumber", fireStationNumber); 
		  response.put("people",mockPeople);
		  
		  when(endPointsController.getFireStationByAddress("123 Main St")).thenReturn(response);
		  
		  Map<String, Object> actualResponse = endPointsController.getFireStationByAddress("123 Main St");
		  
		  List<PersonWithMedicationDTO> people = (List<PersonWithMedicationDTO>) actualResponse.get("people");
		  
		  assertEquals("John",people.get(0).getFirstName(),"Adult name should be John");
	      assertEquals(25,people.get(0).getAge(),"Age should be 25");
	      assertEquals(Arrays.asList("ibupurin:200mg","hydrapermazol:400mg"),people.get(0).getMedications(),"Medications must be same");
	      assertEquals(Arrays.asList("nillacilan"),people.get(0).getAllergies(),"Allergies must be same");
		 
	}

	@Test
	void testGetPeopleByStation() {
		  List<Integer> stationNumbers = Arrays.asList(1);
		  List<PersonWithAddressDTO> mockPeople = Arrays.asList( new PersonWithAddressDTO("John","Doe","123-456-7890",25,Arrays.asList("ibupurin:200mg",
		  "hydrapermazol:400mg"), Arrays.asList("nillacilan"),"123 Main st"), new  PersonWithAddressDTO("Jane","Doe","123-456-7890",45,Arrays.asList("pharmacol:5000mg", "terazine:10mg",
		  "noznazol:250mg"), Arrays.asList("peanut"),"123 Main st") ); 
		  
		  Map<String,List<PersonWithAddressDTO>> response =  new HashMap<String, List<PersonWithAddressDTO>>(); 
		  
		  response.put("123 Main st",mockPeople); 
		  
		  when(endPointsController.getPeopleByStation(stationNumbers)).thenReturn(response);
		  
		  Map<String,List<PersonWithAddressDTO>> actualResponse = endPointsController.getPeopleByStation(stationNumbers);
		  
		  List<PersonWithAddressDTO> people = actualResponse.get("123 Main st");
		  
		  assertEquals("John",people.get(0).getFirstName(),"Adult name should be John");
	      assertEquals(25,people.get(0).getAge(),"Age should be 25");
	      assertEquals(Arrays.asList("ibupurin:200mg","hydrapermazol:400mg"),people.get(0).getMedications(),"Medications must be same");
	      assertEquals(Arrays.asList("nillacilan"),people.get(0).getAllergies(),"Allergies must be same");
	      assertEquals("123 Main st",people.get(0).getAddress(),"Address should be 123 Main st");
	}

	@Test
	void testGetPersonByName() {
		
		String firstName = "John";
		String lastName = "Doe";
		
	  List<PersonWithAddressEmailDTO> mockPeople = Arrays.asList( new PersonWithAddressEmailDTO("John","Doe","123-456-7890",25,Arrays.asList("ibupurin:200mg",
	  "hydrapermazol:400mg"), Arrays.asList("nillacilan"),"123 Main st","Test@gmail.com"), new  PersonWithAddressEmailDTO("Jane","Doe","123-456-7890",45,Arrays.asList("pharmacol:5000mg", "terazine:10mg",
	  "noznazol:250mg"), Arrays.asList("peanut"),"123 Main st","Test1@gmail.com") ); 
	  
	  when(endPointsController.getPersonByName(firstName,lastName)).thenReturn(mockPeople);
	  
	  List<PersonWithAddressEmailDTO> actualResponse = endPointsController.getPersonByName(firstName,lastName);
	 
	  
	  assertEquals("John",actualResponse.get(0).getFirstName(),"Adult name should be John");
      assertEquals(25,actualResponse.get(0).getAge(),"Age should be 25");
      assertEquals(Arrays.asList("ibupurin:200mg","hydrapermazol:400mg"),actualResponse.get(0).getMedications(),"Medications must be same");
      assertEquals(Arrays.asList("nillacilan"),actualResponse.get(0).getAllergies(),"Allergies must be same");
      assertEquals("123 Main st",actualResponse.get(0).getAddress(),"Address should be 123 Main st");
      assertEquals("Test@gmail.com",actualResponse.get(0).getEmail(),"Email should be Test@gmail.com");
	}

	@Test
	void testGetAllEmailsInCity() {
		
		String city = "Culver";
		
	  List<String> mockEmails = Arrays.asList("test@gmail.com","test1@gmail.com","test2@gmail.com"); 
	  
	  when(endPointsController.getAllEmailsInCity(city)).thenReturn(mockEmails);
	  
	  List<String> actualResponse = endPointsController.getAllEmailsInCity(city);
	 
	  
	  assertFalse(actualResponse.isEmpty(),"Email list should not be empty for address Culver");
	  assertEquals("test@gmail.com",actualResponse.get(0));
	}

}
