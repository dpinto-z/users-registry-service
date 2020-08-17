package com.users.registry.service.controller;


import com.users.registry.service.domain.User;
import com.users.registry.service.domain.request.RegisterUserRequest;
import com.users.registry.service.exception.RegisterNotAllowedException;
import com.users.registry.service.service.UserRegistryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@Api(value = "User Controller", description = "CRUD Users")
@RestController
@RequestMapping("/user-management")
@RequiredArgsConstructor
public class UserRegisterController {
    private final UserRegistryService userRegistryService;

    @ApiOperation(value = "Post User to register in User repository")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User successfully persisted"),
            @ApiResponse(code = 422, message = "Un-processable entity"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(value = "/registry", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @ApiOperation(value = "Get All Users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of Users successfully retrieved"),
            @ApiResponse(code = 422, message = "Un-processable entity"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("action=getAllUsers");
        ResponseEntity<List<User>> responseEntity = new ResponseEntity<>(
                userRegistryService.findAllUsers(), HttpStatus.OK);
        log.info("action=getAllUsersSuccess, size={}");
        return responseEntity;
    }


}
