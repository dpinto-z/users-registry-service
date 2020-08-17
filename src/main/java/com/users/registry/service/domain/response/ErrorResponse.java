package com.users.registry.service.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponse {

    @JsonProperty("ts")
    private Instant timeStamp = Instant.now();
    private int httpStatus;
    private List<Errors> errors;

    public ErrorResponse(int httpStatus, List<Errors> errors) {
        this.httpStatus = httpStatus;
        this.errors = errors;
    }
}
