package com.users.registry.service.service;

import com.users.registry.service.domain.Phone;
import com.users.registry.service.domain.User;
import com.users.registry.service.domain.request.AuthenticationRequest;
import com.users.registry.service.domain.request.RegisterUserRequest;
import com.users.registry.service.domain.request.UpdateRequest;
import com.users.registry.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserRegistryService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public User registerUser(RegisterUserRequest registerUserRequest) {
        log.info("action=registerUser");

        List<Phone> phones = registerUserRequest.getPhoneList();
        User user = User.builder()
                .name(registerUserRequest.getName())
                .email(registerUserRequest.getEmail())
                .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                .createdDate(Date.valueOf(LocalDate.now()))
                .modifiedDate(Date.valueOf(LocalDate.now()))
                .lastLoginDate(Date.valueOf(LocalDate.now()))
                .phoneList(phones)
                .build();
        String jwtToken = jwtService.generateToken(user);
        user.setToken(jwtToken);
        phones.forEach(phone -> phone.setUsers(user));

        return userRepository.save(user);
    }

    public User refreshUserToken(AuthenticationRequest request) {
        log.info("action=refreshUserToken");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        User user = userRepository.findByEmail(
                request.getEmail()).orElseThrow(() ->
                new IllegalArgumentException("Invalid email or password."));
        String newJwtToken = jwtService.generateToken(user);
        user.setToken(newJwtToken);
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        log.info("action=findAllUsers");
        return userRepository.findAll();
    }

    public User updateUser(UpdateRequest request){
        log.info("action=updateUser");
        User currentUser = userRepository.findByEmail(
                request.getEmail()).orElseThrow(() ->
                new IllegalArgumentException("User not found"));

        List<Phone> phones = request.getPhoneList();
        currentUser.setName(request.getName());
        currentUser.setPassword(passwordEncoder.encode(request.getPassword()));
        currentUser.setPhoneList(phones);
        phones.forEach(phone -> phone.setUsers(currentUser));
        userRepository.save(currentUser);
        return currentUser;
    }

    public User deleteUser(AuthenticationRequest request) {
        log.info("action=deleteUser");
        User user = userRepository.findByEmail(
                request.getEmail()).orElseThrow(() ->
                new IllegalArgumentException("User not found"));
         userRepository.delete(user);
         return user;
    }

    public Optional<User> findUserByEmail(String email) {
        log.info("action=findUserByEmail, email={}", email);
        return userRepository.findByEmail(email);
    }
}
