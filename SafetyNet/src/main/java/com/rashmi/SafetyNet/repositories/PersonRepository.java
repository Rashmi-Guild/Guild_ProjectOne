package com.rashmi.SafetyNet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rashmi.SafetyNet.resources.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByFirstNameAndLastName(String firstName, String lastName);
    List<Person> findByAddress(String address);
    List<Person> findByCity(String city);
}
