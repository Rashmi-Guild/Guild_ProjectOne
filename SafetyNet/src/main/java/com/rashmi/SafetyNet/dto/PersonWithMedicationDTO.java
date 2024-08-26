package com.rashmi.SafetyNet.dto;

import java.util.List;

public class PersonWithMedicationDTO extends ChildrenDTO{

	private String phoneNumber;
    private List<String> medications;
    private List<String> allergies;
    
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public List<String> getMedications() {
		return medications;
	}
	public void setMedications(List<String> medications) {
		this.medications = medications;
	}
	public List<String> getAllergies() {
		return allergies;
	}
	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}
	
	
    
	public PersonWithMedicationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PersonWithMedicationDTO(String firstName, String lastName, String phoneNumber, int age,List<String> medications,List<String> allergies ){
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.phoneNumber = phoneNumber;
		this.setAge(age);
		this.setMedications(medications);
	    this.setAllergies(allergies);	
	}
	
}
