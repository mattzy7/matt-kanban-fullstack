package com.matt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse<T> {
    private boolean success;
    private T data;
    private String message;

    public static <T> APIResponse<T> success(T data) {
        return new APIResponse<>(true, data, null);
    }

    public static <T> APIResponse<T> failure(String message) {
        return new APIResponse<>(false, null, message);
    }
}
