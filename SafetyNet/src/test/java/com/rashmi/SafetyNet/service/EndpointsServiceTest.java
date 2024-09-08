package com.rashmi.SafetyNet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rashmi.SafetyNet.dto.PersonWithAddressDTO;
import com.rashmi.SafetyNet.dto.PersonWithAddressEmailDTO;
import com.rashmi.SafetyNet.dto.PersonWithMedicationDTO;
import com.rashmi.SafetyNet.repositories.FireStationRepository;
import com.rashmi.SafetyNet.repositories.MedicalRecordRepository;
import com.rashmi.SafetyNet.repositories.PersonRepository;
import com.rashmi.SafetyNet.resources.FireStation;
import com.rashmi.SafetyNet.resources.MedicalRecord;
import com.rashmi.SafetyNet.resources.Person;

class EndpointsServiceTest {

	@Mock
    private FireStationRepository firestationRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private MedicalRecordRepository medicalRepository;

    @InjectMocks
    private EndpointsService endpointsService;
    
    @Mock
    private ObjectMapper objectMapper;
    
    @Mock
    private ClassPathResource personResource;

    @Mock
    private ClassPathResource firestationResource;

    @Mock
    private ClassPathResource medicalRecordResource;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
	
	  @Test 
	  void testLoadData() throws IOException {
		  
		  // Mock the input streams
	  InputStream personInputStream = mock(InputStream.class); 
	  InputStream firestationInputStream = mock(InputStream.class);
	  InputStream  medicalRecordInputStream = mock(InputStream.class);
	  
	  TypeReference<List<Person>> personTypeReference = new TypeReference<>() {};
      TypeReference<List<FireStation>> firestationTypeReference = new TypeReference<>() {};
      TypeReference<List<MedicalRecord>> medicalRecordTypeReference = new TypeReference<>() {};
	  
	  when(personResource.getInputStream()).thenReturn(personInputStream);
	  when(firestationResource.getInputStream()).thenReturn(firestationInputStream); 
	  when(medicalRecordResource.getInputStream()).thenReturn( medicalRecordInputStream);
	  
	  // Mock the data to be returned by the ObjectMapper
	  List<Person> persons = List.of(new Person(1L, "John", "Boyd", "123 Main St", "City", "22222","123-456-7890", "test@gmail.com", 35),
			                         new Person(1L, "Peter", "Duncan", "123 Main St", "City", "22222","123-456-7890", "test1@gmail.com", 20));
	  List<FireStation> firestations = List.of(new FireStation(1L,"123 Main St", 1)); 
	  List<MedicalRecord> medicalRecords = List.of(new MedicalRecord(1L,"John", "Doe", "01/01/2000", List.of(), List.of()));
	  
	  when(objectMapper.readValue((personInputStream),personTypeReference)).thenReturn(persons);
	  when(objectMapper.readValue((firestationInputStream), firestationTypeReference)).thenReturn(firestations);
	  when(objectMapper.readValue((medicalRecordInputStream),medicalRecordTypeReference)).thenReturn(medicalRecords);
	  
	  // Call the method to test
	  String result = endpointsService.loadData();
	  
	// Capture the actual arguments passed to the saveAll method
	    ArgumentCaptor<List<Person>> personCaptor = ArgumentCaptor.forClass(List.class);
	    verify(personRepository, times(1)).saveAll(personCaptor.capture());

	    // Use ArgumentMatcher to check for specific properties or contents
	    List<Person> capturedPersons = personCaptor.getValue();
	    assertTrue(capturedPersons.stream().anyMatch(p -> "John".equals(p.getFirstName()) && "Boyd".equals(p.getLastName())),
	               "Expected person with name John Doe not found in the saved list");


	    // Verify the result of the method
	    assertEquals("Data loaded successfully", result);
	  }
	 


    @Test
    void testGetPeopleUnderFireStation() {
        // Mock data
        FireStation station = new FireStation(1L,"123 Main St", 1);
        List<FireStation> firestations = Arrays.asList(station);
        
        Person person1 = new Person(1L, "John", "Doe", "123 Main St", "City", "22222", "123-456-7890", "test@gmail.com", 45);
        Person person2 = new Person(2L, "Jane", "Doe", "123 Main St", "City", "22222", "123-456-7891", "test2@gmail.com", 16);
        List<Person> persons = Arrays.asList(person1, person2);

        MedicalRecord record1 = new MedicalRecord(1L,"John", "Doe", "03/01/2000", new ArrayList<>(), new ArrayList<>());
        MedicalRecord record2 = new MedicalRecord(2L,"Jane", "Doe", "07/15/2010", new ArrayList<>(), new ArrayList<>());

        // Mock repository methods
        when(firestationRepository.findByStation(1)).thenReturn(firestations);
        when(personRepository.findByAddress("123 Main St")).thenReturn(persons);
        when(medicalRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(record1);
        when(medicalRepository.findByFirstNameAndLastName("Jane", "Doe")).thenReturn(record2);

        // Call the method to test
        Map<String, Object> response = endpointsService.getPeopleUnderFireStation(1);

        // Verify the response
        assertEquals(2, ((List<Person>) response.get("persons")).size());
        assertEquals(1L, response.get("numberOfAdults"));
        assertEquals(1L, response.get("numberOfChildren"));
    }

    @Test
    void testGetChildrenAtAddress() {
        // Mock data
        Person person1 = new Person(1L, "John", "Doe", "123 Main St", "City", "22222", "123-456-7890", "test@gmail.com", 0);
        Person person2 = new Person(2L, "Jane", "Doe", "123 Main St", "City", "22222", "123-456-7891", "test2@gmail.com", 0);
        List<Person> persons = Arrays.asList(person1, person2);

        MedicalRecord record1 = new MedicalRecord(1L,"John", "Doe", "03/01/2000", new ArrayList<>(), new ArrayList<>());
        MedicalRecord record2 = new MedicalRecord(2L,"Jane", "Doe", "07/15/2010", new ArrayList<>(), new ArrayList<>());

        // Mock repository methods
        when(personRepository.findByAddress("123 Main St")).thenReturn(persons);
        when(medicalRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(record1);
        when(medicalRepository.findByFirstNameAndLastName("Jane", "Doe")).thenReturn(record2);

        // Call the method to test
        Map<String, Object> response = endpointsService.getChildrenAtAddress("123 Main St");

        // Verify the response
        assertEquals(1, ((List<?>) response.get("children")).size());
        assertEquals(1, ((List<?>) response.get("adults")).size());
    }

    @Test
    void testGetPhoneUnderFireStation() {
        // Mock data
        FireStation station = new FireStation(1L,"123 Main St", 1);
        List<FireStation> firestations = Arrays.asList(station);
        
        Person person1 = new Person(1L, "John", "Doe", "123 Main St", "City", "22222", "123-456-7890", "test@gmail.com", 0);
        List<Person> persons = Arrays.asList(person1);

        // Mock repository methods
        when(firestationRepository.findByStation(1)).thenReturn(firestations);
        when(personRepository.findByAddress("123 Main St")).thenReturn(persons);

        // Call the method to test
        List<String> phoneNumbers = endpointsService.getPhoneUnderFireStation(1);

        // Verify the response
        assertEquals(1, phoneNumbers.size());
        assertEquals("123-456-7890", phoneNumbers.get(0));
    }

    @Test
    void testGetFireStationByAddress() {
        // Mock data
        FireStation station = new FireStation(1L,"123 Main St", 1);
        Person person = new Person(1L, "John", "Doe", "123 Main St", "City", "22222", "123-456-7890", "test@gmail.com", 0);
        MedicalRecord record = new MedicalRecord(1L,"John", "Doe", "03/01/2000", new ArrayList<>(), new ArrayList<>());

        // Mock repository methods
        when(firestationRepository.findByAddress("123 Main St")).thenReturn(station);
        when(personRepository.findByAddress("123 Main St")).thenReturn(Arrays.asList(person));
        when(medicalRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(record);

        // Call the method to test
        Map<String, Object> response = endpointsService.getFireStationByAddress("123 Main St");

        // Verify the response
        assertEquals(1, response.get("firestationNumber"));
        assertEquals(1, ((List<PersonWithMedicationDTO>) response.get("people")).size());
        assertEquals("John", ((List<PersonWithMedicationDTO>) response.get("people")).get(0).getFirstName());
    }

    @Test
    void testGetPeopleByStation() {
        // Mock data
        FireStation station = new FireStation(1L,"123 Main St", 1);
        Person person = new Person(1L, "John", "Doe", "123 Main St", "City", "22222", "123-456-7890", "test@gmail.com", 0);
        MedicalRecord record = new MedicalRecord(1L,"John", "Doe", "03/01/2000", new ArrayList<>(), new ArrayList<>());

        // Mock repository methods
        when(firestationRepository.findByStation(1)).thenReturn(Arrays.asList(station));
        when(personRepository.findByAddress("123 Main St")).thenReturn(Arrays.asList(person));
        when(medicalRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(record);

        // Call the method to test
        Map<String, List<PersonWithAddressDTO>> response = endpointsService.getPeopleByStation(Arrays.asList(1));

        // Verify the response
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(1, response.get("123 Main St").size());
        assertEquals("John", response.get("123 Main St").get(0).getFirstName());
    }

    @Test
    void testGetPersonByName() {
        // Mock data
        Person person = new Person(1L, "John", "Doe", "123 Main St", "City", "22222", "123-456-7890", "test@gmail.com", 0);
        MedicalRecord record = new MedicalRecord(1L,"John", "Doe", "03/01/2000", new ArrayList<>(), new ArrayList<>());

        // Mock repository methods
        when(personRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(Arrays.asList(person));
        when(medicalRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(record);

        // Call the method to test
        List<PersonWithAddressEmailDTO> response = endpointsService.getPersonByName("John", "Doe");

        // Verify the response
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("John", response.get(0).getFirstName());
    }

    @Test
    void testGetAllEmailsInCity() {
        // Mock data
        Person person1 = new Person(1L, "John", "Doe", "123 Main St", "City", "22222", "123-456-7890", "test@gmail.com", 0);
        Person person2 = new Person(2L, "Jane", "Doe", "124 Main St", "City", "22222", "123-456-7891", "test2@gmail.com", 0);

        // Mock repository methods
        when(personRepository.findByCity("City")).thenReturn(Arrays.asList(person1, person2));

        // Call the method to test
        List<String> response = endpointsService.getAllEmailsInCity("City");

        // Verify the response
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("test@gmail.com", response.get(0));
        assertEquals("test2@gmail.com", response.get(1));
    }
}