package com.example.ds2023_device.service;


import com.example.ds2023_device.entity.model.Device;
import com.example.ds2023_device.entity.model.UserId;
import com.example.ds2023_device.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public UUID insertDevice(Device device){
        deviceRepository.save(device);
        return device.getId();
    }

    public List<Device> getAllDevices(){
        List<Device> databaseDevices = deviceRepository.findAll();
         return databaseDevices;
    }

    public boolean updateDevice(UUID deviceId, Device device){
        Optional<Device> deviceToUpdate = deviceRepository.findById(deviceId);

        if(deviceToUpdate.isPresent()){
            Device existingDevice = deviceToUpdate.get();
            existingDevice.setDescription(device.getDescription());
            existingDevice.setAddress(device.getAddress());
            existingDevice.setEnergyTime(device.getEnergyTime());
            existingDevice.setUserId(device.getUserId());

            deviceRepository.save(existingDevice);
            return true;
        }
        return false;
    }

    public boolean deleteDevice(UUID deviceId) {
        Optional<Device> deviceToDelete = deviceRepository.findById(deviceId);

        if(deviceToDelete.isPresent()){
            deviceRepository.delete(deviceToDelete.get());
            return true;
        }
        return false;
    }

    public List<Device> getAllUserDevices(UUID userId) {
        return deviceRepository.findAllByUserId(userId);
    }
}
