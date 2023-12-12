package com.example.ds2023.service;

import com.example.ds2023.config.JwtService;
import com.example.ds2023.model.entity.User;
import com.example.ds2023.model.helper.Role;
import com.example.ds2023.model.helper.UserDTO;
import com.example.ds2023.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final WebClient.Builder webClientBuilder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    public UserDTO getUser(String auth) throws Exception {
        String userEmail = jwtService.extractUsername(auth);

        Optional<User> databaseUser = userRepository.findByEmail(userEmail);

        if(databaseUser.isPresent()){
            UserDTO userDTO = new UserDTO(databaseUser.get());
            return userDTO;
        } else {
            return null;
        }

        //throw new UsernameNotFoundException("could not find the user in database");
        }

    public String insertUser(User user) {

        Optional<User> databaseUser = userRepository.findByEmail(user.getEmail());

        if(databaseUser.isPresent()){
            return "Existent user!";
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            sendUserIdToDeviceMicroservice(user.getId());
            return "User inserted!";
        }

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

    public List<UserDTO> getAllUsers() {
        List<User> databaseUsers = userRepository.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();

        for(User user : databaseUsers) {
            if(user.getRole() == Role.USER){
                UserDTO userDTO = new UserDTO();
                userDTO.setId(user.getId());
                userDTO.setName(user.getName());
                userDTO.setEmail(user.getEmail());
                userDTO.setRole(user.getRole());

                usersDTO.add(userDTO);

            }
        }
        return usersDTO;
    }

    @Transactional
    public boolean updateUser(UUID userId, User updatedUser) {
        User userToUpdate = userRepository.getReferenceById(userId);

        if(userToUpdate != null){
            userToUpdate.setName(updatedUser.getName());
            userToUpdate.setEmail(updatedUser.getEmail());
            userToUpdate.setPassword(updatedUser.getPassword());

            userRepository.save(userToUpdate);
            return true;
        }
        return false;
    }

    public boolean deleteUser(UUID userId) {
        User userToDelete = userRepository.getReferenceById(userId);

        if(userToDelete != null){
            userRepository.delete(userToDelete);
            return true;
        }
        return false;
    }
}