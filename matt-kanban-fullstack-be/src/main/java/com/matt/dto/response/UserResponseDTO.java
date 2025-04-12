package com.matt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing a user's basic response information.
 * This class is used to transfer the user details such as id, creatorId, email, and username
 * across different layers or services in the application.
 * <p>
 * {@link UserResponseDTO} serves as a base class for user-related responses, including but not limited to
 * user information, authentication, or other user-related details.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    /**
     * The unique identifier of the user.
     */
    protected Long id;
    /**
     * The identifier of the user who created or owns this user record.
     */
    protected Long creatorId;
    /**
     * The email address associated with the user.
     */
    protected String email;
    /**
     * The username chosen by the user.
     */
    protected String username;
}
