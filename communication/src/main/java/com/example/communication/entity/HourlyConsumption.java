package com.example.communication.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hourly_consumption")
public class HourlyConsumption {

    @Id
    @Column(name= "device_id", columnDefinition = "BINARY(16)")
    private UUID deviceId;

    private Float hourlyConsumption;


}
