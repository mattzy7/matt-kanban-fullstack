package com.matt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(description = "DTO representing for logout")
public class LogoutRequestDTO {
    @NotEmpty(message = "User Id is required.")
    @Schema(description = "User's id", example = "123456")
    private String jwtToken;
}

