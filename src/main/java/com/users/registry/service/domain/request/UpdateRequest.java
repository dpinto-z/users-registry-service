package com.users.registry.service.domain.request;

import com.users.registry.service.domain.Phone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequest {
    @NotEmpty
    private String name;
    @Email(message = "Email not valid!")
    private String email;
    @Pattern(regexp = "^(?=(?:.*?[A-Z]){1})(?=.*?[a-z])(?=(?:.*?[0-9]){2}).*$",
            message = "Password must contain one uppercase one lowercase, and two numbers!")
    private String password;
    private List<Phone> phoneList;
}
