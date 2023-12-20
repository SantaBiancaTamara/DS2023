package com.example.communication;

import com.example.communication.entity.Device;
import com.example.communication.entity.HourlyConsumption;
import com.example.communication.entity.Measurement;
import com.example.communication.repository.DeviceRepository;
import com.example.communication.repository.HourlyConsumptionRepository;
import com.example.communication.repository.MeasurementRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageReceiver {

    private final MeasurementRepository measurementRepository;
    private final DeviceRepository deviceRepository;
    private final HourlyConsumptionRepository hourlyConsumptionRepository;


    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String SMART_METER_DATA_QUEUE = "smart_meter_data";
    private final String DEVICES_QUEUE = "devices";

    public void startListening() throws Exception {
        System.out.println("hello");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("woodpecker.rmq.cloudamqp.com"); // Replace with your CloudAMQP server details
        factory.setPort(5672); // CloudAMQP typically uses port 5672 for non-SSL connections
        factory.setUsername("eqxfpanf");
        factory.setPassword("9vubXoJXNd2kRDWRfpOJ8M6-P7Bwhp_v");
        factory.setVirtualHost("eqxfpanf");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        channel.queueDeclare(SMART_METER_DATA_QUEUE, false, false, false, null);
        channel.queueDeclare(DEVICES_QUEUE, true, false, false, null);

        System.out.println("Waiting for messages...");

        DeliverCallback deviceDeliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            processDeviceMessageDevice(message);
        };

        channel.basicConsume(DEVICES_QUEUE, true, deviceDeliverCallback, consumerTag -> {});


        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Received: " + message);

            // Process the received message (compute energy consumption, store in DB, etc.)
            processMessageSimulator(message);
        };

        channel.basicConsume(SMART_METER_DATA_QUEUE, true, deliverCallback, consumerTag -> {
        });

    }

//   @RabbitListener(queues = {"devices"})
    private void processDeviceMessageDevice(String message) throws JsonProcessingException {
        // Logic to process device messages
        Device device = objectMapper.readValue(message, Device.class);
        deviceRepository.save(device);
        System.out.println(deviceRepository.findById(device.getId()));
    }

//   @RabbitListener(queues = {"smart_meter_data"})
    private void processMessageSimulator(String message) throws JsonProcessingException {
        // Logic to process measurement messages
        Measurement measurement = objectMapper.readValue(message, Measurement.class);
        measurementRepository.save(measurement);


        List<Measurement> dbMeasurements = measurementRepository.findByDeviceId(measurement.getDeviceId());

        Float total = 0f;

        if (dbMeasurements.size() >= 6) {
            int fromIndex = dbMeasurements.size() - 6; // Set the start index
            int toIndex = dbMeasurements.size(); // Set the end index (exclusive)

            for (Measurement m : dbMeasurements.subList(fromIndex, toIndex)) {
                total += m.getMeasurementValue();
            }

            total = total/6;

            HourlyConsumption hourlyConsumption = new HourlyConsumption();
            hourlyConsumption.setDeviceId(measurement.getDeviceId());
            hourlyConsumption.setHourlyConsumption(total);

            //store the hourly consumption in the db
            hourlyConsumptionRepository.save(hourlyConsumption);

            // Rest of your processing logic
        }
    }
}
