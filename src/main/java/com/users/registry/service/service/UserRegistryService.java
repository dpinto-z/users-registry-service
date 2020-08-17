package com.users.registry.service.service;


import com.users.registry.service.domain.Phone;
import com.users.registry.service.domain.User;
import com.users.registry.service.domain.request.RegisterUserRequest;
import com.users.registry.service.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRegistryService {

    private final UserRepository userRepository;

    public User registerUser(RegisterUserRequest registerUserRequest) {
        log.info("action=registerUser");

        List<Phone> phones = registerUserRequest.getPhoneList();

        User user = User.builder()
                .name(registerUserRequest.getName())
                .email(registerUserRequest.getEmail())
                .password(registerUserRequest.getPassword())
                .token(UUID.randomUUID().toString())
                .createdDate(Date.valueOf(LocalDate.now()))
                .modifiedDate(Date.valueOf(LocalDate.now()))
                .lastLoginDate(Date.valueOf(LocalDate.now()))
                .phoneList(phones)
                .build();

        phones.forEach(phone -> phone.setUser(user));

        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        log.info("action=findAllUsers");
        return userRepository.findAll();
    }

    public Optional<User> findUserByEmail(String email) {
        log.info("action=findUserByEmail, email={}", email);
        return userRepository.findByEmail(email);
    }
}
