package com.example.ds2023.controller;

import com.example.ds2023.model.entity.User;
import com.example.ds2023.model.helper.AuthenticationResponse;
import com.example.ds2023.model.helper.LoginDTO;
import com.example.ds2023.model.helper.UserDTO;
import com.example.ds2023.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class LoginController {



    private final LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User user) {

        return ResponseEntity.ok(loginService.register(user));
    }

//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponse> register(@RequestBody LoginDTO loginDTO) {
//        return ResponseEntity.ok(loginService.login(loginDTO));
//
//    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginDTO loginDTO) {
        AuthenticationResponse authenticationResponse = loginService.login(loginDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationResponse.getToken());
        return ResponseEntity.ok().headers(headers).body(authenticationResponse);
    }

}