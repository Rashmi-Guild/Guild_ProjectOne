package com.rashmi.SafetyNet.dto;

import java.util.List;

public class PersonWithAddressDTO extends PersonWithMedicationDTO{

	 private String address;
    
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
    
	public PersonWithAddressDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PersonWithAddressDTO(String firstName, String lastName, String phoneNumber, int age,List<String> medications,List<String> allergies, String address ){
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setPhoneNumber(phoneNumber);
		this.setAge(age);
		this.setMedications(medications);
	    this.setAllergies(allergies);	
	    this.address = address;
	}
    
	
}
