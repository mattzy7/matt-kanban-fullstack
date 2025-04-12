package com.matt.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.matt.utils.Constants.SYSTEM_USER_ID;

/**
 * Data Transfer Object (DTO) representing a response for user login.
 * This class extends {@link UserResponseDTO} and adds a token field, which is typically
 * used to store the authentication token returned after a successful login.
 * <p>
 * The {@link UserLoginResponseDTO} is used to send user details along with the authentication token
 * back to the client after a successful login request.
 * </p>
 */
@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class UserLoginResponseDTO extends UserResponseDTO {
    public UserLoginResponseDTO(Long id, String email, String username, String token) {
        super(id, SYSTEM_USER_ID, email, username);
        this.token = token;
    }

    /**
     * The authentication token (e.g., JWT) generated after successful login.
     * This token is used for subsequent requests to authenticate the user.
     */
    private String token;
}
