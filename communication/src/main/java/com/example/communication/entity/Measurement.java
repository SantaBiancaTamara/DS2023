package com.example.communication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "measurement")
public class Measurement {


    private Long timestamp;
    private UUID deviceId;
    private Float measurementValue;

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name= "id", columnDefinition = "BINARY(16)")
    private UUID id;


}
