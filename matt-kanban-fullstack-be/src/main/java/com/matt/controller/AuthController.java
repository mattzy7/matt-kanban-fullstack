package com.matt.controller;

import com.matt.dto.request.LoginRequestDTO;
import com.matt.dto.request.LogoutRequestDTO;
import com.matt.dto.response.APIResponse;
import com.matt.dto.response.UserLoginResponseDTO;
import com.matt.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("auth")
@RestController
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final HttpServletRequest request;
    public AuthController(AuthService authService, HttpServletRequest request) {
        this.authService = authService;
        this.request = request;
    }

    @Operation(
            summary = "Login user",
            description = "Login the user using username or email and password, and return a JWT token.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class))),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    @PostMapping("login")
    public ResponseEntity<APIResponse<UserLoginResponseDTO>> login(@Valid @RequestBody LoginRequestDTO request) throws Exception {
        UserLoginResponseDTO dto = authService.login(request);
        log.info("UserLoginResponseDTO is {}", dto);
        return ResponseEntity.ok(APIResponse.success(dto));
    }

    @Operation(
            summary = "Logout user",
            description = "Logout the user by invalidating their JWT token.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Logout successful",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            }
    )
    @PostMapping("logout")
    public ResponseEntity<APIResponse<String>> logout(@Valid @RequestBody LogoutRequestDTO request) throws Exception {
        authService.logout(request);
        return ResponseEntity.ok(APIResponse.success(""));
    }
}
