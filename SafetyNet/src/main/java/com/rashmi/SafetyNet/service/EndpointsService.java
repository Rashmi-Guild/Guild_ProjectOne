package com.rashmi.SafetyNet.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rashmi.SafetyNet.dto.ChildrenDTO;
import com.rashmi.SafetyNet.dto.PersonWithAddressDTO;
import com.rashmi.SafetyNet.dto.PersonWithAddressEmailDTO;
import com.rashmi.SafetyNet.dto.PersonWithMedicationDTO;
import com.rashmi.SafetyNet.repositories.FireStationRepository;
import com.rashmi.SafetyNet.repositories.MedicalRecordRepository;
import com.rashmi.SafetyNet.repositories.PersonRepository;
import com.rashmi.SafetyNet.resources.FireStation;
import com.rashmi.SafetyNet.resources.MedicalRecord;
import com.rashmi.SafetyNet.resources.Person;

@Service
public class EndpointsService {

	@Autowired
    private FireStationRepository firestationRepository;
    
    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private MedicalRecordRepository medicalRepository;
    
    
    public String loadData() {
    	ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Person>> personTypeReference = new TypeReference<>() {};
        TypeReference<List<FireStation>> firestationTypeReference = new TypeReference<>() {};
        TypeReference<List<MedicalRecord>> medicalRecordTypeReference = new TypeReference<>() {};

        InputStream personInputStream;
		try {
			personInputStream = new ClassPathResource("data/person.json").getInputStream();
			
	        InputStream firestationInputStream = new ClassPathResource("data/firestations.json").getInputStream();
	        InputStream medicalRecordInputStream = new ClassPathResource("data/medicalrecords.json").getInputStream();
	        
	        List<Person> persons = mapper.readValue(personInputStream, personTypeReference);
	        List<FireStation> firestations = mapper.readValue(firestationInputStream, firestationTypeReference);
	        List<MedicalRecord> medicalRecords = mapper.readValue(medicalRecordInputStream, medicalRecordTypeReference);
		


        // Save data to repositories
        personRepository.saveAll(persons);
        firestationRepository.saveAll(firestations);
        medicalRepository.saveAll(medicalRecords);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println("Data loaded successfully");
        return "Data loaded successfully";
    }
    
    public Map<String, Object> getPeopleUnderFireStation(int stationNumber){
    	
	    List<FireStation> firestations =   firestationRepository.findByStation(stationNumber);
		List<Person> persons = new ArrayList<>();
		
		for(FireStation  station : firestations) {
			String address = station.getAddress();
		    List<Person> people =  personRepository.findByAddress(address);
		    for(Person p : people) {
		    	MedicalRecord medicalRecord = medicalRepository.findByFirstNameAndLastName(p.getFirstName(), p.getLastName());
		    	int age = calculateAge(medicalRecord.getBirthdate());
		    	p.setAge(age);
		    }
		    persons.addAll(people);
		}
	    long numberOfAdults = persons.stream().filter(p -> p.getAge()>18).count();
	    long numberOfChildren = persons.stream().filter(p -> p.getAge()<=18).count();
	    
		Map<String, Object> response = new HashMap<>();
		response.put("persons", persons);
	    response.put("numberOfAdults", numberOfAdults);
	    response.put("numberOfChildren", numberOfChildren);
		
	    return response;
    }
    
	 public Map<String, Object> getChildrenAtAddress(String address) {
	    	
	    	List<ChildrenDTO> childrenDtoList = new ArrayList<>();
	    	List<Person> adults = new ArrayList<Person>();
	    	
	    	List<Person> people = personRepository.findByAddress(address);
	    	for(Person p : people) {
	       		  MedicalRecord medical =  medicalRepository.findByFirstNameAndLastName(p.getFirstName(), p.getLastName());
	    		  int age = calculateAge(medical.getBirthdate());
	  	    	   p.setAge(age);
	  	    	   if(age<=18) {
	  	    		 ChildrenDTO childrenDTO = new ChildrenDTO();
	  	    		 childrenDTO.setFirstName(p.getFirstName());
	  	    		 childrenDTO.setLastName(p.getLastName());
	  	    		 childrenDTO.setAge(p.getAge());
	  	    		childrenDtoList.add(childrenDTO);
	  	    	   }else {
	  	    		 adults.add(p);
	  	    	   }
	    	}
	    	
	    	Map<String, Object> response = new HashMap<>();
	    	if(!childrenDtoList.isEmpty()) {
		    	response.put("children", childrenDtoList);
		    	response.put("adults",adults);
	    	}	   	
	        return response;
	    }
	 
	 public List<String> getPhoneUnderFireStation(int firestation) {
	    	
	    	List<String> phoneNumbers = new ArrayList<String>();
	    	
	    	List<FireStation> firestations =   firestationRepository.findByStation(firestation);
	    	
	    	for(FireStation  station : firestations) {
	    		String address = station.getAddress();
	    	    List<Person> people =  personRepository.findByAddress(address);
	    	    for(Person p : people) {
	    	    	phoneNumbers.add(p.getPhone());
	    	    }
	    	}
	    	return phoneNumbers;
	    }
	 
	 public  Map<String,Object> getFireStationByAddress(String address) {
	    	
	    	List<PersonWithMedicationDTO> personWithMedicationDTOs = new ArrayList<>();
	    	
	    	FireStation firestation =   firestationRepository.findByAddress(address);
	    	
	    	List<Person> people = personRepository.findByAddress(address);
	    	
		    for(Person p : people) {
		    	MedicalRecord medicalRecord = medicalRepository.findByFirstNameAndLastName(p.getFirstName(), p.getLastName());
		    	int age = calculateAge(medicalRecord.getBirthdate());
		    	PersonWithMedicationDTO dto = new PersonWithMedicationDTO();
		    	dto.setFirstName(p.getFirstName());
		    	dto.setLastName(p.getLastName());
		    	dto.setPhoneNumber(p.getPhone());
		    	dto.setMedications(medicalRecord.getMedications());
		    	dto.setAllergies(medicalRecord.getAllergies());
		    	dto.setAge(age);
		    	personWithMedicationDTOs.add(dto);
		    }
		    
		    Map<String,Object> response = new HashMap<String, Object>();
		    response.put("firestationNumber", firestation.getStation());
		    response.put("people",personWithMedicationDTOs);
	    	return response;

	    }
	 
	  public  Map<String,List<PersonWithAddressDTO>> getPeopleByStation(List<Integer> stations) {
	    	
	    	List<PersonWithAddressDTO> personWithAddressDTOs = new ArrayList<>();
	    	
	    	for(Integer station : stations) {
	    	
	    		List<FireStation> firestations =   firestationRepository.findByStation(station);
	    		
	    		for(FireStation fireStation : firestations) {
	    			
	    			List<Person> people = personRepository.findByAddress(fireStation.getAddress());
	    			for(Person p : people) {
	    		    	MedicalRecord medicalRecord = medicalRepository.findByFirstNameAndLastName(p.getFirstName(), p.getLastName());
	    		    	int age = calculateAge(medicalRecord.getBirthdate());
	    		    	PersonWithAddressDTO dto = new PersonWithAddressDTO();
	    		    	dto.setFirstName(p.getFirstName());
	    		    	dto.setLastName(p.getLastName());
	    		    	dto.setPhoneNumber(p.getPhone());
	    		    	dto.setMedications(medicalRecord.getMedications());
	    		    	dto.setAllergies(medicalRecord.getAllergies());
	    		    	dto.setAge(age);
	    		    	dto.setPhoneNumber(p.getPhone());
	    		    	dto.setAddress(p.getAddress());
	    		    	personWithAddressDTOs.add(dto);
	    		    }
	    		}
	    	}
	    	Map<String,List<PersonWithAddressDTO>> response =personWithAddressDTOs.stream().collect(Collectors.groupingBy(p -> p.getAddress()));
	    	return response;

	    }
	  
	  public  List<PersonWithAddressEmailDTO> getPersonByName(String firstName, String lastName) {
	    	
	    	List<PersonWithAddressEmailDTO> personWithAddressEmailDTOs = new ArrayList<>();
	    	
	    	List<Person> people = personRepository.findByFirstNameAndLastName(firstName,lastName);
	    	
	    	for(Person p : people) {
		    	MedicalRecord medicalRecord = medicalRepository.findByFirstNameAndLastName(p.getFirstName(), p.getLastName());
		    	int age = calculateAge(medicalRecord.getBirthdate());
		    	PersonWithAddressEmailDTO dto = new PersonWithAddressEmailDTO();
		    	dto.setFirstName(p.getFirstName());
		    	dto.setLastName(p.getLastName());
		    	dto.setPhoneNumber(p.getPhone());
		    	dto.setMedications(medicalRecord.getMedications());
		    	dto.setAllergies(medicalRecord.getAllergies());
		    	dto.setAge(age);
		    	dto.setPhoneNumber(p.getPhone());
		    	dto.setAddress(p.getAddress());
		    	dto.setEmail(p.getEmail());
		    	personWithAddressEmailDTOs.add(dto);
		    }
	    	
	    	return personWithAddressEmailDTOs;

	    } 
	  
	  public  List<String> getAllEmailsInCity(String city) {

		  List<String> emails = new ArrayList<>();
	    	
	    	List<Person> people = personRepository.findByCity(city);
	    	
	    	for(Person p : people) {
		    	emails.add(p.getEmail());
		    }
	    	return emails;
	    }
    
    private int calculateAge(String dob) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try {
            LocalDate birthDate = LocalDate.parse(dob, formatter);
            return Period.between(birthDate, LocalDate.now()).getYears();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use MM/dd/yyyy format.", e);
     
        }
    }
} 
