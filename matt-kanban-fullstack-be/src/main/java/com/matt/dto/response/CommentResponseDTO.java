package com.matt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing a comment response.
 * This object is used to transfer comment data between layers, typically from the service layer
 * to the presentation layer.
 * <p>
 * A CommentResponseDTO contains details about the comment itself, such as its ID, content,
 * the ID of its creator, the creation timestamp, and any child comments (replies).
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {
    /**
     * The unique identifier for the comment.
     */
    private Integer id;
    /**
     * The ID of the parent comment.
     * If the comment is a root-level comment, this value will be {@code COMMENTS_ROOT_PID}.
     */
    private Integer parentId;
    /**
     * The ID of the user who created the comment.
     */
    private Integer creatorId;
    /**
     * The content of the comment.
     * This is the text or body of the comment made by the user.
     */
    private String content;
    /**
     * The timestamp of when the comment was created.
     * This is used to determine the comment's creation time.
     */
    private LocalDateTime createdOn;
    /**
     * The username of the user who created the comment.
     * This field is set based on the creator's ID and is used for displaying the comment author's name.
     */
    private String username;
    /**
     * A list of child comments (replies) to the current comment.
     * This forms the nested structure of replies in a comment thread.
     */
    private List<CommentResponseDTO> comments;
}
