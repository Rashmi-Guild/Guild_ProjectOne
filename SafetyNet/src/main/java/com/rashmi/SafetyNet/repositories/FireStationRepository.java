package com.rashmi.SafetyNet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rashmi.SafetyNet.resources.FireStation;

@Repository
public interface FireStationRepository extends JpaRepository<FireStation, Long> {
	
    FireStation findByAddress(String address);
    List<FireStation> findByStation(int station);
}