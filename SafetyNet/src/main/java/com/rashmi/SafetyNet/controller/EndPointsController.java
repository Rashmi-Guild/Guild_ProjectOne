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
public class EndPointsController {
  
   
    @Autowired
    private EndpointsService service;
    
    @GetMapping("/loadData")
    public String loadData() {
    	service.loadData();
    	
   	 return "Data Loaded succesfully in H2 DB";
   }
}
