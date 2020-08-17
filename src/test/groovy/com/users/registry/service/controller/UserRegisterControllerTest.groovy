package com.users.registry.service.controller

import com.users.registry.service.domain.Phone
import com.users.registry.service.domain.User
import com.users.registry.service.domain.request.RegisterUserRequest
import com.users.registry.service.exception.RegisterNotAllowedException
import com.users.registry.service.service.UserRegistryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import java.sql.Date
import java.time.LocalDate

class UserRegisterControllerTest extends Specification {

    UserRegisterController userRegisterController
    UserRegistryService userRegistryService

    def setup() {
        userRegistryService = Mock(UserRegistryService)
        userRegisterController = new UserRegisterController(userRegistryService)
    }

    def "User should be correctly register"() {
        given:

        def request = RegisterUserRequest.builder()
                .email("someemail@domain.com")
                .name("John Doe")
                .password("youDontKnow10")
                .phoneList(
                [Phone.builder()
                         .cityCode("1")
                         .countryCode("11")
                         .number("11111111")
                         .build(),
                 Phone.builder()
                         .cityCode("2")
                         .countryCode("22")
                         .number("22222222")
                         .build()
                ])
                .build()
        def expectedUser = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .token(UUID.randomUUID().toString())
                .createdDate(Date.valueOf(LocalDate.now()))
                .modifiedDate(Date.valueOf(LocalDate.now()))
                .lastLoginDate(Date.valueOf(LocalDate.now()))
                .phoneList(request.getPhoneList())
                .build()

        userRegistryService.findUserByEmail(_ as String) >> Optional.empty()

        userRegistryService.registerUser(request) >> expectedUser

        when:
        def response = userRegisterController.registerUser(request)

        then:
        response == new ResponseEntity(expectedUser, HttpStatus.CREATED)
    }

    def "User already register"() {
        given:

        def request = RegisterUserRequest.builder()
                .email("someemail@domain.com")
                .name("John Doe")
                .password("youDontKnow10")
                .phoneList(
                [Phone.builder()
                         .cityCode("1")
                         .countryCode("11")
                         .number("11111111")
                         .build(),
                 Phone.builder()
                         .cityCode("2")
                         .countryCode("22")
                         .number("22222222")
                         .build()
                ])
                .build()
        def expectedUser = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .token(UUID.randomUUID().toString())
                .createdDate(Date.valueOf(LocalDate.now()))
                .modifiedDate(Date.valueOf(LocalDate.now()))
                .lastLoginDate(Date.valueOf(LocalDate.now()))
                .phoneList(request.getPhoneList())
                .build()

        userRegistryService.findUserByEmail(_ as String) >> Optional.of(expectedUser)

        when:
        userRegisterController.registerUser(request)

        then:
        def e = thrown(RegisterNotAllowedException)
        e.message == "User already registered email=someemail@domain.com"

    }
}
