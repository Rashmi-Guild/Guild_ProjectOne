package com.rashmi.SafetyNet.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import com.rashmi.SafetyNet.repositories.FireStationRepository;
import com.rashmi.SafetyNet.resources.FireStation;

@RestController
@RequestMapping("/firestation")
public class FireStationController {
	
	private static final Logger logger = LogManager.getLogger(FireStationController.class);
	
    @Autowired
    private FireStationRepository firestationRepository;
    
    @GetMapping("/{stationNumber}")
    public List<FireStation> getFireStation(@PathVariable("stationNumber") int stationNumber) {
    	
    	 return firestationRepository.findByStation(stationNumber);
    }
   
    @PostMapping
    public FireStation addFirestation(@RequestBody FireStation firestation) {
        return firestationRepository.save(firestation);
    }

    @PutMapping
    public FireStation updateFirestation(@RequestBody FireStation firestation) {
        Optional<FireStation> existingFirestation = firestationRepository.findById(firestation.getId());
        if (existingFirestation.isPresent()) {
        	FireStation updatedFirestation = existingFirestation.get();
            updatedFirestation.setStation(firestation.getStation());
            updatedFirestation.setAddress(firestation.getAddress());
            
            return firestationRepository.save(updatedFirestation);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Firestation not found");
        }
    }

    @DeleteMapping
    public void deleteFirestation(@RequestParam String address) {
    	FireStation firestation = firestationRepository.findByAddress(address);
        if (firestation != null) {
            firestationRepository.delete(firestation);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Firestation not found");
        }
    }
}
