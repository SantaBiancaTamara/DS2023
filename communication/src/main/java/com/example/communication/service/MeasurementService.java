package com.example.communication.service;

import com.example.communication.dto.HourlyConsumptionPair;
import com.example.communication.entity.Device;
import com.example.communication.entity.HourlyConsumption;
import com.example.communication.entity.Measurement;
import com.example.communication.repository.DeviceRepository;
import com.example.communication.repository.MeasurementRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final DeviceRepository deviceRepository;

    public List<HourlyConsumptionPair> getAllDeviceMeasurements(String timestamp, UUID userId) throws ParseException {
        // Convert the date string to a Timestamp
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(timestamp);
        Timestamp startOfDay = new Timestamp(date.getTime());
        Timestamp endOfDay = new Timestamp(date.getTime() + 24 * 60 * 60 * 1000 - 1);

        List<Device> devices = getAllUserDevices(userId);

        Float[] hourlyConsumption = new Float[24];

        Arrays.fill(hourlyConsumption, 0.0f);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");


        for(Device device : devices) {
            List<Measurement> measurements = measurementRepository.findAllByDeviceIdAndTimestampBetween(device.getId(), startOfDay.getTime(), endOfDay.getTime());

            for (Measurement measurement : measurements) {
                String hour = simpleDateFormat.format(measurement.getTimestamp());
                int hourIndex = Integer.parseInt(hour);
                if (hourlyConsumption[hourIndex] == 0.0f) {
                    hourlyConsumption[hourIndex] = measurement.getMeasurementValue();
                } else {
                    hourlyConsumption[hourIndex] += measurement.getMeasurementValue();
                }
            }
        }

        // Convert the array to an array of JSON values
        List<HourlyConsumptionPair> pairs = new ArrayList<>();
        for (int i = 0; i < hourlyConsumption.length; i++) {
            HourlyConsumptionPair pair = new HourlyConsumptionPair(String.valueOf(i), hourlyConsumption[i]);
            pairs.add(pair);
        }

        return pairs;
    }




    public List<Device>getAllUserDevices(UUID userId){
        List<Device> devices =  deviceRepository.findAllByUserId(userId);
        return devices;
    }



        private final ObjectMapper objectMapper = new ObjectMapper();

//        public String getTotalConsumptionPerHourForTimestampAndDevice(String timestamp, UUID deviceId) {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            LocalDateTime dateTime = LocalDateTime.parse(timestamp, formatter);
//
//            LocalDateTime startDateTime = dateTime.withHour(0).withMinute(0).withSecond(0);
//            LocalDateTime endDateTime = dateTime.withHour(23).withMinute(59).withSecond(59);
//
//            List<Measurement> measurements = measurementRepository.findAllByDeviceIdAndTimestampBetween(
//                    deviceId, startDateTime.toEpochSecond(), endDateTime.toEpochSecond());
//
//            Map<Integer, Float> consumptionPerHour = new HashMap<>();
//
//            for (Measurement measurement : measurements) {
//                LocalDateTime measurementDateTime = LocalDateTime.ofEpochSecond(
//                        measurement.getTimestamp(), 0, java.time.ZoneOffset.UTC);
//
//                int hour = measurementDateTime.getHour();
//                float measurementValue = measurement.getMeasurementValue();
//
//                consumptionPerHour.put(hour, consumptionPerHour.getOrDefault(hour, 0f) + measurementValue);
//            }
//
//            String jsonResult = null;
//            try {
//                jsonResult = objectMapper.writeValueAsString(consumptionPerHour);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace(); // Handle or log the exception appropriately
//            }
//
//            return jsonResult;
//        }





 }





