package com.rashmi.SafetyNet;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rashmi.SafetyNet.repositories.FireStationRepository;
import com.rashmi.SafetyNet.repositories.MedicalRecordRepository;
import com.rashmi.SafetyNet.repositories.PersonRepository;

@SpringBootTest
class SafetyNetApplicationTests {

	@Mock
    private FireStationRepository firestationRepository;
    
    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private MedicalRecordRepository medicalRepository;
	@Test
	void contextLoads() {
	}

}
