package com.example.ds2023.service;


import com.example.ds2023.config.JwtService;
import com.example.ds2023.model.entity.User;
import com.example.ds2023.model.helper.AuthenticationResponse;
import com.example.ds2023.model.helper.LoginDTO;
import com.example.ds2023.model.helper.UserDTO;
import com.example.ds2023.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginService {


    private final WebClient.Builder webClientBuilder;
    private final  UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;



    public AuthenticationResponse register(User user){


        Optional<User> existentUser = userRepository.findByEmail(user.getEmail());

        if(existentUser.isPresent()){
            return null;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        sendUserIdToDeviceMicroservice(user.getId());

        Optional<User> existentUserId = userRepository.findByEmail(user.getEmail());


        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

     private void sendUserIdToDeviceMicroservice(UUID id) {
        WebClient webClient = webClientBuilder.baseUrl("http://localhost:8081")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        webClient.post()
                .uri("/api/storeUserId")
                .body(BodyInserters.fromValue(id))
                .retrieve()
                .bodyToMono(Void.class)
                .block(); // Adjust as needed for non-blocking operations

    }

    public AuthenticationResponse login(LoginDTO loginDTO) {
//        String email = loginDTO.getEmail();
//        User user = userRepository.findByEmail(email).orElse(null);
//        if (null == user) {
//            return "this user do not exists";
//        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );
                var user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow();
                var userId = user.getId();
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder().token(jwtToken).userId(userId).build();
                }

                //method to make the conversion between userDTO and user entity
//    private User convertToUserEntity(UserDTO userDTO) {
//        return new User(
//                userDTO.getName(),
//                userDTO.getEmail(),
//                passwordEncoder.encode(userDTO.getPassword()),
//                userDTO.getRole()
//        );
//    }

                }
