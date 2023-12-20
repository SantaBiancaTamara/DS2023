package com.example.communication.repository;

import com.example.communication.entity.HourlyConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HourlyConsumptionRepository extends JpaRepository<HourlyConsumption,UUID> {

}
