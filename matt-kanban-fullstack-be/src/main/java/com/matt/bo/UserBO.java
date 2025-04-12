package com.matt.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the Business Object (BO) for a User entity.
 * This class is used to hold the business data for a user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBO {

    /**
     * The unique identifier for the user.
     */
    private Long id;

    /**
     * The identifier of the user who created this user record.
     */
    private Long creatorId;

    /**
     * The username chosen by the user, used for login and display purposes.
     */
    private String username;

    /**
     * The user's password, stored securely (ensure it's properly hashed and never exposed in plain text).
     */
    private String password;

    /**
     * The user's email address, used for communication and as an alternative login method.
     */
    private String email;

    /**
     * The salt used for hashing the user's password to enhance security.
     */
    private String salt;
}
