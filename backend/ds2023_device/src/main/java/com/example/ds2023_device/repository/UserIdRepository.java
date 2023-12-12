package com.example.ds2023_device.repository;

import com.example.ds2023_device.entity.model.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserIdRepository extends JpaRepository<UserId, UUID> {

}
