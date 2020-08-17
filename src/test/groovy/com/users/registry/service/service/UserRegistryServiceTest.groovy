package com.users.registry.service.service

import com.users.registry.service.domain.Phone
import com.users.registry.service.domain.User
import com.users.registry.service.domain.request.RegisterUserRequest
import com.users.registry.service.repository.UserRepository
import spock.lang.Specification

import java.sql.Date
import java.time.LocalDate

class UserRegistryServiceTest extends Specification {


    UserRegistryService userRegistryService
    UserRepository userRepository = Mock(UserRepository)


    def setup() {
        userRegistryService = new UserRegistryService(userRepository)

    }

    def "RegisterUser"() {
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

        userRepository.save(_ as User) >> expectedUser
        when:
        def result = userRegistryService.registerUser(request)

        then:
        result.name == "John Doe"
        result.email == "someemail@domain.com"
        result.password == "youDontKnow10"

    }

    def "FindAllUsers"() {
        given:
        def users = [User.builder()
                             .name("testUserName1")
                             .email("test1@test.com")
                             .password("Aaaaa11")
                             .token(UUID.randomUUID().toString())
                             .createdDate(Date.valueOf(LocalDate.now()))
                             .modifiedDate(Date.valueOf(LocalDate.now()))
                             .lastLoginDate(Date.valueOf(LocalDate.now()))
                             .phoneList([])
                             .build()
        ]

        userRepository.findAll() >> users

        when:
        def result = userRegistryService.findAllUsers()

        then:
            result.size() == 1
            result == users
    }

    def "FindUserByEmail"() {
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

        userRepository.findByEmail(_ as String) >> Optional.of(expectedUser)

        when:
        def result = userRegistryService.findUserByEmail("someemail@domain.com")

        then:
        result.get().name == "John Doe"
        result.get().email == "someemail@domain.com"
        result.get().password == "youDontKnow10"

    }
}
