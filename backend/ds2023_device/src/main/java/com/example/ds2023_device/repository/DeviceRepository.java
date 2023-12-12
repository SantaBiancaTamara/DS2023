package com.example.ds2023_device.repository;

import com.example.ds2023_device.entity.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {

    Optional<Device> findById(UUID uuid);
    List<Device> findAll();

    List<Device> findAllByUserId(UUID userId);

    void delete(Device deviceToDelete);
}
