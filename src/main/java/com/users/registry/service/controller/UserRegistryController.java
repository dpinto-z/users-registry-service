package com.users.registry.service.controller;

import com.users.registry.service.domain.User;
import com.users.registry.service.domain.request.AuthenticationRequest;
import com.users.registry.service.domain.request.RegisterUserRequest;
import com.users.registry.service.domain.request.UpdateRequest;
import com.users.registry.service.exception.RegisterNotAllowedException;
import com.users.registry.service.service.UserRegistryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

    @Slf4j
    @RestController
    @RequestMapping("/user-management")
    @RequiredArgsConstructor
    public class UserRegistryController {
        private final UserRegistryService userRegistryService;


        @Operation(summary = "Post User to register in User repository",
                description= "Post User to register in User repository")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "User successfully persisted"),
                @ApiResponse(responseCode = "422", description = "Un-processable entity"),
                @ApiResponse(responseCode = "500", description = "Internal server error")})
        @RequestMapping(value = "/user-registry", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<User> registerUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
            log.info("action=registerUser, registerUserRequest={}", registerUserRequest);

            Optional<User> userByEmail = userRegistryService.findUserByEmail(registerUserRequest.getEmail());

            if (userByEmail.isPresent()) {
                throw new RegisterNotAllowedException("User already registered email=" + registerUserRequest.getEmail());
            }

            ResponseEntity<User> responseEntity = new ResponseEntity<>(
                    userRegistryService.registerUser(registerUserRequest), HttpStatus.CREATED);
            log.info("action=registerUserSuccessfully, registerUserRequest={}", registerUserRequest);
            return responseEntity;
        }

        @PostMapping("/refresh-token")
        public ResponseEntity<User> refreshUserToken(
                @RequestBody AuthenticationRequest request) {
                User authenticatedUser = userRegistryService.refreshUserToken(request);
            return ResponseEntity.ok(authenticatedUser);
        }


        @Operation(summary = "Get All Users",
                description= "Get All Users")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "List of Users successfully retrieved"),
                @ApiResponse(responseCode = "422", description = "Un-processable entity"),
                @ApiResponse(responseCode = "500", description = "Internal server error")})
        @RequestMapping(value = "/all-users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<List<User>> getAllUsers() {
            log.info("action=getAllUsers");
            ResponseEntity<List<User>> responseEntity = new ResponseEntity<>(
                    userRegistryService.findAllUsers(), HttpStatus.OK);
            log.info("action=getAllUsersSuccess, size={}");
            return responseEntity;
        }

        @RequestMapping(value="/update-user", method = RequestMethod.PUT)
        public ResponseEntity<User> updateUser(@RequestBody UpdateRequest request) {
            log.info("action=updateUser");
            User updatedUser = userRegistryService.updateUser(request);
            return  ResponseEntity.ok(updatedUser);
        }

        @RequestMapping(value="/delete-user", method = RequestMethod.DELETE)
        public ResponseEntity<User> echoDelete(@RequestBody AuthenticationRequest request) {
            User deletedUser = userRegistryService.deleteUser(request);
            return ResponseEntity.ok(deletedUser);
        }
}
