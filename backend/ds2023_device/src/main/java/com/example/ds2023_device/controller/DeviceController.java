package com.example.ds2023_device.controller;

import com.example.ds2023_device.entity.model.Device;
import com.example.ds2023_device.entity.model.UserId;
import com.example.ds2023_device.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;


    @PostMapping("/insertDevice")
    public UUID insertDevice(@RequestBody Device device){
        return deviceService.insertDevice(device);

        //return ResponseEntity.ok("Device inserted");
    }

    @GetMapping("/seeAllDevices")
    public ResponseEntity<List<Device>> getAllDevices(){

        List<Device> allDevices = deviceService.getAllDevices();

        return ResponseEntity.ok(allDevices);
    }



    @GetMapping("/seeAllUserDevices/{userId}")
    public ResponseEntity<List<Device>> getAllUserDevices(@PathVariable("userId") UUID userId){

        List<Device> allDevices = deviceService.getAllUserDevices(userId);

        return ResponseEntity.ok(allDevices);
    }

    @PutMapping("/updateDevice/{id}")
    public ResponseEntity<String> updateDevice(@PathVariable("id") UUID deviceId,
                                               @RequestBody Device updatedDevice){
        boolean updated = deviceService.updateDevice(deviceId, updatedDevice);

        if(updated){
            return ResponseEntity.ok("device updated");
        }
        return ResponseEntity.ok("device not found");
    }

    @DeleteMapping("/deleteDevice/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable("id") UUID deviceId){

        boolean deletedDevice = deviceService.deleteDevice(deviceId);
        if(deletedDevice){
            return ResponseEntity.ok("device deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Device not found");
    }


}
