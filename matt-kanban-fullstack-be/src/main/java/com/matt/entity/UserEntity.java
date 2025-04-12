package com.matt.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "t_users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String salt;

    @Column(nullable = false)
    private String email;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;
}