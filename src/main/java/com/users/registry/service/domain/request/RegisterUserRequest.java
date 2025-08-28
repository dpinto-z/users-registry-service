package com.users.registry.service.domain.request;


import com.users.registry.service.domain.Phone;
import lombok.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {
    @NotEmpty
    private String name;
    @Email(message = "Email not valid!")
    private String email;
    @Pattern(regexp = "^(?=(?:.*?[A-Z]){1})(?=.*?[a-z])(?=(?:.*?[0-9]){2}).*$",
            message = "Password must contain one uppercase one lowercase, and two numbers!")
    private String password;
    private List<Phone> phoneList;
}
