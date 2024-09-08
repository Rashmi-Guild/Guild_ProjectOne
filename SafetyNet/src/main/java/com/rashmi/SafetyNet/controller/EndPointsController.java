package com.rashmi.SafetyNet.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rashmi.SafetyNet.dto.PersonWithAddressDTO;
import com.rashmi.SafetyNet.dto.PersonWithAddressEmailDTO;
import com.rashmi.SafetyNet.service.EndpointsService;

@RestController
@RequestMapping("/safetyNet")
public class EndPointsController {
  
   
    @Autowired
    private EndpointsService service;
    
    @GetMapping("/loadData")
    public String loadData() {
    	service.loadData();
    	
   	 return "Data Loaded succesfully in H2 DB";
   }
    
    @GetMapping("/firestation")
    public Map<String, Object> getPeopleUnderFireStation(@RequestParam(name = "stationNumber", required = false, defaultValue = "1") int stationNumber) {
    	
    	 return service.getPeopleUnderFireStation(stationNumber);
    }
    
    @GetMapping("/childAlert")
    public Map<String, Object> getChildrenAtAddress(@RequestParam String address) {
    	
        return service.getChildrenAtAddress(address);
    }
    
    @GetMapping("/phoneAlert")
    public List<String> getPhoneUnderFireStation(@RequestParam int firestation) {
    	
    	return service.getPhoneUnderFireStation(firestation);
    }
    
    @GetMapping("/fire")
    public  Map<String, Object> getFireStationByAddress(@RequestParam String address) {
    	
    	return service.getFireStationByAddress(address);

    }
    
    //http://localhost:8080/flood/stations?stations=<a list of station_numbers>
    @GetMapping("/flood/stations")
    public  Map<String,List<PersonWithAddressDTO>> getPeopleByStation(@RequestParam List<Integer> stations) {
    	
       	return service.getPeopleByStation(stations);

    }
    
    @GetMapping("/personInfo")
    public  List<PersonWithAddressEmailDTO> getPersonByName(@RequestParam String firstName, @RequestParam String lastName) {
    	
    	return service.getPersonByName(firstName,lastName);
    }
    
    @GetMapping("/communityEmail")
    public  List<String> getAllEmailsInCity(@RequestParam String city) {

    	return service.getAllEmailsInCity(city);

    }
}
