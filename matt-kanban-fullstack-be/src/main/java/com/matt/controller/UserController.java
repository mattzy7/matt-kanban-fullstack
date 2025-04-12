package com.matt.controller;

import com.matt.bo.UserBO;
import com.matt.dto.request.UserCreateRequestDTO;
import com.matt.dto.response.APIResponse;
import com.matt.dto.response.UserResponseDTO;
import com.matt.exceptions.UserDuplicateException;
import com.matt.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    UserService userService;

    @Operation(
            summary = "Create user",
            description = "Create a new user, typically used for user registration.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User successfully created",
                            content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request data",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    @PostMapping
    public ResponseEntity<APIResponse<UserResponseDTO>> createUser(@Valid @RequestBody UserCreateRequestDTO userCreateRequestDTO)
            throws UserDuplicateException, NoSuchAlgorithmException, InvalidKeySpecException {
        UserBO userBO =  userService.createUser(userCreateRequestDTO);
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                userBO.getId(),
                userBO.getCreatorId(),
                userBO.getEmail(),
                userBO.getUsername());
        return ResponseEntity.ok(APIResponse.success(userResponseDTO));
    }

}
