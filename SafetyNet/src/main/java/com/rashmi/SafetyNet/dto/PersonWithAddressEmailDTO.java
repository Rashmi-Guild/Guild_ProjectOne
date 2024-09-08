package com.rashmi.SafetyNet.dto;

import java.util.List;

public class PersonWithAddressEmailDTO extends PersonWithAddressDTO{

	 private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public PersonWithAddressEmailDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PersonWithAddressEmailDTO(String firstName, String lastName, String phoneNumber, int age,List<String> medications,List<String> allergies,String address, String email ){
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setPhoneNumber(phoneNumber);
		this.setAge(age);
		this.setMedications(medications);
	    this.setAllergies(allergies);	
	    this.setAddress(address);
	    this.email = email;
	}
    
}
