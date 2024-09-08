package com.rashmi.SafetyNet.controller;

import java.util.List;
import java.util.Optional;

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

import com.rashmi.SafetyNet.repositories.PersonRepository;
import com.rashmi.SafetyNet.resources.Person;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/{firstName}/{lastName}")
    public List<Person> getPerson(@PathVariable("firstName") String firstName,@PathVariable("lastName") String lastName) {
    	
    	 return personRepository.findByFirstNameAndLastName(firstName,lastName);
    }
    
    @PostMapping
    public Person addPerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @PutMapping
    public Person updatePerson(@RequestBody Person person) {
        Optional<Person> existingPerson = personRepository.findById(person.getId());
        if (existingPerson.isPresent()) {
            Person updatedPerson = existingPerson.get();
            updatedPerson.setAddress(person.getAddress());
            updatedPerson.setCity(person.getCity());
            updatedPerson.setPhone(person.getPhone());
            updatedPerson.setEmail(person.getEmail());
            return personRepository.save(updatedPerson);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found");
        }
    }

    @DeleteMapping
    public void deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        List<Person> persons = personRepository.findByFirstNameAndLastName(firstName, lastName);
        if (!persons.isEmpty()) {
            personRepository.deleteAll(persons);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found");
        }
    }
}
