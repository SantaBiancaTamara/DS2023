package com.example.communication.controller;


import com.example.communication.dto.HourlyConsumptionPair;
import com.example.communication.entity.Measurement;
import com.example.communication.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/measurement")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials="true")
public class MeasurementController {

    private final MeasurementService measurementService;

    @GetMapping("/graphData/{timestamp}/{userId}")
    public List<HourlyConsumptionPair> getAllDeviceMeasurements(@PathVariable("timestamp") String timestamp, @PathVariable("userId") UUID userId) throws ParseException {
        List<HourlyConsumptionPair> allMeasurements = measurementService.getAllDeviceMeasurements(timestamp, userId);
        return allMeasurements;
    }
}
