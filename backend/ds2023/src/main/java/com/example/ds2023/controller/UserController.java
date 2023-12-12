package com.example.ds2023.controller;

import com.example.ds2023.model.entity.User;
import com.example.ds2023.model.helper.Role;
import com.example.ds2023.model.helper.UserDTO;
import com.example.ds2023.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/userDetails")
    public ResponseEntity<UserDTO> details(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws Exception {
        String authToken = authHeader.substring(7); // Extract token from header
        return ResponseEntity.ok(userService.getUser(authToken));
    }

    @PostMapping("/insertUser")
    public ResponseEntity<String> insertUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                             @RequestBody User user) throws Exception {
        String authToken = authHeader.substring(7);
        UserDTO userDTO = userService.getUser(authToken);

        if(userDTO != null && userDTO.getRole() == Role.ADMIN){
            userService.insertUser(user);
            return ResponseEntity.ok("User created successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not have sufficient privileges");
    }

    @GetMapping("/seeAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws Exception{
        String authToken = authHeader.substring(7);
        UserDTO userDTO = userService.getUser(authToken);

        if(userDTO != null && userDTO.getRole() == Role.ADMIN){
            List<UserDTO> allUsers = userService.getAllUsers();

            return ResponseEntity.ok(allUsers);
        }
        return null;
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") UUID userId,
                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                             @RequestBody User updatedUser) throws Exception{
        String authToken = authHeader.substring(7);
        UserDTO userDTO = userService.getUser(authToken);

        if(userDTO != null && userDTO.getRole() == Role.ADMIN) {
            boolean updated = userService.updateUser(userId, updatedUser);
            if(updated){
                return ResponseEntity.ok("User updated");
            }
            return ResponseEntity.ofNullable("User not found");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not have sufficient privileges");
    }

    // when performing get/delete send the id in url nor as request body

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") UUID userId,
                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws Exception{
        String authToken = authHeader.substring(7);
        UserDTO userDTO = userService.getUser(authToken);

        if(userDTO != null && userDTO.getRole() == Role.ADMIN){
            boolean deleted = userService.deleteUser(userId);
            if(deleted){
                return ResponseEntity.ok("user deleted");
            }
            return ResponseEntity.ofNullable("User not found");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not have sufficient privileges");
    }
}

