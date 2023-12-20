package com.example.communication.repository;

import com.example.communication.entity.Measurement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {

    List<Measurement> findAllByDeviceId(UUID uuid);

    List<Measurement> findAllByDeviceIdAndTimestampBetween(UUID deviceId, Long startDate,Long endDate);

    List<Measurement> findByDeviceId(UUID deviceId);
}
