package com.rashmi.SafetyNet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rashmi.SafetyNet.resources.MedicalRecord;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    MedicalRecord findByFirstNameAndLastName(String firstName, String lastName);
}
