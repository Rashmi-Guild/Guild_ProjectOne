package com.rashmi.SafetyNet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rashmi.SafetyNet.repositories.MedicalRecordRepository;
import com.rashmi.SafetyNet.resources.MedicalRecord;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @GetMapping("/{firstName}/{lastName}")
    public MedicalRecord getMedicalRecord(@PathVariable("firstName") String firstName,@PathVariable("lastName") String lastName) {
    	
    	 return medicalRecordRepository.findByFirstNameAndLastName(firstName,lastName);
    }
    
    @PostMapping
    public MedicalRecord addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    @PutMapping
    public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        MedicalRecord existingMedicalRecord = medicalRecordRepository.findByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());
        if (existingMedicalRecord != null) {
            existingMedicalRecord.setMedications(medicalRecord.getMedications());
            existingMedicalRecord.setAllergies(medicalRecord.getAllergies());
            return medicalRecordRepository.save(existingMedicalRecord);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medical Record not found");
        }
    }

    @DeleteMapping
    public void deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName);
        if (medicalRecord != null) {
            medicalRecordRepository.delete(medicalRecord);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medical Record not found");
        }
    }
}
