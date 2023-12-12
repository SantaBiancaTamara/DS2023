package com.example.ds2023_device.controller;

import com.example.ds2023_device.entity.model.UserId;
import com.example.ds2023_device.repository.UserIdRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserIdController {

    @Autowired
    private UserIdRepository userIdRepository;

    @PostMapping("/storeUserId")
    public ResponseEntity<Void> storeUserId(@RequestBody UUID userId){
        UserId databaseUserId = new UserId();
        databaseUserId.setUserId(userId);

        userIdRepository.save(databaseUserId);
        return ResponseEntity.ok().build();
    }


}
